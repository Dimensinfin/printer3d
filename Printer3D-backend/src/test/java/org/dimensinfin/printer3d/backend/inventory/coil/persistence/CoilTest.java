package org.dimensinfin.printer3d.backend.inventory.coil.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_WEIGHT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_ROLL_MATERIAL;

public class CoilTest {
	@Test
	public void buildContract() {
		final Coil coil = new Coil.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_ROLL_MATERIAL )
				.withColor( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.build();
		Assertions.assertNotNull( coil );
	}

	@Test
	public void buildEmpty() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withMaterial( TEST_ROLL_MATERIAL )
					.withColor( TEST_COIL_COLOR )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( TEST_COIL_ID )
					.withColor( TEST_COIL_COLOR )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( TEST_COIL_ID )
					.withMaterial( TEST_ROLL_MATERIAL )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( null )
					.withMaterial( TEST_ROLL_MATERIAL )
					.withColor( TEST_COIL_COLOR )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( TEST_COIL_ID )
					.withMaterial( null )
					.withColor( TEST_COIL_COLOR )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Coil.Builder()
					.withId( TEST_COIL_ID )
					.withMaterial( TEST_ROLL_MATERIAL )
					.withColor( null )
					.withWeight( TEST_COIL_WEIGHT )
					.build();
		} );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( Coil.class )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final Coil coil = new Coil.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_ROLL_MATERIAL )
				.withColor( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_COIL_ID, coil.getId() );
		Assertions.assertEquals( TEST_ROLL_MATERIAL, coil.getMaterial() );
		Assertions.assertEquals( TEST_COIL_COLOR, coil.getColor() );
		Assertions.assertEquals( TEST_COIL_WEIGHT, coil.getWeight() );
	}

	@Test
	public void toStringContract() {
		// Given
		final Coil coil = new Coil.Builder()
				.withId( UUID.fromString( "55236c12-1322-4e32-8285-dac2a45a65fe" ) )
				.withMaterial( TEST_ROLL_MATERIAL )
				.withColor( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.build();
		final String expected = "{\"id\":\"55236c12-1322-4e32-8285-dac2a45a65fe\",\"material\":\"PLA\",\"color\":\"-COLOR-\",\"weight\":750}";
		final String obtained = coil.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
