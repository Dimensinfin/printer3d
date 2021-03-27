package org.dimensinfin.printer3d.backend.production.request.persistence;

import java.time.Instant;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_CLOSED_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestEntityV2Test {
	@Test
	public void buildContract() {
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( new ArrayList<>() )
				.withAmount( TEST_REQUEST_AMOUNT )
				.build();
		Assertions.assertNotNull( requestEntityV2 );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( null )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( null )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( null )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( null )
					.withContents( new ArrayList<>() )
					.build();
		} );
	}

	@Test
	public void buildUnset() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
	}

	@Test
	public void close() {
		// Test
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( new ArrayList<>() )
				.build();
		Assertions.assertEquals( RequestState.OPEN, requestEntityV2.getState() );
		requestEntityV2.close();
		// Assertions
		Assertions.assertEquals( RequestState.CLOSE, requestEntityV2.getState() );
	}

	@Test
	public void getterContract() {
		// Given
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( new ArrayList<>() )
				.withAmount( TEST_REQUEST_AMOUNT )
				.withClosedDate( TEST_REQUEST_CLOSED_DATE )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, requestEntityV2.getAmount() );
		requestEntityV2.setAmount( 123.0F );
		Assertions.assertEquals( 123.0F, requestEntityV2.getAmount() );
		Assertions.assertEquals( TEST_REQUEST_CLOSED_DATE.toString(), requestEntityV2.getClosedDate().toString() );
		requestEntityV2.setClosedDate( Instant.parse( "2020-07-31T10:54:00.0Z" ) );
		Assertions.assertEquals( Instant.parse( "2020-07-31T10:54:00.0Z" ).toString(), requestEntityV2.getClosedDate().toString() );
		Assertions.assertNotNull( requestEntityV2.getContents() );
		Assertions.assertEquals( 0, requestEntityV2.getContents().size() );
		Assertions.assertEquals( TEST_REQUEST_ID.toString(), requestEntityV2.getId().toString() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, requestEntityV2.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_DATE.toString(), requestEntityV2.getRequestDate().toString() );
		Assertions.assertEquals( TEST_REQUEST_STATE, requestEntityV2.getState() );
		Assertions.assertFalse( requestEntityV2.isClosed() );
		Assertions.assertTrue( requestEntityV2.isOpen() );
	}

	@Test
	public void signalCompleted() {
		// Test
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( new ArrayList<>() )
				.build();
		Assertions.assertEquals( RequestState.OPEN, requestEntityV2.getState() );
		requestEntityV2.signalCompleted();
		// Assertions
		Assertions.assertEquals( RequestState.COMPLETED, requestEntityV2.getState() );
	}
}
