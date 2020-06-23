package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineUpdaterV2;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.converter.MachineEntityToMachineV2Converter;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

@Service
@Transactional
public class MachineServiceV2 {
	protected final MachineRepository machineRepository;
	protected final PartRepository partRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public MachineServiceV2( final @NotNull MachineRepository machineRepository,
	                         final @NotNull PartRepository partRepository ) {
		this.machineRepository = Objects.requireNonNull( machineRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	public MachineListV2 getMachines() {
		final List<MachineV2> machines = this.machineRepository.findAll()
				.stream()
				.map( machineEntity -> {
					// Convert the entity to the api domain
					final BuildRecord buildRecord = new BuildRecord.Builder()
							.withPartCopies( machineEntity.getCurrentPartInstances() )
							.withPart( this.getBuildPart( machineEntity ) )
							.withJobInstallmentDate( machineEntity.getJobInstallmentDate() )
							.build();
					return new MachineEntityToMachineV2Converter( buildRecord ).convert( machineEntity );
				} )
				.collect( Collectors.toList() );
		return new MachineListV2.Builder().withMachines( machines ).build();
	}

	public MachineV2 startBuild( final @NotNull UUID machineId, final @NotNull JobRequest jobRequest ) {
		LogWrapper.enter();
		try {
			// Find the machine and update it.
			final Optional<MachineEntity> machineOpt = this.machineRepository.findById( machineId );
			if (machineOpt.isEmpty())
				throw new DimensinfinRuntimeException( ErrorInfo.MACHINE_NOT_FOUND.getErrorMessage( machineId ) );
			final Optional<PartEntity> jobPartOpt = this.partRepository.findById( jobRequest.getPartId() );
			if (jobPartOpt.isEmpty())
				throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machineOpt.get().getCurrentJobPartId() ) );
			final MachineEntity machineEntity = this.machineRepository.save( new MachineUpdaterV2( machineOpt.get() ).update( jobRequest ) );
			final BuildRecord buildRecord = new BuildRecord.Builder()
					.withPartCopies( machineEntity.getCurrentPartInstances() )
					.withPart( this.getBuildPart( machineEntity ) )
					.withJobInstallmentDate( machineEntity.getJobInstallmentDate() )
					.build();
			return new MachineEntityToMachineV2Converter( buildRecord ).convert( machineEntity );
		} finally {
			LogWrapper.exit();
		}
	}

	private Part getBuildPart( final MachineEntity machineEntity ) {
		// Check for completed jobs.
		if (null != machineEntity.getCurrentJobPartId()) { // Check if the job has completed
			final Optional<PartEntity> jobPartOpt = this.partRepository.findById( machineEntity.getCurrentJobPartId() );
			if (jobPartOpt.isEmpty())
				throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machineEntity.getCurrentJobPartId() ) );
			return new PartEntityToPartConverter().convert( jobPartOpt.get() );
		} else return null;
	}
}
