package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_CUSTOMER;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_IVA;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_TOTAL;

public class RequestControllerV2Test {
	private final List<RequestItem> contents = new ArrayList<>();
	private RequestServiceV2 requestServiceV2;

	@BeforeEach
	public void beforeEach() {
		final RequestItem requestItem = Mockito.mock( RequestItem.class );
		this.contents.clear();
		this.contents.add( requestItem );
		this.requestServiceV2 = Mockito.mock( RequestServiceV2.class );
	}

	@Test
	public void closeRequest() {
		// Given
		final UUID requestId = UUID.randomUUID();
		final CustomerRequestResponseV2 request = Mockito.mock( CustomerRequestResponseV2.class );
		// When
		Mockito.when( this.requestServiceV2.closeRequest( requestId ) ).thenReturn( request );
		Mockito.when( request.getId() ).thenReturn( requestId );
		// Test
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		final ResponseEntity<CustomerRequestResponseV2> obtained = requestControllerV2.closeRequest( requestId );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( requestId.toString(), obtained.getBody().getId().toString() );
	}

	@Test
	public void constructorContract() {
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		Assertions.assertNotNull( requestControllerV2 );
	}

	@Test
	public void getOpenRequests() {
		// Given
		final CustomerRequestResponseV2 request = Mockito.mock( CustomerRequestResponseV2.class );
		final List<CustomerRequestResponseV2> openRequests = new ArrayList<>();
		openRequests.add( request );
		// When
		Mockito.when( this.requestServiceV2.getOpenRequests() ).thenReturn( openRequests );
		// Test
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		final ResponseEntity<List<CustomerRequestResponseV2>> obtained = requestControllerV2.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().size() );
	}

	@Test
	public void newRequest_notpaid() {
		// Given
		final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withContents( this.contents )
				.withTotalAmount( TEST_REQUEST_TOTAL )
				.build();
		final CustomerRequestResponseV2 requestResponse = new CustomerRequestResponseV2()
				.setId( TEST_REQUEST_ID )
				.setLabel( TEST_REQUEST_LABEL )
				.setContents( new ArrayList<>() )
				.setCustomer( TEST_REQUEST_CUSTOMER )
				.setRequestDate( TEST_REQUEST_DATE )
				.setState( RequestState.OPEN )
				.setAmount( TEST_REQUEST_AMOUNT )
				.setIva( TEST_REQUEST_IVA )
				.setTotal( TEST_REQUEST_TOTAL )
				.setPaid( false );
		// When
		Mockito.when( this.requestServiceV2.newRequest( Mockito.any( CustomerRequestRequestV2.class ) ) )
				.thenReturn( requestResponse );
		// Test
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		final ResponseEntity<CustomerRequestResponseV2> obtained = requestControllerV2.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getBody().getLabel() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, obtained.getBody().getTotal() );
		Assertions.assertEquals( TEST_REQUEST_IVA, obtained.getBody().getIva() );
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, obtained.getBody().getAmount() );
		Assertions.assertFalse( obtained.getBody().isPaid() );
	}

	@Test
	public void newRequest_paid() {
		// Given
		final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withContents( this.contents )
				.withTotalAmount( TEST_REQUEST_TOTAL )
				.withPaid( true )
				.build();
		final CustomerRequestResponseV2 requestResponse = new CustomerRequestResponseV2()
				.setId( TEST_REQUEST_ID )
				.setLabel( TEST_REQUEST_LABEL )
				.setContents( new ArrayList<>() )
				.setCustomer( TEST_REQUEST_CUSTOMER )
				.setRequestDate( TEST_REQUEST_DATE )
				.setState( RequestState.OPEN )
				.setAmount( TEST_REQUEST_AMOUNT )
				.setIva( TEST_REQUEST_IVA )
				.setTotal( TEST_REQUEST_TOTAL )
				.setPaid( true );
		// When
		Mockito.when( this.requestServiceV2.newRequest( Mockito.any( CustomerRequestRequestV2.class ) ) ).thenReturn( requestResponse );
		// Test
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		final ResponseEntity<CustomerRequestResponseV2> obtained = requestControllerV2.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getBody().getLabel() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, obtained.getBody().getTotal() );
		Assertions.assertEquals( TEST_REQUEST_IVA, obtained.getBody().getIva() );
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, obtained.getBody().getAmount() );
		Assertions.assertTrue( obtained.getBody().isPaid() );
	}
}
