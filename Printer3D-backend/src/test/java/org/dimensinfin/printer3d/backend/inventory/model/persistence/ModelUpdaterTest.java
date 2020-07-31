package org.dimensinfin.printer3d.backend.inventory.model.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_STOCK_LEVEL;

public class ModelUpdaterTest {
	@Test
	public void constructorContract() {
		final ModelEntity modelEntity = Mockito.mock( ModelEntity.class );
		final ModelUpdater modelUpdater = new ModelUpdater( modelEntity );
		Assertions.assertNotNull( modelUpdater );
	}

	@Test
	public void update() {
		// Given
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		final ModelRequest modelRequest = new ModelRequest.Builder()
				.withId( TEST_MODELREQUEST_ID )
				.withLabel( TEST_MODELREQUEST_LABEL )
				.withPrice( TEST_MODELREQUEST_PRICE )
				.withStockLevel( TEST_MODELREQUEST_STOCK_LEVEL )
				.withImagePath( TEST_MODELREQUEST_IMAGE_PATH )
				.withActive( TEST_MODELREQUEST_ACTIVE )
				.build();
		// Test
		final ModelUpdater modelUpdater = new ModelUpdater( modelEntity );
		final ModelEntity obtained = modelUpdater.update( modelRequest );
		// Assertions
		Assertions.assertEquals( TEST_MODEL_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_MODELREQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_MODELREQUEST_PRICE, obtained.getPrice(), 0.01 );
		Assertions.assertEquals( TEST_MODELREQUEST_STOCK_LEVEL, obtained.getStockLevel() );
		Assertions.assertEquals( TEST_MODELREQUEST_IMAGE_PATH, obtained.getImagePath() );
		Assertions.assertFalse( obtained.isActive() );
	}
}
