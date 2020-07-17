package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_CLOSED_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestV2Test {
	@Test
	public void buildContract() {
		final RequestV2 requestEntityV2 = new RequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withState( TEST_REQUEST_STATE )
				.withAmount( TEST_REQUEST_AMOUNT )
				.withClosedDate( TEST_REQUEST_CLOSED_DATE.toString() )
				.withContents( new ArrayList<>() )
				.build();
		Assertions.assertNotNull( requestEntityV2 );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestV2.Builder()
					.withId( null )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( null )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( null )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withState( null )
					.withContents( new ArrayList<>() )
					.build();
		} );
	}

	@Test
	public void buildUnset() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestV2.Builder()
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.build();
		} );
	}
}
