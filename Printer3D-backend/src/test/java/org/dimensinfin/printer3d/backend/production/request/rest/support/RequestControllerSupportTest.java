package org.dimensinfin.printer3d.backend.production.request.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryException;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;

public class RequestControllerSupportTest {

	private RequestsRepository requestsRepositoryV1;
	private RequestsRepositoryV2 requestsRepositoryV2;

	@BeforeEach
	public void beforeEach() {
		this.requestsRepositoryV1 = Mockito.mock( RequestsRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
	}

	@Test
	public void constructorContract() {
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.requestsRepositoryV1,
				this.requestsRepositoryV2 );
		Assertions.assertNotNull( requestControllerSupport );
	}

	@Test
	public void deleteAllRequests() {
		// When
		Mockito.when( this.requestsRepositoryV1.count() ).thenReturn( 2L );
		// Test
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.requestsRepositoryV1,
				this.requestsRepositoryV2 );
		final ResponseEntity<CountResponse> obtained = requestControllerSupport.deleteAllRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 2, obtained.getBody().getRecords() );
	}

	@Test
	public void deleteAllRequestsException() {
		// When
		Mockito.when( this.requestsRepositoryV1.count() ).thenReturn( 2L );
		Mockito.doThrow( RuntimeException.class ).when( this.requestsRepositoryV1 ).deleteAll();
		// Exceptions
		Assertions.assertThrows( RepositoryException.class, () -> {
			final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
					this.requestsRepositoryV1,
					this.requestsRepositoryV2 );
			requestControllerSupport.deleteAllRequests();
		} );
	}
}
