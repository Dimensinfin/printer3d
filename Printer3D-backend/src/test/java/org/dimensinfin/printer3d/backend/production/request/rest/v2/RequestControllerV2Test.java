package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

public class RequestControllerV2Test {

	private RequestServiceV2 requestServiceV2;

	@BeforeEach
	public void beforeEach() {
		this.requestServiceV2 = Mockito.mock( RequestServiceV2.class );
	}

	@Test
	public void constructorContract() {
		final RequestControllerV2 requestControllerV2 = new RequestControllerV2( this.requestServiceV2 );
		Assertions.assertNotNull( requestControllerV2 );
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
		final ResponseEntity<List<RequestV2>> obtained = requestControllerV2
				.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().size() );
	}
}
