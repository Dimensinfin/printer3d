package org.dimensinfin.printer3d.backend.inventory.coil.converter;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

@Component
public class CoilEntityToCoilConverter implements Converter<CoilEntity, Coil> {
	@Override
	public Coil convert( final CoilEntity input ) {
		return new Coil.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withWeight( input.getWeight() )
				.withMaterial( input.getMaterial() )
				.withColor( input.getColor() )
				.withActive( input.getActive() )
				.withTradeMark( input.getTradeMark() )
				.build();
	}
}
