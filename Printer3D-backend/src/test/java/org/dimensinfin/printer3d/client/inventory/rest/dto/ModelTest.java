package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_STOCK_LEVEL;

public class ModelTest {
	@Test
	public void buildContract() {
		final Model modelRequest = new Model.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.withPartIdList( new ArrayList<>() )
				.build();
		Assertions.assertNotNull( modelRequest );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Model.Builder()
					.withId( null )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.withPartIdList( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Model.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( null )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.withPartIdList( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Model.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( null )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.withPartIdList( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Model.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( null )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.withPartIdList( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Model.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.withPartIdList( null )
					.build();
		} );
	}
	@Test
	public void buildEmpty() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Model.Builder().withId( null )
					.withLabel( TEST_MODEL_LABEL )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Model.Builder()
					.withId( TEST_MODEL_ID ).withLabel( null )
					.withPrice( TEST_MODEL_PRICE )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Model.Builder()
					.withId( TEST_MODEL_ID )
					.withLabel( TEST_MODEL_LABEL )
					.withStockLevel( TEST_MODEL_STOCK_LEVEL )
					.withImagePath( TEST_MODEL_IMAGE_PATH )
					.withActive( TEST_MODEL_ACTIVE )
					.build();
		} );
	}

	@Test
	public void getterContract() {
		// Given
		final Model modelRequest = new Model.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_MODEL_ID.toString(), modelRequest.getId().toString() );
		Assertions.assertEquals( TEST_MODEL_LABEL, modelRequest.getLabel() );
		Assertions.assertEquals( TEST_MODEL_IMAGE_PATH, modelRequest.getImagePath() );
		Assertions.assertEquals( TEST_MODEL_PRICE, modelRequest.getPrice(), 0.1 );
		Assertions.assertEquals( TEST_MODEL_STOCK_LEVEL, modelRequest.getStockLevel() );
		Assertions.assertEquals( TEST_MODEL_ACTIVE, modelRequest.isActive() );
	}
}
