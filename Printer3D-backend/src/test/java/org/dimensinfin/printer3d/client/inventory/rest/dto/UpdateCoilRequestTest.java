package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class UpdateCoilRequestTest {
	@Test
	public void buildContract() {
		final UpdateCoilRequest updateCoilRequest = new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withWeight( 200 )
				.build();
		Assertions.assertNotNull( updateCoilRequest );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new UpdateCoilRequest.Builder()
					.withId( null )
					.withWeight( 200 )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new UpdateCoilRequest.Builder()
					.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
					.withWeight( null )
					.build();
		} );
	}
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( UpdateCoilRequest.class ).withIgnoredFields( "id" )
				.suppress( Warning.STRICT_INHERITANCE)
				.verify();
	}

	@Test
	public void toStringContract() {
		// Given
		final UpdateCoilRequest updateCoilRequest = new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withWeight( 200 )
				.build();
		// Test
		final String expected = "{\"id\":\"47461aa3-24f0-4cc5-a335-53e8bb61accc\",\"weight\":200}";
		final String obtained = updateCoilRequest.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}

	@Test
	public void getterContract() {
		// Given
		final UpdateCoilRequest updateCoilRequest = new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withWeight( 200 )
				.build();
		// Assertions
		Assertions.assertEquals( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ).toString(), updateCoilRequest.getId().toString() );
		Assertions.assertEquals( 200, updateCoilRequest.getWeight() );
	}
}
