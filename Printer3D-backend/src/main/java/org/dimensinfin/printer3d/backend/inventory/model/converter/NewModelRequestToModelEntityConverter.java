package org.dimensinfin.printer3d.backend.inventory.model.converter;

import org.springframework.stereotype.Component;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;

@Component
public class NewModelRequestToModelEntityConverter implements Converter<NewModelRequest, ModelEntity> {
	@Override
	public ModelEntity convert( final NewModelRequest input ) {
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
