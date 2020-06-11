package org.dimensinfin.printer3d.backend.inventory.model.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_STOCK_LEVEL;

public class ModelEntityToModelConverterTest {

	@Test
	public void convert() {
		// Given
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		final ModelEntityToModelConverter modelEntityToModelConverter = new ModelEntityToModelConverter();
		// Test
		final Model obtained = modelEntityToModelConverter.convert( modelEntity );
		// Assertions
		Assertions.assertEquals( TEST_MODEL_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_MODEL_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_MODEL_IMAGE_PATH, obtained.getImagePath() );
		Assertions.assertEquals( TEST_MODEL_PRICE, obtained.getPrice(), 0.1 );
		Assertions.assertEquals( TEST_MODEL_STOCK_LEVEL, obtained.getStockLevel() );
		Assertions.assertEquals( TEST_MODEL_STOCK_AVAILABLE, obtained.getStockAvailable() );
		Assertions.assertEquals( TEST_MODEL_ACTIVE, obtained.isActive() );
	}
}
