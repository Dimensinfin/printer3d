package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR_CODE;
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

public class PartUpdaterTest {
	@Test
	public void constructorContract() {
		final Part part = Mockito.mock( Part.class );
		final PartUpdater partUpdater = new PartUpdater( part );
		Assertions.assertNotNull( partUpdater );
	}

	@Test
	public void update() {
		// Given
		final Part part = new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColorCode( TEST_PART_COLOR_CODE )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final Part updatePart = new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColorCode( TEST_PART_COLOR_CODE )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST + 10 )
				.withPrice( TEST_PART_PRICE + 10 )
				.withStockLevel( TEST_PART_STOCK_LEVEL + 2 )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE + 3 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( true )
				.build();
		final UUID partId = UUID.randomUUID();
		// Test
		final Part obtained = new PartUpdater( part ).update( updatePart );
		// Assertions
		Assertions.assertEquals( TEST_PART_COST + 10, obtained.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE + 10, obtained.getPrice() );
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL + 2, obtained.getStockLevel() );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE + 3, obtained.getStockAvailable() );
		Assertions.assertEquals( TEST_PART_MATERIAL, obtained.getMaterial() );
		Assertions.assertEquals( TEST_PART_COLOR_CODE, obtained.getColorCode() );
	}
}
