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
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PROJECT;
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
				.withProject( TEST_PART_PROJECT + MODIFIED )
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
				.withWeight( TEST_PART_WEIGHT )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		// Assertions
		// Original
		Assertions.assertEquals( TEST_PART_ID.toString(), partEntity.getId().toString() );
		Assertions.assertEquals( TEST_PART_LABEL, partEntity.getLabel() );
		Assertions.assertEquals( "<DEFAULT>", partEntity.getProject() );
		Assertions.assertEquals( TEST_PART_DESCRIPTION, partEntity.getDescription() );
		Assertions.assertEquals( TEST_PART_MATERIAL, partEntity.getMaterial() );
		Assertions.assertEquals( TEST_PART_COLOR, partEntity.getColor() );
		Assertions.assertEquals( TEST_PART_WEIGHT, partEntity.getWeight() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME, partEntity.getBuildTime() );
		Assertions.assertEquals( TEST_PART_COST, partEntity.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE, partEntity.getPrice() );
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL, partEntity.getStockLevel() );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE, partEntity.getStockAvailable() );
		Assertions.assertEquals( TEST_PART_IMAGE_PATH, partEntity.getImagePath() );
		Assertions.assertEquals( TEST_PART_MODEL_PATH, partEntity.getModelPath() );
		Assertions.assertFalse( partEntity.isActive() );
		// Test
		final PartGroupUpdater partGroupUpdater = new PartGroupUpdater( partEntity );
		final PartEntity obtained = partGroupUpdater.update( updateData );
		// Assertions
		// Modified
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_PART_ID.toString(), partEntity.getId().toString() );
		Assertions.assertEquals( TEST_PART_LABEL, partEntity.getLabel() );
		Assertions.assertEquals( TEST_PART_PROJECT + MODIFIED, partEntity.getProject() );
		Assertions.assertEquals( TEST_PART_DESCRIPTION + MODIFIED, obtained.getDescription() );
		Assertions.assertEquals( TEST_PART_MATERIAL, partEntity.getMaterial() );
		Assertions.assertEquals( TEST_PART_COLOR, partEntity.getColor() );
		Assertions.assertEquals( TEST_PART_WEIGHT + 10, obtained.getWeight() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME + 10, obtained.getBuildTime() );
		Assertions.assertEquals( TEST_PART_COST, partEntity.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE, partEntity.getPrice() );
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL, partEntity.getStockLevel() );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE, partEntity.getStockAvailable() );
		Assertions.assertEquals( TEST_PART_IMAGE_PATH + MODIFIED, obtained.getImagePath() );
		Assertions.assertEquals( TEST_PART_MODEL_PATH + MODIFIED, obtained.getModelPath() );
		Assertions.assertFalse( partEntity.isActive() );
	}
}
