package org.dimensinfin.printer3d.backend.part.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;

public class PartTest {
	@Test
	public void buildContract() {
		final Part part = new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
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
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( null )
					.withDescription( TEST_PART_DESCRIPTION )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withCost( null )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withCost( TEST_PART_COST )
					.withPrice( null )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( null )
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
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withDescription( TEST_PART_DESCRIPTION )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withPrice( TEST_PART_PRICE )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withCost( TEST_PART_COST )
					.withStockLevel( TEST_PART_STOCK_LEVEL )
					.withActive( false )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Part.Builder()
					.withId( TEST_PART_ID )
					.withLabel( TEST_PART_LABEL )
					.withDescription( TEST_PART_DESCRIPTION )
					.withCost( TEST_PART_COST )
					.withPrice( TEST_PART_PRICE )
					.withActive( false )
					.build();
		} );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( Part.class )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final Part part = new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withActive( false )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_PART_ID.toString(), part.getId().toString() );
		Assertions.assertEquals( TEST_PART_LABEL, part.getLabel() );
		Assertions.assertEquals( TEST_PART_DESCRIPTION, part.getDescription() );
		Assertions.assertEquals( TEST_PART_COST, part.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE, part.getPrice() );
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL, part.getStockLevel() );
		Assertions.assertFalse( part.isActive() );
	}

	@Test
	public void toStringContract() {
		// Given
		final Part part = new Part.Builder()
				.withId( UUID.fromString("112ad653-9eea-4124-ab20-9fcd92d0527b") )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withActive( false )
				.build();
		// Test
		final String expected = "{\"id\":\"112ad653-9eea-4124-ab20-9fcd92d0527b\",\"label\":\"-TEST_PART_LABEL-\",\"description\":\"-TEST_PART_DESCRIPTION-\",\"cost\":0.76,\"price\":2.0,\"stockLevel\":4,\"active\":false}";
		final String obtained = part.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
