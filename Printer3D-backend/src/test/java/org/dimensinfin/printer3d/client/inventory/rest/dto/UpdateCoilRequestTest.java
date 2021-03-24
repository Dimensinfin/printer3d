package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;

public class UpdateCoilRequestTest {
	@Test
	public void buildContract() {
		final UpdateCoilRequest updateCoilRequest = new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withWeight( 200 )
				.withLabel( TEST_COIL_COLOR )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		Assertions.assertNotNull( updateCoilRequest );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( UpdateCoilRequest.class ).withIgnoredFields( "id" )
				.suppress( Warning.STRICT_INHERITANCE )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final UpdateCoilRequest updateCoilRequest = new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withWeight( 200 )
				.withLabel( TEST_COIL_COLOR )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		// Assertions
		Assertions.assertEquals( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ).toString(), updateCoilRequest.getId().toString() );
		Assertions.assertEquals( 200, updateCoilRequest.getWeight() );
		Assertions.assertEquals( TEST_COIL_COLOR, updateCoilRequest.getLabel() );
		Assertions.assertEquals( TEST_COIL_ACTIVE, updateCoilRequest.getActive() );
	}

	@Test
	public void toStringContract() {
		// Given
		final UpdateCoilRequest updateCoilRequest = new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withWeight( 200 )
				.withLabel( TEST_COIL_COLOR )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		// Test
		final String expected = "{\"id\":\"47461aa3-24f0-4cc5-a335-53e8bb61accc\",\"weight\":200,\"color\":\"-COLOR-\",\"active\":false}";
		final String obtained = updateCoilRequest.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
