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
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineUpdater;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.converter.MachineEntityToMachineConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.StartBuildRequest;

@Service
@Transactional
public class MachineServiceV1 {
	protected final MachineRepository machineRepository;
	protected final PartRepository partRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public MachineServiceV1( final MachineRepository machineRepository,
	                         final PartRepository partRepository ) {
		this.machineRepository = Objects.requireNonNull( machineRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	public MachineList getMachines() {
		final List<Machine> machines = this.machineRepository.findAll()
				.stream()
				.map( machineEntity -> {
					// Check for completed jobs.
					if (null != machineEntity.getCurrentJobPartId()) { // Check if the job has completed
						final Optional<Part> jobPartOpt = this.partRepository.findById( machineEntity.getCurrentJobPartId() );
						if (jobPartOpt.isEmpty())
							throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machineEntity.getCurrentJobPartId() ) );
						// Replace the MachineEntitu by an expanded api Machine
						final Machine machine = new MachineEntityToMachineConverter( jobPartOpt.get() ).convert( machineEntity );
						final Duration jobDuration = Duration.ofMinutes( machine.getCurrentJobPart().getBuildTime() );
						final OffsetDateTime jobCompletionTime = machineEntity.getJobInstallmentDate().plus( jobDuration );
						if (OffsetDateTime.now().isAfter( jobCompletionTime )) { // Job completed
							machineEntity.clearJob();
							this.machineRepository.save( machineEntity );
							machine.getCurrentJobPart().incrementStock( machineEntity.getCurrentPartInstances() );
							this.partRepository.save( machine.getCurrentJobPart() );
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

	public Machine startBuild( final StartBuildRequest startBuildRequest ) {
		// Find the machine nad update it.
		final Optional<MachineEntity> machineOpt = this.machineRepository.findById( startBuildRequest.getMachineId() );
		if (machineOpt.isEmpty())
			throw new DimensinfinRuntimeException( ErrorInfo.MACHINE_NOT_FOUND.getErrorMessage( startBuildRequest.getMachineId() ) );
		final Optional<Part> jobPartOpt = this.partRepository.findById( startBuildRequest.getPartId() );
		if (jobPartOpt.isEmpty())
			throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machineOpt.get().getCurrentJobPartId() ) );
		return new MachineEntityToMachineConverter( jobPartOpt.get() ).convert(
				this.machineRepository.save( new MachineUpdater( machineOpt.get() ).update( startBuildRequest.getPartId() ) )
		);
	}
}
