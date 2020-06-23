package org.dimensinfin.printer3d.backend.inventory.model.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_STOCK_LEVEL;

public class ModelEntityTest {
	@Test
	public void addPart() {
		// Given
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//				.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		// Assertions
		Assertions.assertNotNull( modelEntity.getPartIdList() );
		Assertions.assertEquals( 0, modelEntity.getPartIdList().size() );
		// Test
		modelEntity.addPart( UUID.randomUUID() );
		modelEntity.addPart( UUID.randomUUID() );
		// Assertions
		Assertions.assertNotNull( modelEntity.getPartIdList() );
		Assertions.assertEquals( 2, modelEntity.getPartIdList().size() );
	}

	@Test
	public void buildContract() {
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//				.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		Assertions.assertNotNull( modelEntity );
	}

	@Test
	public void buildEmpty() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new ModelEntity.Builder()
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//					.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new ModelEntity.Builder()
					.withId( TEST_MODEL_ID )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//					.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new ModelEntity.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( null )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//					.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new ModelEntity.Builder()
					.withId( null )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//					.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new ModelEntity.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( null )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//					.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new ModelEntity.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( null )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//					.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new ModelEntity.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( null )
//					.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new ModelEntity.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//					.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( null )
					.build();
		} );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( ModelEntity.class )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//				.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_MODEL_ID.toString(), modelEntity.getId().toString() );
		Assertions.assertEquals( TEST_MODEL_LABEL, modelEntity.getLabel() );
		Assertions.assertEquals( TEST_MODEL_IMAGE_PATH, modelEntity.getImagePath() );
		Assertions.assertEquals( TEST_MODEL_PRICE, modelEntity.getPrice(), 0.1 );
		Assertions.assertEquals( TEST_MODEL_STOCK_LEVEL, modelEntity.getStockLevel() );
//		Assertions.assertEquals( TEST_MODEL_STOCK_AVAILABLE, modelEntity.getStockAvailable() );
		Assertions.assertEquals( TEST_MODEL_ACTIVE, modelEntity.isActive() );
	}

	@Test
	public void removePart() {
		// Given
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//				.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		// Assertions
		Assertions.assertNotNull( modelEntity.getPartIdList() );
		Assertions.assertEquals( 0, modelEntity.getPartIdList().size() );
		// Test
		modelEntity.addPart( UUID.fromString( "dda12806-2a1e-4a13-89c8-3bf0f23e5ffc" ) );
		modelEntity.addPart( UUID.fromString( "2d2a38f0-fc13-4113-a386-4a72aa661686" ) );
		modelEntity.removePart( UUID.fromString( "dda12806-2a1e-4a13-89c8-3bf0f23e5ffc" ) );
		// Assertions
		Assertions.assertNotNull( modelEntity.getPartIdList() );
		Assertions.assertEquals( 1, modelEntity.getPartIdList().size() );
	}

	@Test
	public void toStringContract() {
		// Given
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( UUID.fromString( "dda12806-2a1e-4a13-89c8-3bf0f23e5ffc" ) )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		// Test
		final String expected = "{\"id\":\"dda12806-2a1e-4a13-89c8-3bf0f23e5ffc\",\"label\":\"-TEST_MODEL_LABEL-\",\"partList\":[],\"price\":6.0,\"stockLevel\":2,\"imagePath\":\"-TEST_MODEL_IMAGE_PATH-\",\"active\":true}";
		final String obtained = modelEntity.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
