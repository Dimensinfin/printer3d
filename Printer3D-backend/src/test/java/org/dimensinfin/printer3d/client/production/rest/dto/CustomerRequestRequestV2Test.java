package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_CUSTOMER;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_TOTAL;

public class CustomerRequestRequestV2Test {
	private final List<RequestItem> contents = new ArrayList<>();

	@BeforeEach
	public void beforeEach() {
		final RequestItem requestItem = Mockito.mock( RequestItem.class );
		this.contents.clear();
		this.contents.add( requestItem );
	}

	@Test
	public void buildContract() {
		final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withContents( this.contents )
				.withTotalAmount( TEST_REQUEST_TOTAL )
				.withPaid( true )
				.build();
		Assertions.assertNotNull( request );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new CustomerRequestRequestV2.Builder()
					.withId( null )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withContents( this.contents )
					.withTotalAmount( TEST_REQUEST_TOTAL )
					.withPaid( true )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new CustomerRequestRequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( null )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withContents( this.contents )
					.withTotalAmount( TEST_REQUEST_TOTAL )
					.withPaid( true )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new CustomerRequestRequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withContents( null )
					.withTotalAmount( TEST_REQUEST_TOTAL )
					.withPaid( true )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new CustomerRequestRequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withContents( this.contents )
					.withTotalAmount( null )
					.withPaid( true )
					.build();
		} );
	}

	@Test
	public void buildFailure_noContent() {
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withContents( new ArrayList<>() )
					.withTotalAmount( TEST_REQUEST_TOTAL )
					.withPaid( true )
					.build();
		} );
	}

	@Test
	public void buildUnset() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new CustomerRequestRequestV2.Builder()
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withContents( this.contents )
					.withTotalAmount( TEST_REQUEST_TOTAL )
					.withPaid( true )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new CustomerRequestRequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withContents( this.contents )
					.withTotalAmount( TEST_REQUEST_TOTAL )
					.withPaid( true )
					.build();
		} );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			new CustomerRequestRequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withTotalAmount( TEST_REQUEST_TOTAL )
					.withPaid( true )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new CustomerRequestRequestV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withRequestDate( TEST_REQUEST_DATE_STRING )
					.withContents( this.contents )
					.withPaid( true )
					.build();
		} );
	}

	@Test
	public void getterContract() {
		// Given
		final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withContents( this.contents )
				.withTotalAmount( TEST_REQUEST_TOTAL )
				.withPaid( true )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_REQUEST_ID.toString(), request.getId().toString() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, request.getLabel() );
		Assertions.assertNull( request.getCustomer() );
		request.setCustomer( TEST_REQUEST_CUSTOMER );
		Assertions.assertEquals( TEST_REQUEST_CUSTOMER, request.getCustomer() );
		Assertions.assertEquals( TEST_REQUEST_DATE.toString(), request.getRequestDate() );
		Assertions.assertEquals( TEST_REQUEST_STATE, RequestState.OPEN );
		Assertions.assertNotNull( request.getContents() );
		Assertions.assertEquals( 1, request.getContents().size() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, request.getTotal() );
	}
}
