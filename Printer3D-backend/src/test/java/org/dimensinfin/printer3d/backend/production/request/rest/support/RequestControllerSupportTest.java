package org.dimensinfin.printer3d.backend.production.request.rest.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

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
					this.requestsRepositoryV1,
					this.requestsRepositoryV2 );
			requestControllerSupport.deleteAllRequestsV2();
		} );
	}

	@Test
	public void deleteAllRequestsV2() {
		// When
		Mockito.when( this.requestsRepositoryV2.count() ).thenReturn( 2L );
		// Test
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV1,
				this.requestsRepositoryV2 );
		final ResponseEntity<CounterResponse> obtained = requestControllerSupport.deleteAllRequestsV2();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 2, obtained.getBody().getRecords() );
	}

	@Test
	public void getRepositoryRequests() {
		// Given
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		final List<RequestEntity> requestListV1 = new ArrayList<>();
		requestListV1.add( requestEntity );
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
		Mockito.when( this.requestsRepositoryV1.findAll() ).thenReturn( requestListV1 );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestListV2 );
		// Test
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV1,
				this.requestsRepositoryV2 );
		final ResponseEntity<List<RequestV2>> obtained = requestControllerSupport.getRepositoryRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 2, obtained.getBody().size() );
	}

	@Test
	public void newRequestV1() {
		// Given
		final Request request = new Request.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV1.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.requestsRepositoryV1.save( Mockito.any( RequestEntity.class ) ) ).thenReturn( requestEntity );
		// Test
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV1,
				this.requestsRepositoryV2 );
		final ResponseEntity<Request> obtained = requestControllerSupport.newRequestV1( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
	}

	@Test
	public void newRequestV1Exception() {
		// Given
		final Request request = new Request.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV1.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntity ) );
		Mockito.when( this.requestsRepositoryV1.save( Mockito.any( RequestEntity.class ) ) ).thenReturn( requestEntity );
		// Test
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV1,
				this.requestsRepositoryV2 );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			requestControllerSupport.newRequestV1( request );
		} );
	}
}
