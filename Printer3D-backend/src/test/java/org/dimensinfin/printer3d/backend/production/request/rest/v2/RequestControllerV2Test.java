package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestControllerV2Test {

	private RequestServiceV2 requestServiceV2;

	@BeforeEach
	public void beforeEach() {
		this.requestServiceV2 = Mockito.mock( RequestServiceV2.class );
	}

	@Test
	public void closeRequest() {
		// Given
		final UUID requestId = UUID.randomUUID();
		final RequestV2 request = Mockito.mock( RequestV2.class );
		// When
		Mockito.when( this.requestServiceV2.closeRequest( requestId ) ).thenReturn( request );
		Mockito.when( request.getId() ).thenReturn( requestId );
		// Test
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		final ResponseEntity<RequestV2> obtained = requestControllerV2.closeRequest( requestId );
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
	public void deleteRequest() {
		// Given
		final UUID requestId = UUID.randomUUID();
		final CounterResponse counterResponse = Mockito.mock( CounterResponse.class );
		// When
		Mockito.when( this.requestServiceV2.deleteRequest( Mockito.any( UUID.class ) ) ).thenReturn( counterResponse );
		Mockito.when( counterResponse.getRecords() ).thenReturn( 1 );
		// Test
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		final ResponseEntity<CounterResponse> obtained = requestControllerV2.deleteRequest( requestId );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().getRecords() );
	}

	@Test
	public void getOpenRequestsOnlyV1Requests() {
		// Given
		final RequestV2 request = Mockito.mock( RequestV2.class );
		final List<RequestV2> openRequests = new ArrayList<>();
		openRequests.add( request );
		// When
		Mockito.when( this.requestServiceV2.getOpenRequests() ).thenReturn( openRequests );
		// Test
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		final ResponseEntity<List<RequestV2>> obtained = requestControllerV2.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().size() );
	}

	@Test
	public void newRequest() {
		// Given
		final RequestV2 request = new RequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withState( TEST_REQUEST_STATE )
				.withContents( new ArrayList<>() )
				.build();
		// When
		Mockito.when( this.requestServiceV2.newRequest( Mockito.any( RequestV2.class ) ) ).thenReturn( request );
		// Test
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		final ResponseEntity<RequestV2> obtained = requestControllerV2.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getBody().getLabel() );
	}
}
