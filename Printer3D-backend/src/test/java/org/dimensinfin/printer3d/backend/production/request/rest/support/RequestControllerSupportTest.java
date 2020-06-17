package org.dimensinfin.printer3d.backend.production.request.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryException;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;

public class RequestControllerSupportTest {

	private RequestsRepository requestsRepository;

	@BeforeEach
	public void beforeEach() {
		this.requestsRepository = Mockito.mock( RequestsRepository.class );
	}

	@Test
	public void constructorContract() {
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport( this.requestsRepository );
		Assertions.assertNotNull( requestControllerSupport );
	}

	@Test
	public void deleteAllRequests() {
		// When
		Mockito.when( this.requestsRepository.count() ).thenReturn( 2L );
		// Test
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport( this.requestsRepository );
		final ResponseEntity<CountResponse> obtained = requestControllerSupport.deleteAllRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 2, obtained.getBody().getRecords() );
	}

	@Test
	public void deleteAllRequestsException() {
		// When
		Mockito.when( this.requestsRepository.count() ).thenReturn( 2L );
		Mockito.doThrow( RuntimeException.class ).when( this.requestsRepository ).deleteAll();
		// Exceptions
		Assertions.assertThrows( RepositoryException.class, () -> {
			final RequestControllerSupport requestControllerSupport = new RequestControllerSupport( this.requestsRepository );
			requestControllerSupport.deleteAllRequests();
		} );
	}

	@Test
	public void getRepositoryRequests() {
	}
}
