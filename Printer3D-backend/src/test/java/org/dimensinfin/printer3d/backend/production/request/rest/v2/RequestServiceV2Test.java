package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.domain.StockManager;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
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
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;

public class RequestServiceV2Test {
	private StockManager stockManager;
	private RequestsRepository requestsRepository;
	private RequestsRepositoryV2 requestsRepositoryV2;
	private ModelRepository modelRepository;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		//		this.stockManager = Mockito.mock( StockManager.class );
		this.requestsRepository = Mockito.mock( RequestsRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepository,
				this.requestsRepositoryV2,
				this.modelRepository, partRepository1 );
		Assertions.assertNotNull( requestServiceV2 );
	}

	//	@Test
	public void getOpenRequestsOnlyV1() {
		// Given
		final List<PartRequest> requestPartList = new ArrayList<>();
		requestPartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withQuantity( 2 )
				.build() );
		requestPartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withQuantity( 3 )
				.build() );
		requestPartList.add( new PartRequest.Builder()
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
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( requestPartList )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntity requestEntity2 = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( requestPartList2 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntity requestEntityClosed = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.CLOSE )
				.withPartList( requestPartList2 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final List<RequestEntity> requestList = new ArrayList<>();
		requestList.add( requestEntity1 );
		requestList.add( requestEntityClosed );
		requestList.add( requestEntity2 );
		// When
		Mockito.when( this.requestsRepository.findAll() ).thenReturn( requestList );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( new ArrayList<>() );
		Mockito.when( this.stockManager.minus( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ), 2 ) )
				.thenReturn( 2 );
		Mockito.when( this.stockManager.minus( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ), 3 ) )
				.thenReturn( 0 );
		Mockito.when( this.stockManager.minus( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ), 3 ) )
				.thenReturn( -1 );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepository,
				this.requestsRepositoryV2,
				this.modelRepository, partRepository1 );
		final List<RequestV2> obtained = requestServiceV2.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.size() );
		Assertions.assertEquals( RequestState.OPEN, obtained.get( 0 ).getState() );
		Assertions.assertEquals( RequestState.COMPLETED, obtained.get( 1 ).getState() );
	}

	//	@Test
	public void getOpenRequestsOnlyV2() {
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
				.withItemId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withQuantity( 2 )
				.withType( RequestContentType.PART )
				.build() );
		contents2.add( new RequestItem.Builder()
				.withItemId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withQuantity( 3 )
				.withType( RequestContentType.PART )
				.build() );
		final RequestEntityV2 requestEntity1 = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( contents1 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntityV2 requestEntity2 = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( contents2 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntityV2 requestEntityClosed = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.CLOSE )
				.withContents( contents2 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final List<RequestEntityV2> requestv2List = new ArrayList<>();
		requestv2List.add( requestEntity1 );
		requestv2List.add( requestEntityClosed );
		requestv2List.add( requestEntity2 );
		// When
		Mockito.when( this.requestsRepository.findAll() ).thenReturn( new ArrayList<>() );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestv2List );
		Mockito.when( this.stockManager.minus( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ), 2 ) )
				.thenReturn( 2 );
		Mockito.when( this.stockManager.minus( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ), 3 ) )
				.thenReturn( 0 );
		Mockito.when( this.stockManager.minus( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ), 3 ) )
				.thenReturn( -1 );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepository,
				this.requestsRepositoryV2,
				this.modelRepository, partRepository1 );
		final List<RequestV2> obtained = requestServiceV2.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.size() );
		Assertions.assertEquals( RequestState.OPEN, obtained.get( 0 ).getState() );
		Assertions.assertEquals( RequestState.COMPLETED, obtained.get( 1 ).getState() );
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
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntity requestEntity2 = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V1 02" )
				.withState( RequestState.OPEN )
				.withPartList( requestPartList2 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntity requestEntityClosed = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V1 03 CLOSED" )
				.withState( RequestState.CLOSE )
				.withPartList( requestPartList2 )
				.withRequestDate( OffsetDateTime.now() )
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
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntityV2 requestEntityV22 = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V2 02" )
				.withState( RequestState.OPEN )
				.withContents( contents2 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntityV2 requestEntityV2Closed = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( "Request Entity V2 03 CLOSED" )
				.withState( RequestState.CLOSE )
				.withContents( contents2 )
				.withRequestDate( OffsetDateTime.now() )
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
		Mockito.when( this.requestsRepository.findAll() ).thenReturn( requestv1List );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestv2List );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockList );
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		Mockito.when( modelEntity.getPartIdList() ).thenReturn( partIdList );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepository,
				this.requestsRepositoryV2,
				this.modelRepository, partRepository1 );
		final List<RequestV2> obtained = requestServiceV2.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 4, obtained.size() );
		Assertions.assertEquals( RequestState.COMPLETED, obtained.get( 0 ).getState() );
		Assertions.assertEquals( RequestState.COMPLETED, obtained.get( 1 ).getState() );
		Assertions.assertEquals( RequestState.OPEN, obtained.get( 2 ).getState() );
		Assertions.assertEquals( RequestState.OPEN, obtained.get( 3 ).getState() );
	}

//	@Test
	public void getOpenRequestsWithModels() {
		// Given
		final List<PartRequest> requestPartList = new ArrayList<>();
		requestPartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withQuantity( 2 )
				.build() );
		requestPartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withQuantity( 3 )
				.build() );
		requestPartList.add( new PartRequest.Builder()
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
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( requestPartList )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntity requestEntity2 = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( requestPartList2 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntity requestEntityClosed = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.CLOSE )
				.withPartList( requestPartList2 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final List<RequestEntity> requestList = new ArrayList<>();
		requestList.add( requestEntity1 );
		requestList.add( requestEntityClosed );
		requestList.add( requestEntity2 );

		final List<RequestItem> contentsWithModel = new ArrayList<>();
		contentsWithModel.add( new RequestItem.Builder()
				.withType( RequestContentType.MODEL )
				.withQuantity( 3 )
				.withItemId( UUID.fromString( "c926da84-258c-47d2-8cc8-859058c6266e" ) )
				.build() );
		final RequestEntityV2 requestEntityV21 = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( contentsWithModel )
				.withRequestDate( OffsetDateTime.now() )
				.build();

		final List<RequestEntityV2> requestv2List = new ArrayList<>();
		requestv2List.add( requestEntityV21 );
		final ModelEntity model = Mockito.mock( ModelEntity.class );
		final List<UUID> idList = new ArrayList<>();
		idList.add( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) );
		idList.add( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) );
		idList.add( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) );
		idList.add( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) );
		idList.add( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) );
		idList.add( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) );
		idList.add( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) );
		idList.add( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) );
		// When
		Mockito.when( this.requestsRepository.findAll() ).thenReturn( requestList );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestv2List );
		Mockito.when( this.stockManager.minus( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ), 2 ) )
				.thenReturn( 2 );
		Mockito.when( this.stockManager.minus( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ), 3 ) )
				.thenReturn( 0 );
		Mockito.when( this.stockManager.minus( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ), 3 ) )
				.thenReturn( -1 );
		Mockito.when( this.stockManager.minus( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ), 1 ) )
				.thenReturn( 2 );
		Mockito.when( this.stockManager.minus( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ), 1 ) )
				.thenReturn( 0 );
		Mockito.when( this.stockManager.minus( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ), 1 ) )
				.thenReturn( -1 );
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( model ) );
		Mockito.when( model.getPartIdList() ).thenReturn( idList );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.requestsRepository,
				this.requestsRepositoryV2,
				this.modelRepository, partRepository1 );
		final List<RequestV2> obtained = requestServiceV2.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 3, obtained.size() );
		Assertions.assertEquals( RequestState.OPEN, obtained.get( 0 ).getState() );
		Assertions.assertEquals( RequestState.COMPLETED, obtained.get( 1 ).getState() );
		Assertions.assertEquals( RequestState.OPEN, obtained.get( 2 ).getState() );
	}
}
