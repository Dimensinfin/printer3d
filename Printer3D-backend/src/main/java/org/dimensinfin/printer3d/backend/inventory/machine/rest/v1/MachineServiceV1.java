package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.printer3d.backend.core.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobEntity;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineUpdaterV1;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.converter.MachineEntityToMachineConverter;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.StartBuildRequest;

@Service
@Transactional
public class MachineServiceV1 {
	protected final MachineRepository machineRepository;
	protected final PartRepository partRepository;
	private final JobRepository jobRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public MachineServiceV1( final MachineRepository machineRepository,
	                         final PartRepository partRepository,
	                         final JobRepository jobRepository ) {
		this.machineRepository = Objects.requireNonNull( machineRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
		this.jobRepository = Objects.requireNonNull( jobRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	@Deprecated
	public MachineList getMachines() {
		final List<Machine> machines = this.machineRepository.findAll()
				.stream()
				.map( machineEntity -> {
					// Check for completed jobs.
					if (null != machineEntity.getCurrentJobPartId()) { // Check if the job has completed
						final Optional<PartEntity> jobPartOpt = this.partRepository.findById( machineEntity.getCurrentJobPartId() );
						if (jobPartOpt.isEmpty())
							throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machineEntity.getCurrentJobPartId() ) );
						final PartEntity targetPart = jobPartOpt.get();
						// Replace the MachineEntity by an expanded api Machine
						final Machine machine = new MachineEntityToMachineConverter( new PartEntityToPartConverter().convert( targetPart ) )
								.convert( machineEntity );
						final Duration jobDuration = Duration.ofMinutes( machine.getCurrentJobPart().getBuildTime() );
						final OffsetDateTime jobCompletionTime = machineEntity.getJobInstallmentDate().plus( jobDuration );
						if (OffsetDateTime.now().isAfter( jobCompletionTime )) { // Job completed
							machineEntity.clearJob();
							this.machineRepository.save( machineEntity );
							targetPart.incrementStock( machineEntity.getCurrentPartInstances() );
							this.partRepository.save( targetPart );
						}
						return machine;
					} else return new MachineEntityToMachineConverter( null ).convert( machineEntity );
				} )
				.collect( Collectors.toList() );
		return new MachineList.Builder()
				.withMachines( machines )
				.build();
	}

	public Machine cancelBuild( final @NotNull UUID machineId ) {
		final Optional<MachineEntity> machineOpt = this.machineRepository.findById( machineId );
		if (machineOpt.isEmpty())
			throw new DimensinfinRuntimeException( ErrorInfo.MACHINE_NOT_FOUND.getErrorMessage( machineId ) );
		return new MachineEntityToMachineConverter( null ).convert( this.machineRepository.save( machineOpt.get().clearJob() ) );
	}

	/**
	 * With this command the **Machine** will complete the current job. This will change the Part stock levels adding to the current Part stock
	 * the number of Parts already built. Then the Machine will return to the IDLE state.
	 * The Job registered on the Machine is then persisted to a nee backend repository to create an analysis record to be able to perform
	 * production statistics with the completed jobs.
	 *
	 * @param machineId the machine identifier that has the job to complete.
	 */
	public Machine completeBuild( final UUID machineId ) {
		final Optional<MachineEntity> machineOpt = this.machineRepository.findById( machineId );
		if (machineOpt.isEmpty())
			throw new DimensinfinRuntimeException( ErrorInfo.MACHINE_NOT_FOUND.getErrorMessage( machineId ) );
		final MachineEntity machineEntity = machineOpt.get();
		this.storeBuiltParts( machineEntity.getCurrentJobPartId(), machineEntity.getCurrentPartInstances() );
		this.recordJob( machineEntity );
		return new MachineEntityToMachineConverter( null ).convert( this.machineRepository.save( machineOpt.get().clearJob() ) );
	}

	public Machine startBuild( final StartBuildRequest startBuildRequest ) {
		// Find the machine and update it.
		final Optional<MachineEntity> machineOpt = this.machineRepository.findById( startBuildRequest.getMachineId() );
		if (machineOpt.isEmpty())
			throw new DimensinfinRuntimeException( ErrorInfo.MACHINE_NOT_FOUND.getErrorMessage( startBuildRequest.getMachineId() ) );
		final Optional<PartEntity> jobPartOpt = this.partRepository.findById( startBuildRequest.getPartId() );
		if (jobPartOpt.isEmpty())
			throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machineOpt.get().getCurrentJobPartId() ) );
		return new MachineEntityToMachineConverter( new PartEntityToPartConverter().convert( jobPartOpt.get() ) )
				.convert(
						this.machineRepository.save( new MachineUpdaterV1( machineOpt.get() ).update( startBuildRequest.getPartId() ) )
				);
	}

	private void recordJob( final MachineEntity machineEntity ) {
		final Optional<PartEntity> jobPartOpt = this.partRepository.findById( machineEntity.getCurrentJobPartId() );
		if (jobPartOpt.isEmpty())
			throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machineEntity.getCurrentJobPartId() ) );
		final JobEntity job = new JobEntity.Builder()
				.withPartId( machineEntity.getCurrentJobPartId() )
				.withBuildTime( jobPartOpt.get().getBuildTime() * machineEntity.getCurrentPartInstances() ) // Multiple copies take longer to build.
				.withCost( jobPartOpt.get().getCost() )
				.withPrice( jobPartOpt.get().getPrice() )
				.withPartCopies( machineEntity.getCurrentPartInstances() )
				.withJobInstallmentDate( machineEntity.getJobInstallmentDate() )
				.withJobBuildDate( OffsetDateTime.now() )
				.build();
		this.jobRepository.save( job );
	}

	private void storeBuiltParts( final UUID currentJobPartId, final int currentPartInstances ) {
		final Optional<PartEntity> jobPartOpt = this.partRepository.findById( currentJobPartId );
		if (jobPartOpt.isEmpty())
			throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( currentJobPartId ) );
		final PartEntity targetPart = jobPartOpt.get();
		targetPart.incrementStock( currentPartInstances );
		this.partRepository.save( targetPart );
	}
}
