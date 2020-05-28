package org.dimensinfin.printer3d.backend.inventory.roll.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RollConstants.TEST_ROLL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RollConstants.TEST_ROLL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RollConstants.TEST_ROLL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RollConstants.TEST_ROLL_WEIGHT;

public class RollTest {
	@Test
	public void buildContract() {
		final Roll roll = new Roll.Builder()
				.withId( TEST_ROLL_ID )
				.withMaterial( TEST_ROLL_MATERIAL )
				.withColor( TEST_ROLL_COLOR )
				.withWeight( TEST_ROLL_WEIGHT )
				.build();
		Assertions.assertNotNull( roll );
	}

	@Test
	public void buildEmpty() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Roll.Builder()
					.withMaterial( TEST_ROLL_MATERIAL )
					.withColor( TEST_ROLL_COLOR )
					.withWeight( TEST_ROLL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Roll.Builder()
					.withId( TEST_ROLL_ID )
					.withColor( TEST_ROLL_COLOR )
					.withWeight( TEST_ROLL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Roll.Builder()
					.withId( TEST_ROLL_ID )
					.withMaterial( TEST_ROLL_MATERIAL )
					.withWeight( TEST_ROLL_WEIGHT )
					.build();
		} );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Roll.Builder()
					.withId( null )
					.withMaterial( TEST_ROLL_MATERIAL )
					.withColor( TEST_ROLL_COLOR )
					.withWeight( TEST_ROLL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Roll.Builder()
					.withId( TEST_ROLL_ID )
					.withMaterial( null )
					.withColor( TEST_ROLL_COLOR )
					.withWeight( TEST_ROLL_WEIGHT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Roll.Builder()
					.withId( TEST_ROLL_ID )
					.withMaterial( TEST_ROLL_MATERIAL )
					.withColor( null )
					.withWeight( TEST_ROLL_WEIGHT )
					.build();
		} );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( Roll.class )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final Roll roll = new Roll.Builder()
				.withId( TEST_ROLL_ID )
				.withMaterial( TEST_ROLL_MATERIAL )
				.withColor( TEST_ROLL_COLOR )
				.withWeight( TEST_ROLL_WEIGHT )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_ROLL_ID, roll.getId() );
		Assertions.assertEquals( TEST_ROLL_MATERIAL, roll.getMaterial() );
		Assertions.assertEquals( TEST_ROLL_COLOR, roll.getColor() );
		Assertions.assertEquals( TEST_ROLL_WEIGHT, roll.getWeight() );
	}

	@Test
	public void toStringContract() {
		// Given
		final Roll roll = new Roll.Builder()
				.withId( UUID.fromString( "55236c12-1322-4e32-8285-dac2a45a65fe" ) )
				.withMaterial( TEST_ROLL_MATERIAL )
				.withColor( TEST_ROLL_COLOR )
				.withWeight( TEST_ROLL_WEIGHT )
				.build();
		final String expected = "{\"id\":\"55236c12-1322-4e32-8285-dac2a45a65fe\",\"material\":\"PLA\",\"color\":\"-COLOR-\",\"weight\":750}";
		final String obtained = roll.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
