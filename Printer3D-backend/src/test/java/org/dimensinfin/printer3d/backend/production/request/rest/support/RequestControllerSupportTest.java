package org.dimensinfin.printer3d.backend.production.request.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;

public class RequestControllerSupportTest {

	private RequestsRepository requestsRepositoryV1;
	private RequestsRepositoryV2 requestsRepositoryV2;
	private PartRepository partRepository;
	private ModelRepository modelRepository;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.requestsRepositoryV1 = Mockito.mock( RequestsRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
	}

	@Test
	public void constructorContract() {
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.partRepository,
				this.modelRepository,
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
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV1,
				this.requestsRepositoryV2 );
		final ResponseEntity<CounterResponse> obtained = requestControllerSupport.deleteAllRequests();
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
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
					this.partRepository,
					this.modelRepository,
					this.requestsRepositoryV1,
					this.requestsRepositoryV2 );
			requestControllerSupport.deleteAllRequests();
		} );
	}
}
