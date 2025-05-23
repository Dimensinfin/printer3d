package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
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

public class PartEntityTest {
	@Test
	public void buildContract() {
		final PartEntity part = new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withColor( TEST_PART_COLOR )
					.withBuildTime( TEST_PART_BUILD_TIME )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( null )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
			new PartEntity.Builder()
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
		Assertions.assertThrows( NullPointerException.class, () -> {
			new PartEntity.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withMaterial( TEST_PART_MATERIAL )
					.withColor( TEST_PART_COLOR )
					.withBuildTime( TEST_PART_BUILD_TIME )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
					.withImagePath( TEST_PART_IMAGE_PATH )
					.withModelPath( TEST_PART_MODEL_PATH )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new PartEntity.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
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
	public void decrementStock() {
		// Given
		final PartEntity part = new PartEntity.Builder()
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
		part.decrementStock( 3 );
		// Assertions
		Assertions.assertEquals( 7, part.getStockAvailable() );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( PartEntity.class )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final PartEntity partEntity = new PartEntity.Builder()
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
	}

	@Test
	public void incrementStock() {
		// Given
		final PartEntity part = new PartEntity.Builder()
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
	public void setterContract() {
		// Given
		final PartEntity partEntity = new PartEntity.Builder()
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
		partEntity.setCost( 543.78F );
		Assertions.assertEquals( 543.78F, partEntity.getCost(), 0.01 );
		partEntity.setPrice( 543.78F );
		Assertions.assertEquals( 543.78F, partEntity.getPrice() );
		partEntity.setStockAvailable( 8 );
		Assertions.assertEquals( 8, partEntity.getStockAvailable() );
		partEntity.setStockLevel( 8 );
		Assertions.assertEquals( 8, partEntity.getStockLevel() );
		partEntity.setActive( true );
		Assertions.assertTrue( partEntity.isActive() );
	}

	@Test
	public void toStringContract() {
		// Given
		final PartEntity part = new PartEntity.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527b" ) )
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
		final String expected = "{\"id\":\"112ad653-9eea-4124-ab20-9fcd92d0527b\",\"label\":\"-TEST_PART_LABEL-\",\"project\":\"<DEFAULT>\",\"description\":\"-TEST_PART_DESCRIPTION-\",\"material\":\"PLA\",\"color\":\"VERDE-T\",\"weight\":1,\"buildTime\":60,\"cost\":0.76,\"price\":2.0,\"stockLevel\":4,\"stockAvailable\":4,\"imagePath\":\"https:\\/\\/ibb.co\\/3dGbsRh\",\"modelPath\":\"pieza3.STL\",\"active\":false}";
		final String obtained = part.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
