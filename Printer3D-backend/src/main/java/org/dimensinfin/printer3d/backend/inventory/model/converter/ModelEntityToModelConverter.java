package org.dimensinfin.printer3d.backend.inventory.model.converter;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;

public class ModelEntityToModelConverter implements Converter<ModelEntity, Model> {
	@Override
	public Model convert( final ModelEntity input ) {
		return new Model.Builder()
				.withId( input.getId() )
				.withLabel( input.getLabel() )
				.withPartIdentifierList( input.getPartList())
				.withPrice( input.getPrice() )
				.withStockLevel( input.getStockLevel() )
				.withStockAvailable( input.getStockAvailable() )
				.withImagePath( input.getImagePath() )
				.withActive( true )
				.build();
	}
}
