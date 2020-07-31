package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_CLOSED_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestServiceV2Test {
	private RequestsRepositoryV2 requestsRepositoryV2;
	private ModelRepository modelRepository;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void closeRequest() {
		// Given
		final UUID partId = UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" );
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( new RequestItem.Builder().withItemId( partId ).withQuantity( 1 ).withType( RequestContentType.PART ).build() );
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( contents )
				.withRequestDate( Instant.now() )
				.build();
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
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2 ) );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		final RequestV2 obtained = requestServiceV2.closeRequest( TEST_REQUEST_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( RequestState.CLOSE, obtained.getState() );
		Assertions.assertTrue( Instant.now().toString().startsWith( obtained.getClosedDate().substring( 0, 15 ) ) );
	}

	@Test
	public void closeRequestException() {
		// Given
		final UUID partId = UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" );
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( new RequestItem.Builder().withItemId( partId ).withQuantity( 1 ).withType( RequestContentType.PART ).build() );
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( contents )
				.withRequestDate( Instant.now() )
				.build();
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
				.withStockAvailable( 0 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<PartEntity> stockPartList = new ArrayList<>();
		stockPartList.add( partEntity );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2 ) );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			requestServiceV2.closeRequest( TEST_REQUEST_ID );
		} );
	}

	@Test
	public void closedRequestFailureSelectRequestEntity() {
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			requestServiceV2.closeRequest( TEST_REQUEST_ID );
		} );
	}

	@Test
	public void closeRequestWithModel() {
		// Given
		final UUID partId = UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" );
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( new RequestItem.Builder().withItemId( partId ).withQuantity( 1 ).withType( RequestContentType.MODEL ).build() );
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( contents )
				.withRequestDate( Instant.now() )
				.build();
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
		final List<UUID> partIdList = new ArrayList<>();
		partIdList.add( partId );
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.withPartIdList( partIdList )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2 ) );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		final RequestV2 obtained = requestServiceV2.closeRequest( TEST_REQUEST_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( RequestState.CLOSE, obtained.getState() );
		Assertions.assertTrue( Instant.now().toString().startsWith( obtained.getClosedDate().substring( 0, 15 ) ) );
	}
	@Test
	public void constructorContract() {
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		Assertions.assertNotNull( requestServiceV2 );
	}
	@Test
	public void deleteRequestV2() {
		// Given
		final RequestEntityV2 requestEntityV2 = Mockito.mock( RequestEntityV2.class );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2 ) );
		Mockito.when( requestEntityV2.isOpen() ).thenReturn( true );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		final CounterResponse obtained = requestServiceV2.deleteRequest( TEST_REQUEST_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 1, obtained.getRecords() );
	}

	@Test
	public void deleteRequestV2Closed() {
		// Given
		final RequestEntityV2 requestEntityV2 = Mockito.mock( RequestEntityV2.class );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2 ) );
		Mockito.when( requestEntityV2.isOpen() ).thenReturn( false );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		Assertions.assertThrows( RepositoryConflictException.class, () -> {
			requestServiceV2.deleteRequest( TEST_REQUEST_ID );
		} );
	}

	@Test
	public void deleteRequestV2NotFound() {
		// Given
		final RequestEntityV2 requestEntityV2 = Mockito.mock( RequestEntityV2.class );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			requestServiceV2.deleteRequest( TEST_REQUEST_ID );
		} );
	}

	@Test
	public void newRequest() {
		// Given
		final RequestV2 request = new RequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withState( TEST_REQUEST_STATE )
				.withAmount( TEST_REQUEST_AMOUNT )
				.withClosedDate( TEST_REQUEST_CLOSED_DATE.toString() )
				.withContents( new ArrayList<>() )
				.build();
		final RequestEntityV2 requestEntity = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withAmount( TEST_REQUEST_AMOUNT )
				.withClosedDate( TEST_REQUEST_CLOSED_DATE )
				.withContents( new ArrayList<>() )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.requestsRepositoryV2.save( Mockito.any( RequestEntityV2.class ) ) ).thenReturn( requestEntity );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		final RequestV2 obtained = requestServiceV2.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
	}

	@Test
	public void newRequestAlreadyExist() {
		// Given
		final RequestEntityV2 requestEntity = Mockito.mock( RequestEntityV2.class );
		final RequestV2 request = Mockito.mock( RequestV2.class );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntity ) );
		Mockito.when( request.getId() ).thenReturn( UUID.randomUUID() );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		Assertions.assertThrows( RepositoryConflictException.class, () -> {
			requestServiceV2.newRequest( request );
		} );
	}
}
