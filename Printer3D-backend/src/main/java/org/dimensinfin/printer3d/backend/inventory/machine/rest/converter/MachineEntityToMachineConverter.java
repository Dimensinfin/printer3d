package org.dimensinfin.printer3d.backend.inventory.machine.rest.converter;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

@Deprecated
//@Component
public class MachineEntityToMachineConverter implements Converter<MachineEntity, Machine> {
	private final Part part;

	// - C O N S T R U C T O R S
	public MachineEntityToMachineConverter( final Part part ) {
		this.part = part;
	}

	@Override
	public Machine convert( final MachineEntity input ) {
		return new Machine.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withModel( input.getModel() )
				.withCharacteristics( input.getCharacteristics() )
				.withPart( this.part )
				.withInstallmentDate( input.getJobInstallmentDate() )
				.withInstances( input.getCurrentPartInstances() )
				.build();
	}
}
