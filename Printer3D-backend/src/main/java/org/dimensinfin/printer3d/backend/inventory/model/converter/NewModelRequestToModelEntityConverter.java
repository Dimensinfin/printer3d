package org.dimensinfin.printer3d.backend.inventory.model.converter;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelRequest;

@Component
public class NewModelRequestToModelEntityConverter implements Converter<ModelRequest, ModelEntity> {
	@Override
	public ModelEntity convert( final ModelRequest input ) {
		return new ModelEntity.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withPrice( input.getPrice() )
				.withStockLevel( input.getStockLevel() )
				.withPartIdList( input.getPartIdList() )
				.withImagePath( input.getImagePath() )
				.withActive( true )
				.build();
	}
}
