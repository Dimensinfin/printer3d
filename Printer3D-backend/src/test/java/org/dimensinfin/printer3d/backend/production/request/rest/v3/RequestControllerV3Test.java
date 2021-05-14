package org.dimensinfin.printer3d.backend.production.request.rest.v3;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

public class RequestControllerV3Test {

	private RequestServiceV3 requestServiceV3;

	@BeforeEach
	public void beforeEach() {
		this.requestServiceV3 = Mockito.mock( RequestServiceV3.class );
	}

	@Test
	public void constructorContract() {
		final RequestControllerV3 requestControllerV3 = new RequestControllerV3( this.requestServiceV3 );
		Assertions.assertNotNull( requestControllerV3 );
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