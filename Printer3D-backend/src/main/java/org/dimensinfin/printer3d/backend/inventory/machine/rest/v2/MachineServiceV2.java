package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.printer3d.backend.core.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListv2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machinev2;

@Service
@Transactional
public class MachineServiceV2 {
	protected final MachineRepository machineRepository;
	protected final PartRepository partRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public MachineServiceV2( final MachineRepository machineRepository,
	                         final PartRepository partRepository ) {
		this.machineRepository = Objects.requireNonNull( machineRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	public MachineListv2 getMachines() {
		final List<Machinev2> machines = this.machineRepository.findAll()
				.stream()
				.map( machineEntity -> {
					// Convert the entity to the api domain
					final BuildRecord buildRecord = new BuildRecord();
					buildRecord.setPartCopies( machineEntity.getCurrentPartInstances() );
					buildRecord.setPart( this.getBuildPart( machineEntity ) );
					buildRecord.setJobInstallmentDate( machineEntity.getJobInstallmentDate() );
					final Machinev2 machineModel = new Machinev2();
					machineModel.setId( machineEntity.getId() );
					machineModel.setLabel( machineEntity.getLabel() );
					machineModel.setModel( machineEntity.getModel() );
					machineModel.setCharacteristics( machineEntity.getCharacteristics() );
					machineModel.setBuildRecord( buildRecord );

					if (machineModel.isRunning()) { // The Machine has a part but it can have finished the build time
						final int remainingTime = machineModel.getRemainingTime();
						if (0 == remainingTime) { // Job completed
							machineModel.getBuildRecord().getPart().incrementStock( machineEntity.getCurrentPartInstances() );
							this.partRepository.save( machineModel.getBuildRecord().getPart() );
							machineEntity.clearJob();
							buildRecord.clearJob();
							this.machineRepository.save( machineEntity );
						}
						//						final Duration jobDuration = Duration.ofMinutes( machine.getCurrentJobPart().getBuildTime() );
						//						final LocalDateTime jobCompletionTime = machineEntity.getJobInstallmentDate().plus( jobDuration );
						//						if (LocalDateTime.now().isAfter( jobCompletionTime ))

					}
					// Check for completed jobs.
					//					if (null != machineEntity.getCurrentJobPartId()) { // Check if the job has completed
					//						final Optional<Part> jobPartOpt = this.partRepository.findById( machineEntity.getCurrentJobPartId() );
					//						if (jobPartOpt.isEmpty())
					//							throw new InvalidRequestException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( machineEntity.getCurrentJobPartId() ) );
					//						// Replace the MachineEntitu by an expanded api Machine
					//						final Machine machine = new MachineEntityToMachineConverter( jobPartOpt.get() ).convert( machineEntity );
					return machineModel;
				} )
				.collect( Collectors.toList() );
		return new MachineListv2().machines( machines );
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
