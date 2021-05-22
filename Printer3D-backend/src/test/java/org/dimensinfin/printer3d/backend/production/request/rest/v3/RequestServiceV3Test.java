package org.dimensinfin.printer3d.backend.production.request.rest.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_CUSTOMER;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_IVA;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_TOTAL;

public class RequestServiceV3Test {
	private final List<RequestItem> contents = new ArrayList<>();
	private PartRepository partRepository;
	private ModelRepository modelRepository;
	private RequestsRepositoryV2 requestsRepositoryV2;

	@BeforeEach
	public void beforeEach() {
		final RequestItem requestItem = Mockito.mock( RequestItem.class );
		this.contents.clear();
		this.contents.add( requestItem );

		this.partRepository = Mockito.mock( PartRepository.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
	}

	@Test
	public void constructionContract() {
		final RequestServiceV3 requestServiceV3 = new RequestServiceV3( this.partRepository, this.modelRepository, this.requestsRepositoryV2 );
		Assertions.assertNotNull( requestServiceV3 );
	}

	@Test
	public void deleteRequest() {
		// Given
		final UUID requestId = UUID.fromString( "afdf861b-f2dd-42d8-a5db-f99a864e111b" );
		final RequestEntityV2 requestEntityV2Completed = new RequestEntityV2.Builder()
				.withId( requestId )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.COMPLETED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2Completed ) );
		Mockito.when( this.requestsRepositoryV2.save( Mockito.any( RequestEntityV2.class ) ) ).thenAnswer( i -> i.getArguments()[0] );
		// Test
		final RequestServiceV3 requestServiceV3 = new RequestServiceV3( this.partRepository, this.modelRepository, this.requestsRepositoryV2 );
		final CustomerRequestResponseV2 obtained = requestServiceV3.deleteRequest( requestId );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( requestId, obtained.getId() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_CUSTOMER, obtained.getCustomer() );
		Assertions.assertEquals( TEST_REQUEST_DATE, obtained.getRequestDate() );
		Assertions.assertEquals( RequestState.DELETED, obtained.getState() );
		Assertions.assertNotNull( obtained.getDeliveredDate() );
		Assertions.assertNull( obtained.getPaymentDate() );
		Assertions.assertFalse( obtained.isPaid() );
		Assertions.assertNotNull( obtained.getContents() );
		Assertions.assertEquals( 1, obtained.getContents().size() );
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, obtained.getAmount() );
		Assertions.assertEquals( TEST_REQUEST_IVA, obtained.getIva() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, obtained.getTotal() );
	}

	@Test
	public void deleteRequest_invalidstate() {
		// Given
		final UUID requestId = UUID.fromString( "afdf861b-f2dd-42d8-a5db-f99a864e111b" );
		final RequestEntityV2 requestEntityV2Delivered = new RequestEntityV2.Builder()
				.withId( requestId )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.DELIVERED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2Delivered ) );
		// Test
		final RequestServiceV3 requestServiceV3 = new RequestServiceV3( this.partRepository, this.modelRepository, this.requestsRepositoryV2 );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			requestServiceV3.deleteRequest( requestId );
		} );
	}

	@Test
	public void deleteRequest_notfound() {
		// Given
		final UUID requestId = UUID.fromString( "afdf861b-f2dd-42d8-a5db-f99a864e111b" );
		final RequestEntityV2 requestEntityV2Delivered = new RequestEntityV2.Builder()
				.withId( requestId )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.DELIVERED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Test
		final RequestServiceV3 requestServiceV3 = new RequestServiceV3( this.partRepository, this.modelRepository, this.requestsRepositoryV2 );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			requestServiceV3.deleteRequest( requestId );
		} );
	}

	@Test
	public void deliverRequest_notpaid() {
		// Given
		final UUID requestId = UUID.fromString( "afdf861b-f2dd-42d8-a5db-f99a864e111b" );
		final RequestEntityV2 requestEntityV2Completed = new RequestEntityV2.Builder()
				.withId( requestId )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.COMPLETED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2Completed ) );
		Mockito.when( this.requestsRepositoryV2.save( Mockito.any( RequestEntityV2.class ) ) ).thenAnswer( i -> i.getArguments()[0] );
		// Test
		final RequestServiceV3 requestServiceV3 = new RequestServiceV3( this.partRepository, this.modelRepository, this.requestsRepositoryV2 );
		final CustomerRequestResponseV2 obtained = requestServiceV3.deliverRequest( requestId );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( requestId, obtained.getId() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_CUSTOMER, obtained.getCustomer() );
		Assertions.assertEquals( TEST_REQUEST_DATE, obtained.getRequestDate() );
		Assertions.assertEquals( RequestState.DELIVERED, obtained.getState() );
		Assertions.assertNotNull( obtained.getDeliveredDate() );
		Assertions.assertNull( obtained.getPaymentDate() );
		Assertions.assertFalse( obtained.isPaid() );
		Assertions.assertNotNull( obtained.getContents() );
		Assertions.assertEquals( 1, obtained.getContents().size() );
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, obtained.getAmount() );
		Assertions.assertEquals( TEST_REQUEST_IVA, obtained.getIva() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, obtained.getTotal() );
	}

	@Test
	public void deliverRequest_paid() {
		// Given
		final UUID requestId = UUID.fromString( "afdf861b-f2dd-42d8-a5db-f99a864e111b" );
		final RequestEntityV2 requestEntityV2Completed = new RequestEntityV2.Builder()
				.withId( requestId )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.COMPLETED )
				.withContents( this.contents )
				.withPaid( true )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2Completed ) );
		Mockito.when( this.requestsRepositoryV2.save( Mockito.any( RequestEntityV2.class ) ) ).thenAnswer( i -> i.getArguments()[0] );
		// Test
		final RequestServiceV3 requestServiceV3 = new RequestServiceV3( this.partRepository, this.modelRepository, this.requestsRepositoryV2 );
		final CustomerRequestResponseV2 obtained = requestServiceV3.deliverRequest( requestId );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( requestId, obtained.getId() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_CUSTOMER, obtained.getCustomer() );
		Assertions.assertEquals( TEST_REQUEST_DATE, obtained.getRequestDate() );
		Assertions.assertEquals( RequestState.CLOSED, obtained.getState() );
		Assertions.assertNotNull( obtained.getDeliveredDate() );
		Assertions.assertNotNull( obtained.getPaymentDate() );
		Assertions.assertTrue( obtained.isPaid() );
		Assertions.assertNotNull( obtained.getContents() );
		Assertions.assertEquals( 1, obtained.getContents().size() );
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, obtained.getAmount() );
		Assertions.assertEquals( TEST_REQUEST_IVA, obtained.getIva() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, obtained.getTotal() );
	}

	@Test
	public void getClosedRequestsV3() {
		// Given
		final RequestEntityV2 requestEntityV2Open = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.OPEN )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final RequestEntityV2 requestEntityV2Completed = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.COMPLETED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final RequestEntityV2 requestEntityV2Delivered = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.DELIVERED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final RequestEntityV2 requestEntityV2Closed = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.CLOSED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final List<RequestEntityV2> requestList = new ArrayList<>();
		requestList.add( requestEntityV2Open );
		requestList.add( requestEntityV2Completed );
		requestList.add( requestEntityV2Delivered );
		requestList.add( requestEntityV2Closed );
		// When
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestList );
		// Test
		final RequestServiceV3 requestServiceV3 = new RequestServiceV3( this.partRepository, this.modelRepository, this.requestsRepositoryV2 );
		final List<CustomerRequestResponseV2> obtained = requestServiceV3.getClosedRequestsV3();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.size() );
	}

	@Test
	public void getOpenRequestsV3() {
		// Given
		final RequestEntityV2 requestEntityV2Open = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.OPEN )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final RequestEntityV2 requestEntityV2Completed = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.COMPLETED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final RequestEntityV2 requestEntityV2Delivered = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.DELIVERED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final RequestEntityV2 requestEntityV2Closed = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.CLOSED )
				.withContents( this.contents )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final List<RequestEntityV2> requestList = new ArrayList<>();
		requestList.add( requestEntityV2Open );
		requestList.add( requestEntityV2Completed );
		requestList.add( requestEntityV2Delivered );
		requestList.add( requestEntityV2Closed );
		// Test
		final RequestServiceV3 requestServiceV3 = new RequestServiceV3( this.partRepository, this.modelRepository, this.requestsRepositoryV2 );
		final List<CustomerRequestResponseV2> obtained = requestServiceV3.getOpenRequestsV3();
		// Assertions
		Assertions.assertNotNull( obtained );
	}
}