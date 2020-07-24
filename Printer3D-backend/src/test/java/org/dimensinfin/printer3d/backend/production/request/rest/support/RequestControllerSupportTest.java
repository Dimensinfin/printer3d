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
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
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

	@Test
	public void transformRequestsV1() {
		// Given
		final UUID partId = UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" );
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( new RequestItem.Builder().withItemId( partId ).withQuantity( 1 ).withType( RequestContentType.MODEL ).build() );
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		final List<RequestEntity> requestEntityV1List = new ArrayList<>();
		requestEntityV1List.add( requestEntity );
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.CLOSE )
				.withContents( new ArrayList<>() )
				.build();
		final List<RequestEntityV2> requestEntityV2List = new ArrayList<>();
		requestEntityV2List.add( requestEntityV2 );
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( partId )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 5 )
				.withStockAvailable( 10 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<PartEntity> stockPartList = new ArrayList<>();
		stockPartList.add( partEntity );
		// When
		Mockito.when( this.requestsRepositoryV1.findAll() ).thenReturn( requestEntityV1List );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestEntityV2List );
		Mockito.when( this.requestsRepositoryV2.save( Mockito.any( RequestEntityV2.class ) )).thenReturn( requestEntityV2 );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		// Test
		final RequestControllerSupport requestControllerSupport = new RequestControllerSupport(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV1,
				this.requestsRepositoryV2 );
		final ResponseEntity<List<CounterResponse>> obtained = requestControllerSupport.transformRequestsV1();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 3, obtained.getBody().size() );
		Assertions.assertEquals( 1, obtained.getBody().get( 0 ).getRecords() );
//		Mockito.verify( this.requestsRepositoryV2, Mockito.times( 1 ) ).save( Mockito.any( RequestEntityV2.class ) );
		Mockito.verify( this.requestsRepositoryV1, Mockito.times( 1 ) ).delete( Mockito.any( RequestEntity.class ) );
		Assertions.assertEquals( 1, obtained.getBody().get( 1 ).getRecords() );
		Assertions.assertEquals( 1, obtained.getBody().get( 2 ).getRecords() );
//		Assertions.assertEquals( TEST_PART_PRICE, obtained.getBody().get( 2 ).getRecords() );
	}
}
