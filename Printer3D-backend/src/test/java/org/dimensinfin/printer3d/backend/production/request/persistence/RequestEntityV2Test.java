package org.dimensinfin.printer3d.backend.production.request.persistence;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_CUSTOMER;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_IVA;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_TOTAL;

public class RequestEntityV2Test {
	private final List<RequestItem> contents = new ArrayList<>();

	@BeforeEach
	public void beforeEach() {
		final RequestItem requestItem = Mockito.mock( RequestItem.class );
		this.contents.clear();
		this.contents.add( requestItem );
	}

	@Test
	public void buildContract() {
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build()
				.updateAmounts();
		Assertions.assertNotNull( requestEntityV2 );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( null )
					.withLabel( TEST_REQUEST_LABEL )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( this.contents )
					.withPaid( false )
					.withTotal( TEST_REQUEST_TOTAL )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( null )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( this.contents )
					.withPaid( false )
					.withTotal( TEST_REQUEST_TOTAL )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( null )
					.withState( TEST_REQUEST_STATE )
					.withContents( this.contents )
					.withPaid( false )
					.withTotal( TEST_REQUEST_TOTAL )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( null )
					.withContents( this.contents )
					.withPaid( false )
					.withTotal( TEST_REQUEST_TOTAL )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( null )
					.withPaid( false )
					.withTotal( TEST_REQUEST_TOTAL )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( this.contents )
					.withPaid( false )
					.withTotal( null )
					.build();
		} );
	}

	@Test
	public void buildMissing() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withLabel( TEST_REQUEST_LABEL )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( this.contents )
					.withPaid( false )
					.withTotal( TEST_REQUEST_TOTAL )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( this.contents )
					.withPaid( false )
					.withTotal( TEST_REQUEST_TOTAL )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withState( TEST_REQUEST_STATE )
					.withContents( this.contents )
					.withPaid( false )
					.withTotal( TEST_REQUEST_TOTAL )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( this.contents )
					.withPaid( false )
					.build();
		} );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			new RequestEntityV2.Builder()
					.withId( TEST_REQUEST_ID )
					.withLabel( TEST_REQUEST_LABEL )
					.withCustomerData( TEST_REQUEST_CUSTOMER )
					.withRequestDate( TEST_REQUEST_DATE )
					.withState( TEST_REQUEST_STATE )
					.withContents( new ArrayList<>() )
					.withPaid( false )
					.withTotal( TEST_REQUEST_TOTAL )
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
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build()
				.updateAmounts();
		Assertions.assertEquals( RequestState.OPEN, requestEntityV2.getState() );
		requestEntityV2.close();
		// Assertions
		Assertions.assertEquals( RequestState.CLOSED, requestEntityV2.getState() );
	}

	@Test
	public void getterContract() {
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build()
				.updateAmounts();
		// Assertions
		Assertions.assertEquals( TEST_REQUEST_ID, requestEntityV2.getId() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, requestEntityV2.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_CUSTOMER, requestEntityV2.getCustomer() );
		Assertions.assertEquals( TEST_REQUEST_DATE, requestEntityV2.getRequestDate() );
		Assertions.assertEquals( RequestState.OPEN, requestEntityV2.getState() );
		Assertions.assertEquals( null, requestEntityV2.getDeliveredDate() );
		Assertions.assertEquals( null, requestEntityV2.getPaymentDate() );
		Assertions.assertFalse( requestEntityV2.isPaid() );
		Assertions.assertNotNull( requestEntityV2.getContents() );
		Assertions.assertEquals( 1, requestEntityV2.getContents().size() );
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, requestEntityV2.getAmount() );
		Assertions.assertEquals( TEST_REQUEST_IVA, requestEntityV2.getIva() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, requestEntityV2.getTotal() );
	}

	@Test
	public void setterContract() {
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// Assertions
		requestEntityV2.setAmount( 123.0F );
		Assertions.assertEquals( 123.0F, requestEntityV2.getAmount() );
		requestEntityV2.setIva( 12.0F );
		Assertions.assertEquals( 12.0F, requestEntityV2.getIva() );
		requestEntityV2.setTotal( TEST_REQUEST_TOTAL + 10 );
		Assertions.assertEquals( TEST_REQUEST_TOTAL + 10, requestEntityV2.getTotal() );
		requestEntityV2.setPaid( true );
		Assertions.assertTrue( requestEntityV2.isPaid() );
	}

	@Test
	public void signalCompleted() {
		// Test
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// Assertions
		Assertions.assertEquals( RequestState.OPEN, requestEntityV2.getState() );
		Assertions.assertNull( requestEntityV2.getDeliveredDate() );
		requestEntityV2.signalCompleted();
		Assertions.assertEquals( RequestState.COMPLETED, requestEntityV2.getState() );
	}

	@Test
	public void signalDelivered_notpaid() {
		// Test
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// Assertions
		Assertions.assertEquals( RequestState.OPEN, requestEntityV2.getState() );
		Assertions.assertNull( requestEntityV2.getDeliveredDate() );
		requestEntityV2.signalDelivered();
		Assertions.assertEquals( RequestState.DELIVERED, requestEntityV2.getState() );
		Assertions.assertNotNull( requestEntityV2.getDeliveredDate() );
	}

	@Test
	public void signalDelivered_paid() {
		// Test
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( this.contents )
				.withPaid( true )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// Assertions
		Assertions.assertEquals( RequestState.OPEN, requestEntityV2.getState() );
		Assertions.assertNull( requestEntityV2.getDeliveredDate() );
		requestEntityV2.signalDelivered();
		Assertions.assertEquals( RequestState.CLOSED, requestEntityV2.getState() );
		Assertions.assertNotNull( requestEntityV2.getDeliveredDate() );
		Assertions.assertNotNull( requestEntityV2.getPaymentDate() );
	}

	@Test
	public void signalPaid() {
		// Test
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// Assertions
		Assertions.assertEquals( RequestState.OPEN, requestEntityV2.getState() );
		Assertions.assertEquals( null, requestEntityV2.getPaymentDate() );
		requestEntityV2.signalPaid();
		Assertions.assertNotNull( requestEntityV2.getPaymentDate() );
		Assertions.assertEquals( RequestState.CLOSED, requestEntityV2.getState() );
		Assertions.assertTrue( requestEntityV2.isPaid() );
	}
}
