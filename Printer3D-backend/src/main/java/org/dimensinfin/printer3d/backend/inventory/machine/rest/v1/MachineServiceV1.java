package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.printer3d.backend.core.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.client.domain.Machine;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineUpdater;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.domain.MachineList;
import org.dimensinfin.printer3d.client.domain.StartBuildRequest;

@Service
@Transactional
public class MachineServiceV1 {
	private final MachineRepository machineRepository;
	private final PartRepository partRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public MachineServiceV1( final MachineRepository machineRepository,
	                         final PartRepository partRepository ) {
		this.machineRepository = Objects.requireNonNull( machineRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	public MachineList getMachines() {
		final List<Machine> machines = this.machineRepository.findAll();
		machines.forEach( machine -> {
			// Check for completed jobs.
			if (null != machine.getCurrentJobPartId()) { // Check if the job has completed
				final Optional<Part> jobPartOpt = this.partRepository.findById( machine.getCurrentJobPartId() );
				if (!jobPartOpt.isPresent())
					throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machine.getCurrentJobPartId() ) );
				final Part jobPart = jobPartOpt.get();
				final Duration jobDuration = Duration.ofMinutes( jobPart.getBuildTime() );
				final LocalDateTime jobCompletionTime = machine.getJobInstallmentDate().plus( jobDuration );
				if (LocalDateTime.now().isAfter( jobCompletionTime )) { // Job completed
					machine.clearJob();
					this.machineRepository.save( machine );
					jobPart.incrementStock( machine.getCurrentPartInstances() );
					this.partRepository.save( jobPart );
				}
			}
		} );
		return new MachineList.Builder()
				.withMachines( machines )
				.build();
	}

	public Machine cancelBuild( final @NotNull UUID machineId ) {
		final Optional<Machine> machineOpt = this.machineRepository.findById( machineId );
		if (!machineOpt.isPresent())
			throw new DimensinfinRuntimeException( ErrorInfo.MACHINE_NOT_FOUND.getErrorMessage( machineId ) );
		return this.machineRepository.save( machineOpt.get().clearJob() );
	}

	public Machine startBuild( final StartBuildRequest startBuildRequest ) {
		// Find the machine nad update it.
		final Optional<Machine> machineOpt = this.machineRepository.findById( startBuildRequest.getMachineId() );
		if (!machineOpt.isPresent())
			throw new DimensinfinRuntimeException( ErrorInfo.MACHINE_NOT_FOUND.getErrorMessage( startBuildRequest.getMachineId() ) );
		return this.machineRepository.save( new MachineUpdater( machineOpt.get() ).update( startBuildRequest.getPartId() ) );
	}
}
