package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_WEIGHT;

public class PartGroupUpdaterTest {
	@Test
	public void constructorContract() {
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		final PartGroupUpdater partGroupUpdater = new PartGroupUpdater( partEntity );
		Assertions.assertNotNull( partGroupUpdater );
	}

	@Test
	public void update() {
		// Given
		final String MODIFIED = "MODIFIED";
		final UpdateGroupPartRequest updateData = new UpdateGroupPartRequest.Builder()
				.withLabel( TEST_PART_LABEL + MODIFIED )
				.withDescription( TEST_PART_DESCRIPTION + MODIFIED )
				.withBuildTime( TEST_PART_BUILD_TIME + 10 )
				.withWeight( TEST_PART_WEIGHT + 10 )
				.withImagePath( TEST_PART_IMAGE_PATH + MODIFIED )
				.withModelPath( TEST_PART_MODEL_PATH + MODIFIED )
				.build();
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		// Test
		final PartGroupUpdater partGroupUpdater = new PartGroupUpdater( partEntity );
		final PartEntity obtained = partGroupUpdater.update( updateData );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_PART_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_PART_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_PART_DESCRIPTION + MODIFIED, obtained.getDescription() );
		Assertions.assertEquals( TEST_PART_MATERIAL, obtained.getMaterial() );
		Assertions.assertEquals( TEST_PART_COLOR, obtained.getColor() );
		Assertions.assertEquals( TEST_PART_WEIGHT + 10, obtained.getWeight() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME + 10, obtained.getBuildTime() );
		Assertions.assertEquals( TEST_PART_COST, obtained.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE, obtained.getPrice() );
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL, obtained.getStockLevel() );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE, obtained.getStockAvailable() );
		Assertions.assertEquals( TEST_PART_IMAGE_PATH + MODIFIED, obtained.getImagePath() );
		Assertions.assertEquals( TEST_PART_MODEL_PATH + MODIFIED, obtained.getModelPath() );
		Assertions.assertFalse( obtained.isActive() );
	}
}
