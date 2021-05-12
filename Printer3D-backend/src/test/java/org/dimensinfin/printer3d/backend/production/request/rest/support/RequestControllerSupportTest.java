package org.dimensinfin.printer3d.backend.production.request.rest.support;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestControllerSupportTest {
	private RequestsRepositoryV2 requestsRepositoryV2;
	private PartRepository partRepository;
	private ModelRepository modelRepository;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
	}

	@Test
	public void constructorContract() {
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		Assertions.assertNotNull( requestControllerSupport );
	}

	@Test
	public void deleteAllRequestsV2() {
		// When
		Mockito.when( this.requestsRepositoryV2.count() ).thenReturn( 2L );
		// Test
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		final ResponseEntity<CounterResponse> obtained = requestControllerSupport.deleteAllRequestsV2();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 2, obtained.getBody().getRecords() );
	}

	@Test
	public void deleteAllRequestsV2Exception() {
		// When
		Mockito.when( this.requestsRepositoryV2.count() ).thenReturn( 2L );
		Mockito.doThrow( RuntimeException.class ).when( this.requestsRepositoryV2 ).deleteAll();
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
					this.partRepository,
					this.modelRepository,
					this.requestsRepositoryV2 );
			requestControllerSupport.deleteAllRequestsV2();
		} );
	}

	@Test
	public void getRepositoryRequests() {
		// Given
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( new ArrayList<>() )
				.build();
		final List<RequestEntityV2> requestListV2 = new ArrayList<>();
		requestListV2.add( requestEntityV2 );
		// When
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestListV2 );
		// Test
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		final ResponseEntity<List<CustomerRequestRequestV2>> obtained = requestControllerSupport.getRepositoryRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().size() );
	}
}
