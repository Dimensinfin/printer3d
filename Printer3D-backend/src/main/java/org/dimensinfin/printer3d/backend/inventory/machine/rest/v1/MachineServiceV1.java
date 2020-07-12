package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.converter.MachineEntityToMachineConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobEntity;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;

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
	public Machine cancelBuild( final @NotNull UUID machineId ) {
		final Optional<MachineEntity> machineOpt = this.machineRepository.findById( machineId );
		if (machineOpt.isEmpty())
			throw new RepositoryConflictException( Printer3DErrorInfo.MACHINE_NOT_FOUND( machineId.toString() ) );
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
			throw new RepositoryConflictException( Printer3DErrorInfo.MACHINE_NOT_FOUND( machineId.toString() ) );
		final MachineEntity machineEntity = machineOpt.get();
		this.storeBuiltParts( machineEntity.getCurrentJobPartId(), machineEntity.getCurrentPartInstances() );
		this.recordJob( machineEntity );
		return new MachineEntityToMachineConverter( null ).convert( this.machineRepository.save( machineOpt.get().clearJob() ) );
	}

	private void recordJob( final MachineEntity machineEntity ) {
		final Optional<PartEntity> jobPartOpt = this.partRepository.findById( machineEntity.getCurrentJobPartId() );
		if (jobPartOpt.isEmpty())
			throw new RepositoryConflictException( Printer3DErrorInfo.PART_NOT_FOUND( machineEntity.getCurrentJobPartId() ) );
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
			throw new RepositoryConflictException( Printer3DErrorInfo.PART_NOT_FOUND( currentJobPartId ) );
		final PartEntity targetPart = jobPartOpt.get();
		targetPart.incrementStock( currentPartInstances );
		this.partRepository.save( targetPart );
	}
}
