package org.dimensinfin.printer3d.backend.production.request.persistence;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestEntityTest {
	@Test
	public void buildContract() {
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		Assertions.assertNotNull( requestEntity );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntity.Builder()
					.withId( null )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withPartList( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntity.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( null )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withPartList( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntity.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( null )
					.withState( TEST_REQUEST_STATE )
					.withPartList( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntity.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( null )
					.withPartList( new ArrayList<>() )
					.build();
		} );
	}

	@Test
	public void buildUnset() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntity.Builder()
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withPartList( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntity.Builder()
					.withId( TEST_REQUEST_ID )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withPartList( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntity.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withState( TEST_REQUEST_STATE )
					.withPartList( new ArrayList<>() )
					.build();
		} );
	}

	@Test
	public void close() {
		// Test
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		Assertions.assertEquals( RequestState.OPEN, requestEntity.getState() );
		requestEntity.close();
		// Assertions
		Assertions.assertEquals( RequestState.CLOSE, requestEntity.getState() );
	}

	@Test
	public void signalCompleted() {
		// Test
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		Assertions.assertEquals( RequestState.OPEN, requestEntity.getState() );
		requestEntity.signalCompleted();
		// Assertions
		Assertions.assertEquals( RequestState.COMPLETED, requestEntity.getState() );
	}
}
