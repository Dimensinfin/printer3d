package org.dimensinfin.printer3d.backend.inventory.part.converter;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

@Component
public class PartToPartEntityConverter implements Converter<Part, PartEntity> {

	@Override
	public PartEntity convert( final Part input ) {
		return new PartEntity.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
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
				.build()
				.setProject( input.getProject() );
	}
}
