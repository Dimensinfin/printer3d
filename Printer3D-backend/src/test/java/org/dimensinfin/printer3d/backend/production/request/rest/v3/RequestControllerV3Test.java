package org.dimensinfin.printer3d.backend.production.request.rest.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.backend.production.request.rest.RequestRestErrors;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

public class RequestControllerV3Test {
	private RequestsRepositoryV2 requestsRepositoryV2;
	private RequestServiceV3 requestServiceV3;

	@BeforeEach
	public void beforeEach() {
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
		this.requestServiceV3 = Mockito.mock( RequestServiceV3.class );
	}

	@Test
	public void constructorContract() {
		final RequestControllerV3 requestControllerV3 = new RequestControllerV3( this.requestServiceV3 );
		Assertions.assertNotNull( requestControllerV3 );
	}

	@Test
	public void deleteRequest() {
		// Given
		final UUID requestId = UUID.fromString( "d9e894f3-3016-4f56-aa23-0b9d966b7adc" );
		final CustomerRequestResponseV2 requestResponse = Mockito.mock( CustomerRequestResponseV2.class );
		// When
		Mockito.when( this.requestServiceV3.deleteRequest( Mockito.any( UUID.class ) ) ).thenReturn( requestResponse );
		// Test
		final RequestControllerV3 requestControllerV3 = new RequestControllerV3( this.requestServiceV3 );
		final ResponseEntity<CustomerRequestResponseV2> obtained = requestControllerV3.deleteRequest( requestId );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
	}

	@Test
	public void deliverRequest() {
		// Given
		final UUID requestId = UUID.fromString( "d9e894f3-3016-4f56-aa23-0b9d966b7adc" );
		final CustomerRequestResponseV2 requestResponse = Mockito.mock( CustomerRequestResponseV2.class );
		// When
		Mockito.when( this.requestServiceV3.deliverRequest( Mockito.any( UUID.class ) ) ).thenReturn( requestResponse );
		Mockito.when( requestResponse.getId() ).thenReturn( requestId );
		// Test
		final RequestControllerV3 requestControllerV3 = new RequestControllerV3( this.requestServiceV3 );
		final ResponseEntity<CustomerRequestResponseV2> obtained = requestControllerV3.deliverRequest( requestId );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( requestId, obtained.getBody().getId() );
	}

	@Test
	public void deliverRequest_notfound() {
		// Given
		final UUID requestId = UUID.fromString( "d9e894f3-3016-4f56-aa23-0b9d966b7adc" );
		// When
		Mockito.when( this.requestServiceV3.deliverRequest( Mockito.any( UUID.class ) ) ).thenThrow(
				new DimensinfinRuntimeException( RequestRestErrors.errorREQUESTNOTFOUND( requestId ),
						"No Request found while trying to deliver the Request." ) );
		// Test
		final RequestControllerV3 requestControllerV3 = new RequestControllerV3( this.requestServiceV3 );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			requestControllerV3.deliverRequest( requestId );
		} );
	}

	@Test
	public void getClosedRequestsV3() {
		// Given
		final CustomerRequestResponseV2 request = Mockito.mock( CustomerRequestResponseV2.class );
		final List<CustomerRequestResponseV2> openRequests = new ArrayList<>();
		openRequests.add( request );
		// When
		Mockito.when( this.requestServiceV3.getClosedRequestsV3() ).thenReturn( openRequests );
		// Test
		final RequestControllerV3 requestControllerV3 = new RequestControllerV3( this.requestServiceV3 );
		final ResponseEntity<List<CustomerRequestResponseV2>> obtained = requestControllerV3.getClosedRequestsV3();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().size() );
	}

	@Test
	public void getOpenRequestsV3() {
		// Given
		final CustomerRequestResponseV2 request = Mockito.mock( CustomerRequestResponseV2.class );
		final List<CustomerRequestResponseV2> openRequests = new ArrayList<>();
		openRequests.add( request );
		// When
		Mockito.when( this.requestServiceV3.getOpenRequestsV3() ).thenReturn( openRequests );
		// Test
		final RequestControllerV3 requestControllerV3 = new RequestControllerV3( this.requestServiceV3 );
		final ResponseEntity<List<CustomerRequestResponseV2>> obtained = requestControllerV3.getOpenRequestsV3();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().size() );
	}
}