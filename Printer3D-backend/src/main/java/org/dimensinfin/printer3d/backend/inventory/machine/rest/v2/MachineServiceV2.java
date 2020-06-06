package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.converter.MachineEntityToMachineV2Converter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;

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
					final MachineV2 machineModel = new MachineEntityToMachineV2Converter( buildRecord ).convert( machineEntity );

					if (machineModel.isRunning()) { // The Machine has a part but it can have finished the build time
						final int remainingTime = machineModel.getRemainingTime();
						LogWrapper.info( "RemainingTime: " + remainingTime );
						if (0 == remainingTime) { // Job completed
							machineModel.getBuildRecord().getPart().incrementStock( machineEntity.getCurrentPartInstances() );
							this.partRepository.save( machineModel.getBuildRecord().getPart() );
							machineEntity.clearJob();
							buildRecord.clearJob(); // As side effect changes the content of the machine being processed
							this.machineRepository.save( machineEntity );
						}
					}
					return machineModel;
				} )
				.collect( Collectors.toList() );
		LogWrapper.info( machines.toString() );
		return new MachineListV2.Builder().withMachines( machines ).build();
	}

	private Part getBuildPart( final MachineEntity machineEntity ) {
		// Check for completed jobs.
		if (null != machineEntity.getCurrentJobPartId()) { // Check if the job has completed
			final Optional<Part> jobPartOpt = this.partRepository.findById( machineEntity.getCurrentJobPartId() );
			if (jobPartOpt.isEmpty())
				throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machineEntity.getCurrentJobPartId() ) );
			return jobPartOpt.get();
		} else return null;
	}
}
