package org.dimensinfin.printer3d.backend.production.request.persistence;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

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
