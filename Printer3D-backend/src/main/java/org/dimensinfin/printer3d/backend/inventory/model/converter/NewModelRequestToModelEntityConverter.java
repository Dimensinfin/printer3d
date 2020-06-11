package org.dimensinfin.printer3d.backend.inventory.model.converter;

import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;

public class NewModelRequestToModelEntityConverter implements Converter<NewModelRequest, ModelEntity> {
	@Override
	public ModelEntity convert( final NewModelRequest input ) {
		return new ModelEntity.Builder()
				.withId ( input.getId())
				.withLabel(input.getLabel())
				.withPrice(input.getPrice())
				.withStockLevel(input.getStockLevel())
				.withStockAvailable(input.getStockAvailable())
				.withImagePath(input.getImagePath())
				.withActive(true)
				.build();
	}
}
