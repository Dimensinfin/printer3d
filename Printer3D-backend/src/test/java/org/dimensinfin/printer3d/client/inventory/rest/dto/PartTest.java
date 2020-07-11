package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
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

public class PartTest {
	@Test
	public void buildContract() {
		final Part part = new Part.Builder()
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
		Assertions.assertNotNull( part );
	}

	@Test
	public void buildFailureNull() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( null )
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
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( null )
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
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withColor( null )
					.withBuildTime( TEST_PART_BUILD_TIME )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withColor( TEST_PART_COLOR )
					.withBuildTime( null )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withColor( TEST_PART_COLOR )
					.withBuildTime( TEST_PART_BUILD_TIME )
					.withCost( null )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withColor( TEST_PART_COLOR )
					.withBuildTime( TEST_PART_BUILD_TIME )
					.withCost( TEST_PART_COST )
					.withPrice( null )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( null )
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
		} );
	}

	@Test
	public void buildFailureUndefined() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
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
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
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
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withBuildTime( TEST_PART_BUILD_TIME )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withColor( TEST_PART_COLOR )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withColor( TEST_PART_COLOR )
					.withBuildTime( TEST_PART_BUILD_TIME )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withColor( TEST_PART_COLOR )
					.withBuildTime( TEST_PART_BUILD_TIME )
					.withCost( TEST_PART_COST )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
	}

//	@Test
//	public void decrementStock() {
//		// Given
//		final Part part = new Part.Builder()
//				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527b" ) )
//				.withLabel( TEST_PART_LABEL )
//				.withDescription( TEST_PART_DESCRIPTION )
//				.withMaterial( TEST_PART_MATERIAL )
//				.withColor( TEST_PART_COLOR )
//				.withBuildTime( TEST_PART_BUILD_TIME )
//				.withCost( TEST_PART_COST )
//				.withPrice( TEST_PART_PRICE )
//				.withStockLevel( TEST_PART_STOCK_LEVEL )
//				.withStockAvailable( 10 )
//				.withImagePath( TEST_PART_IMAGE_PATH )
//				.withModelPath( TEST_PART_MODEL_PATH )
//				.withActive( false )
//				.build();
//		// Test
//		Assertions.assertEquals( 10, part.getStockAvailable() );
//		part.decrementStock( 3 );
//		// Assertions
//		Assertions.assertEquals( 7, part.getStockAvailable() );
//	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( Part.class ).withIgnoredFields( "id" )
				.suppress( Warning.STRICT_INHERITANCE)
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final Part part = new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
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
				.build();
		// Assertions
		Assertions.assertEquals( TEST_PART_ID.toString(), part.getId().toString() );
		Assertions.assertEquals( TEST_PART_LABEL, part.getLabel() );
		Assertions.assertEquals( TEST_PART_DESCRIPTION, part.getDescription() );
		Assertions.assertEquals( TEST_PART_MATERIAL, part.getMaterial() );
		Assertions.assertEquals( TEST_PART_COLOR, part.getColor() );
		Assertions.assertEquals( TEST_PART_WEIGHT, part.getWeight() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME, part.getBuildTime() );
		Assertions.assertEquals( TEST_PART_COST, part.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE, part.getPrice() );
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL, part.getStockLevel() );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE, part.getStockAvailable() );
		Assertions.assertEquals( TEST_PART_IMAGE_PATH, part.getImagePath() );
		Assertions.assertEquals( TEST_PART_MODEL_PATH, part.getModelPath() );
		Assertions.assertFalse( part.isActive() );
	}

	@Test
	public void incrementStock() {
		// Given
		final Part part = new Part.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527b" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( 10 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		// Test
		Assertions.assertEquals( 10, part.getStockAvailable() );
		part.incrementStock( 3 );
		// Assertions
		Assertions.assertEquals( 13, part.getStockAvailable() );
	}

	@Test
	public void toStringContract() {
		// Given
		final Part part = new Part.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527b" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withWeight( 1 )
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
		final String expected = "{\"id\":\"112ad653-9eea-4124-ab20-9fcd92d0527b\",\"label\":\"-TEST_PART_LABEL-\",\"description\":\"-TEST_PART_DESCRIPTION-\",\"material\":\"PLA\",\"color\":\"VERDE-T\",\"weight\":1,\"buildTime\":60,\"cost\":0.76,\"price\":2.0,\"stockLevel\":4,\"stockAvailable\":4,\"imagePath\":\"https:\\/\\/ibb.co\\/3dGbsRh\",\"modelPath\":\"pieza3.STL\",\"active\":false}";
		final String obtained = part.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
