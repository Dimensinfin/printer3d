package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;

public class RequestItemTest {
	@Test
	public void buildContract() {
		final RequestItem requestItem = new RequestItem.Builder()
				.withItemId( UUID.randomUUID() )
				.withQuantity( 1 )
				.withType( RequestContentType.PART )
				.build();
		Assertions.assertNotNull( requestItem );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestItem.Builder()
					.withItemId( null )
					.withQuantity( 1 )
					.withType( RequestContentType.PART )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestItem.Builder()
					.withItemId( UUID.randomUUID() )
					.withQuantity( null )
					.withType( RequestContentType.PART )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestItem.Builder()
					.withItemId( UUID.randomUUID() )
					.withQuantity( 1 )
					.withType( null )
					.build();
		} );
	}

	@Test
	public void buildFailureNotSet() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestItem.Builder()
					.withQuantity( 1 )
					.withType( RequestContentType.PART )
					.build();
		} );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			new RequestItem.Builder()
					.withItemId( UUID.randomUUID() )
					.withQuantity( 1 )
					.build();
		} );
	}

	@Test
	public void getterContract() {
		// Given
		final RequestItem requestItem = new RequestItem.Builder()
				.withItemId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withQuantity( 2 )
				.withType( RequestContentType.PART )
				.build();
		// Assertions
		Assertions.assertEquals( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ).toString(), requestItem.getItemId().toString() );
		Assertions.assertEquals( 0, requestItem.getMissing() );
		requestItem.setMissing( 3 );
		Assertions.assertEquals( 3, requestItem.getMissing() );
		Assertions.assertEquals( 2, requestItem.getQuantity() );
		Assertions.assertEquals( RequestContentType.PART, requestItem.getType() );

	}
}
