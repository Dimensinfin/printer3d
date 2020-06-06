package org.dimensinfin.printer3d.backend.inventory.machine.rest.converter;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;

@Component
public class MachineEntityToMachineV2Converter implements Converter<MachineEntity, MachineV2> {
	private final BuildRecord buildRecord;

	// - C O N S T R U C T O R S
	public MachineEntityToMachineV2Converter( final @NotNull BuildRecord buildRecord ) {
		this.buildRecord = Objects.requireNonNull( buildRecord );
	}

	@Override
	public MachineV2 convert( final MachineEntity input ) {
		return new MachineV2.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withModel( input.getModel() )
				.withCharacteristics( input.getCharacteristics() )
				.withBuildRecord( this.buildRecord )
				.build();
	}
}
