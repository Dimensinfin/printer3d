package org.dimensinfin.printer3d.backend.inventory.part.converter;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

@Component
public class PartEntityToPartConverter implements Converter<PartEntity, Part> {
	private boolean unavailable = false;

	// - C O N S T R U C T O R S
	public PartEntityToPartConverter() {}

	public PartEntityToPartConverter( final boolean unavailable ) {
		this.unavailable = unavailable;
	}

	@Override
	public Part convert( final PartEntity input ) {
		return new Part.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withProject( input.getProject() )
				.withDescription( input.getDescription() )
				.withBuildTime( input.getBuildTime() )
				.withWeight( input.getWeight() )
				.withMaterial( input.getMaterial() )
				.withColor( input.getColor() )
				.withCost( input.getCost() )
				.withPrice( input.getPrice() )
				.withStockLevel( input.getStockLevel() )
				.withStockAvailable( input.getStockAvailable() )
				.withImagePath( input.getImagePath() )
				.withModelPath( input.getModelPath() )
				.withActive( input.isActive() )
				.withUnavailable( this.unavailable )
				.build();
	}
}
