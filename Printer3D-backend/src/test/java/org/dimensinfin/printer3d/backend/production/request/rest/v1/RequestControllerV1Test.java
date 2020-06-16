package org.dimensinfin.printer3d.backend.production.request.rest.v1;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.production.rest.dto.Request;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestControllerV1Test {

	private RequestServiceV1 requestServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.requestServiceV1 = Mockito.mock( RequestServiceV1.class );
	}

	@Test
	public void constructorContract() {
		final RequestControllerV1 requestControllerV1 = new RequestControllerV1( this.requestServiceV1 );
		Assertions.assertNotNull( requestControllerV1 );
	}

	@Test
	public void newRequest() {
		// Given
		final Request request = new Request.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		// When
		Mockito.when( this.requestServiceV1.newRequest(Mockito.any(Request.class)) ).thenReturn( request );
		// Test
		final RequestControllerV1 requestControllerV1 = new RequestControllerV1( this.requestServiceV1 );
		final ResponseEntity<Request> obtained = requestControllerV1.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getBody().getLabel() );
	}
}
