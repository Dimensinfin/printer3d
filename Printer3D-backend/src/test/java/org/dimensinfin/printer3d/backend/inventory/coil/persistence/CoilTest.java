package org.dimensinfin.printer3d.backend.inventory.coil.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_WEIGHT;

public class CoilTest {
	@Test
	public void buildContract() {
		final Coil coil = new Coil.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.build();
		Assertions.assertNotNull( coil );
	}

	@Test
	public void buildEmpty() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withMaterial( TEST_COIL_MATERIAL )
					.withTradeMark( TEST_COIL_TRADE_MARK )
					.withColor( TEST_COIL_COLOR )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( TEST_COIL_ID )
					.withTradeMark( TEST_COIL_TRADE_MARK )
					.withColor( TEST_COIL_COLOR )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( TEST_COIL_ID )
					.withMaterial( TEST_COIL_MATERIAL )
					.withTradeMark( TEST_COIL_TRADE_MARK )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( null )
					.withMaterial( TEST_COIL_MATERIAL )
					.withTradeMark( TEST_COIL_TRADE_MARK )
					.withColor( TEST_COIL_COLOR )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( TEST_COIL_ID )
					.withMaterial( null )
					.withTradeMark( TEST_COIL_TRADE_MARK )
					.withColor( TEST_COIL_COLOR )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( TEST_COIL_ID )
					.withMaterial( TEST_COIL_MATERIAL )
					.withTradeMark( null )
					.withColor( TEST_COIL_COLOR )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( TEST_COIL_ID )
					.withMaterial( TEST_COIL_MATERIAL )
					.withTradeMark( TEST_COIL_TRADE_MARK )
					.withColor( null )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( Coil.class )
				.withIgnoredFields( "id", "destructionTime" )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final Coil coil = new Coil.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withLabel( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_COIL_ID, coil.getId() );
		Assertions.assertEquals( TEST_COIL_MATERIAL, coil.getMaterial() );
		Assertions.assertEquals( TEST_COIL_TRADE_MARK, coil.getTradeMark() );
		Assertions.assertEquals( TEST_COIL_COLOR, coil.getLabel() );
		Assertions.assertEquals( TEST_COIL_WEIGHT, coil.getWeight() );
		Assertions.assertEquals( TEST_COIL_COLOR, coil.getColor() );
		Assertions.assertEquals( TEST_COIL_ACTIVE, coil.getActive() );
		Assertions.assertNull( coil.getDestructionTime() );
	}

	@Test
	public void setterContract() {
		// Given
		final Coil coil = new Coil.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withLabel( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_COIL_TRADE_MARK, coil.getTradeMark() );
		coil.setTradeMark( "-NET-TRADE-MARK-" );
		Assertions.assertEquals( "-NET-TRADE-MARK-", coil.getTradeMark() );

		Assertions.assertEquals( TEST_COIL_WEIGHT, coil.getWeight() );
		coil.setWeight( 543 );
		Assertions.assertEquals( 543, coil.getWeight() );

		Assertions.assertEquals( TEST_COIL_COLOR, coil.getLabel() );
		coil.setLabel( "-COLOR-" );
		Assertions.assertEquals( "-COLOR-", coil.getLabel() );
		Assertions.assertEquals( TEST_COIL_COLOR, coil.getLabel() );

		Assertions.assertEquals( TEST_COIL_ACTIVE, coil.getActive() );
		coil.setActive( false );
		Assertions.assertEquals( false, coil.getActive() );
	}

	@Test
	public void toStringContract() {
		// Given
		final Coil coil = new Coil.Builder()
				.withId( UUID.fromString( "55236c12-1322-4e32-8285-dac2a45a65fe" ) )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.withLabel( TEST_COIL_COLOR )
				.build();
		final String expected = "{\"id\":\"55236c12-1322-4e32-8285-dac2a45a65fe\",\"material\":\"PLA\",\"tradeMark\":\"EOLAS\",\"color\":\"-COLOR-\",\"weight\":750,\"label\":\"-COLOR-\",\"active\":true}";
		final String obtained = coil.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
