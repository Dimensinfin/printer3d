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

import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepositoryExtended;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
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
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_CLOSED_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestServiceV2Test {
	//	private StockManager stockManager;
	private RequestsRepository requestsRepositoryV1;
	private RequestsRepositoryV2 requestsRepositoryV2;
	private ModelRepository modelRepository;
	private PartRepositoryExtended partRepository;

	@BeforeEach
	public void beforeEach() {
		this.requestsRepositoryV1 = Mockito.mock( RequestsRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.partRepository = Mockito.mock( PartRepositoryExtended.class );
	}

	@Test
	public void closeRequest() {
		// Given
		final UUID partId = UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" );
		final List<RequestItem> contents =new ArrayList<>();
		contents.add(new RequestItem.Builder().withItemId( partId ).withQuantity( 1 ).withType( RequestContentType.PART ).build());
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
		Mockito.when( this.requestsRepositoryV1.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2 ) );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV1,
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
				this.requestsRepositoryV1,
				this.requestsRepositoryV2,
				this.modelRepository );
		Assertions.assertNotNull( requestServiceV2 );
	}

	@Test
	public void deleteRequestV1() {
		// Given
		final RequestEntity requestEntityV1 = Mockito.mock( RequestEntity.class );
		// When
		Mockito.when( this.requestsRepositoryV1.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV1 ) );
		Mockito.when( requestEntityV1.isOpen() ).thenReturn( true );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV1,
				this.requestsRepositoryV2,
				this.modelRepository );
		final CounterResponse obtained = requestServiceV2.deleteRequest( TEST_REQUEST_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 1, obtained.getRecords() );
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
				this.requestsRepositoryV1,
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
				this.requestsRepositoryV1,
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
				this.requestsRepositoryV1,
				this.requestsRepositoryV2,
				this.modelRepository );
		final CounterResponse obtained = requestServiceV2.deleteRequest( TEST_REQUEST_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 0, obtained.getRecords() );
	}

	@Test
	public void getOpenRequestsV1AndV2() {
		// Given
		final List<PartRequest> requestPartList1 = new ArrayList<>();
		requestPartList1.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withQuantity( 2 )
				.build() );
		requestPartList1.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withQuantity( 3 )
				.build() );
		requestPartList1.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) )
				.withQuantity( 3 )
				.build() );
		final List<PartRequest> requestPartList2 = new ArrayList<>();
		requestPartList2.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withQuantity( 2 )
				.build() );
		requestPartList2.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withQuantity( 3 )
				.build() );
		final RequestEntity requestEntity1 = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V1 01" )
				.withState( RequestState.OPEN )
				.withPartList( requestPartList1 )
				.withRequestDate( Instant.now() )
				.build();
		final RequestEntity requestEntity2 = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V1 02" )
				.withState( RequestState.OPEN )
				.withPartList( requestPartList2 )
				.withRequestDate( Instant.now() )
				.build();
		final RequestEntity requestEntityClosed = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V1 03 CLOSED" )
				.withState( RequestState.CLOSE )
				.withPartList( requestPartList2 )
				.withRequestDate( Instant.now() )
				.build();
		final List<RequestEntity> requestv1List = new ArrayList<>();
		requestv1List.add( requestEntity1 );
		requestv1List.add( requestEntityClosed );
		requestv1List.add( requestEntity2 );
		// Given
		final List<RequestItem> contents1 = new ArrayList<>();
		contents1.add( new RequestItem.Builder()
				.withItemId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withQuantity( 2 )
				.withType( RequestContentType.PART )
				.build() );
		contents1.add( new RequestItem.Builder()
				.withItemId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withQuantity( 3 )
				.withType( RequestContentType.PART )
				.build() );
		contents1.add( new RequestItem.Builder()
				.withItemId( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) )
				.withType( RequestContentType.PART )
				.withQuantity( 3 )
				.build() );
		final List<RequestItem> contents2 = new ArrayList<>();
		contents2.add( new RequestItem.Builder()
				.withItemId( UUID.fromString( "03400770-e894-45d2-8884-727d26a0ee7e" ) )
				.withQuantity( 2 )
				.withType( RequestContentType.MODEL )
				.build() );
		contents2.add( new RequestItem.Builder()
				.withItemId( UUID.fromString( "74da1ab3-2e96-4038-9b41-9dae71566ba1" ) )
				.withQuantity( 3 )
				.withType( RequestContentType.MODEL )
				.build() );
		final RequestEntityV2 requestEntityV21 = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V2 01" )
				.withState( RequestState.OPEN )
				.withContents( contents1 )
				.withRequestDate( Instant.now() )
				.build();
		final RequestEntityV2 requestEntityV22 = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V2 02" )
				.withState( RequestState.OPEN )
				.withContents( contents2 )
				.withRequestDate( Instant.now() )
				.build();
		final RequestEntityV2 requestEntityV2Closed = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V2 03 CLOSED" )
				.withState( RequestState.CLOSE )
				.withContents( contents2 )
				.withRequestDate( Instant.now() )
				.build();
		final List<RequestEntityV2> requestv2List = new ArrayList<>();
		requestv2List.add( requestEntityV21 );
		requestv2List.add( requestEntityV2Closed );
		requestv2List.add( requestEntityV22 );

		final List<PartEntity> stockList = new ArrayList<>();
		final PartEntity part467 = new PartEntity.Builder()
				.withId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( 6 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final PartEntity partb03 = new PartEntity.Builder()
				.withId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( 6 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final PartEntity part266 = new PartEntity.Builder()
				.withId( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( 12 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		stockList.add( part467 );
		stockList.add( partb03 );
		stockList.add( part266 );
		final ModelEntity modelEntity = Mockito.mock( ModelEntity.class );
		final List<UUID> partIdList = new ArrayList<>();
		partIdList.add( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) );
		partIdList.add( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) );
		partIdList.add( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) );
		// When
		Mockito.when( this.requestsRepositoryV1.findAll() ).thenReturn( requestv1List );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestv2List );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockList );
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		Mockito.when( modelEntity.getPartIdList() ).thenReturn( partIdList );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV1,
				this.requestsRepositoryV2,
				this.modelRepository );
		final List<RequestV2> obtained = requestServiceV2.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 4, obtained.size() );
		Assertions.assertEquals( RequestState.COMPLETED, obtained.get( 0 ).getState() );
		Assertions.assertEquals( RequestState.COMPLETED, obtained.get( 1 ).getState() );
		Assertions.assertEquals( RequestState.OPEN, obtained.get( 2 ).getState() );
		Assertions.assertEquals( RequestState.OPEN, obtained.get( 3 ).getState() );
	}

	@Test
	public void getOpenRequestsV1AndV2Exception() {
		// When
		Mockito.when( this.requestsRepositoryV1.findAll() ).thenThrow( RuntimeException.class );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepositoryV1,
				this.requestsRepositoryV2,
				this.modelRepository );
		final List<RequestV2> obtained = requestServiceV2.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 0, obtained.size() );
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
				this.requestsRepositoryV1,
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
				this.requestsRepositoryV1,
				this.requestsRepositoryV2,
				this.modelRepository );
		Assertions.assertThrows( RepositoryConflictException.class, () -> {
			requestServiceV2.newRequest( request );
		} );
	}
}
