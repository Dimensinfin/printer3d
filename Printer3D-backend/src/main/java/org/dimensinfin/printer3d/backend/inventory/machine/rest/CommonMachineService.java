package org.dimensinfin.printer3d.backend.inventory.machine.rest;

import javax.validation.constraints.NotNull;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.rest.PartRestErrors;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.16.1
 */
public class CommonMachineService {
	protected final PartRepository partRepository;

	// - C O N S T R U C T O R S
	public CommonMachineService( @NotNull final PartRepository partRepository ) {
		this.partRepository = partRepository;
	}

	protected Part getBuildPart( final MachineEntity machineEntity ) {
		// Check for completed jobs.
		if (null != machineEntity.getCurrentJobPartId()) { // Check if the job has completed
			return new PartEntityToPartConverter().convert(
					this.partRepository.findById( machineEntity.getCurrentJobPartId() )
							.orElseThrow(
									() -> new DimensinfinRuntimeException( PartRestErrors.errorPARTNOTFOUND( machineEntity.getCurrentJobPartId() ) ) )
			);
		} else return null;
	}
}