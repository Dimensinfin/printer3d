package org.dimensinfin.printer3d.backend.inventory.part.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

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

public class PartToPartEntityConverterTest {

	@Test
	public void convert() {
		// Given
		final Part part = new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withProject( TEST_PART_PROJECT )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withWeight( TEST_PART_WEIGHT )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.withUnavailable( true )
				.build();
		final PartToPartEntityConverter converter = new PartToPartEntityConverter();
		// Test
		final PartEntity obtained = converter.convert( part );
		// Assertions
		Assertions.assertEquals( TEST_PART_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_PART_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_PART_PROJECT, obtained.getProject() );
		Assertions.assertEquals( TEST_PART_DESCRIPTION, obtained.getDescription() );
		Assertions.assertEquals( TEST_PART_MATERIAL, obtained.getMaterial() );
		Assertions.assertEquals( TEST_PART_COLOR, obtained.getColor() );
		Assertions.assertEquals( TEST_PART_WEIGHT, obtained.getWeight() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME, obtained.getBuildTime() );
		Assertions.assertEquals( TEST_PART_COST, obtained.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE, obtained.getPrice() );
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL, obtained.getStockLevel() );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE, obtained.getStockAvailable() );
		Assertions.assertEquals( TEST_PART_IMAGE_PATH, obtained.getImagePath() );
		Assertions.assertEquals( TEST_PART_MODEL_PATH, obtained.getModelPath() );
		Assertions.assertFalse( obtained.isActive() );
	}
}