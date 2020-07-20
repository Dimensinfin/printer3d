package org.dimensinfin.printer3d.backend.production.job.rest.v1;

import java.time.Instant;
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
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ACTIVE;
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
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class JobServiceV1Test {
	private PartRepository partRepository;
	private RequestsRepository requestsRepository;
	private RequestsRepositoryV2 requestsRepositoryV2;
	private ModelRepository modelRepository;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
		this.requestsRepository = Mockito.mock( RequestsRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
	}

	@Test
	public void constructorContract() {
		final JobServiceV1 jobServiceV1 = new JobServiceV1(
				this.partRepository,
				this.requestsRepository,
				this.requestsRepositoryV2,
				this.modelRepository );
		Assertions.assertNotNull( jobServiceV1 );
	}

	@Test
	public void getPendingJobsFromPartAndModel() {
		// Given
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( new RequestItem.Builder()
				.withItemId( UUID.fromString( "c89ece07-d88a-4ed1-9382-d686237d220a" ) )
				.withType( RequestContentType.PART )
				.withQuantity( 2 )
				.build()
		);
		contents.add( new RequestItem.Builder()
				.withItemId( UUID.fromString( "32a8b116-e1b4-4168-95eb-d395f0be2edc" ) )
				.withType( RequestContentType.MODEL )
				.withQuantity( 1 )
				.build()
		);
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( contents )
				.build();
		final List<RequestEntityV2> requestEntityV2List = new ArrayList<>();
		requestEntityV2List.add( requestEntityV2 );
		final List<UUID> modelPartList = new ArrayList<>();
		modelPartList.add( UUID.fromString( "9ed337f1-eb5a-48ff-969f-6991e0f75918" ) );
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( UUID.fromString( "32a8b116-e1b4-4168-95eb-d395f0be2edc" ) )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.withPartIdList( modelPartList )
				.build();
		final List<PartEntity> partEntityList = new ArrayList<>();
		final PartEntity partEntity01 = new PartEntity.Builder()
				.withId( UUID.fromString( "c89ece07-d88a-4ed1-9382-d686237d220a" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( 0 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final PartEntity partEntity02 = new PartEntity.Builder()
				.withId( UUID.fromString( "9ed337f1-eb5a-48ff-969f-6991e0f75918" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( 0 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		partEntityList.add( partEntity01 );
		partEntityList.add( partEntity02 );
		final List<ModelEntity> modelEntityList = new ArrayList<>();
		modelEntityList.add( modelEntity );
		// When
		Mockito.when( this.requestsRepository.findAll() ).thenReturn( new ArrayList<>() );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestEntityV2List );
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		Mockito.when( this.partRepository.findAll() ).thenReturn( partEntityList );
		Mockito.when( this.partRepository.findById( UUID.fromString( "c89ece07-d88a-4ed1-9382-d686237d220a" ) ) )
				.thenReturn( Optional.of( partEntity01 ) );
		Mockito.when( this.partRepository.findById( UUID.fromString( "9ed337f1-eb5a-48ff-969f-6991e0f75918" ) ) )
				.thenReturn( Optional.of( partEntity02 ) );
		Mockito.when( this.modelRepository.findAll() ).thenReturn( modelEntityList );
		// Test
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository, this.requestsRepository, this.requestsRepositoryV2,
				this.modelRepository );
		final List<Job> obtained = jobServiceV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 13, obtained.size() );
	}

	@Test
	public void getPendingJobsOnlyRequestJobs() {
		// Given
		final List<PartRequest> request1PartList = new ArrayList<>();
		request1PartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withQuantity( 2 )
				.build() );
		request1PartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withQuantity( 3 )
				.build() );
		request1PartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) )
				.withQuantity( 3 )
				.build() );
		final RequestEntity requestEntity1 = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( request1PartList )
				.withRequestDate( Instant.now() )
				.build();
		final List<RequestEntity> requestList = new ArrayList<>();
		requestList.add( requestEntity1 );
		final Request request1 = new Request.Builder()
				.withId( UUID.randomUUID() )
				.withRequestDate( Instant.now().toString() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( request1PartList )
				.build();
		final PartEntity part = new PartEntity.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527b" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		// When
		Mockito.when( this.requestsRepository.findAll() ).thenReturn( requestList );
		//		Mockito.when( this.stockManager.getStock( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) ) )
		//				.thenReturn( 2 );
		//		Mockito.when( this.stockManager.getStock( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) ) )
		//				.thenReturn( 0 );
		//		Mockito.when( this.stockManager.getStock( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) ) )
		//				.thenReturn( -1 );
		Mockito.when( this.partRepository.findAll() ).thenReturn( new ArrayList<>() );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		// Test
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository, this.requestsRepository, this.requestsRepositoryV2,
				this.modelRepository );
		final List<Job> obtained = jobServiceV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 0, obtained.size() );
	}

	@Test
	public void getPendingJobsOnlyStockJobs() {
		// Given
		final PartEntity part1 = new PartEntity.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527a" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 2 )
				.withStockAvailable( 2 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final PartEntity part2 = new PartEntity.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527b" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 3 )
				.withStockAvailable( 1 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<PartEntity> partList = new ArrayList<>();
		partList.add( part1 );
		partList.add( part2 );
		// When
		Mockito.when( this.requestsRepository.findAll() ).thenReturn( new ArrayList<>() );
		Mockito.when( this.partRepository.findAll() ).thenReturn( partList );
		// Test
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository, this.requestsRepository, this.requestsRepositoryV2,
				this.modelRepository );
		final List<Job> obtained = jobServiceV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.size() );
	}

	@Test
	public void getPendingJobsSorting() {
		// Given
		final PartEntity part1 = new PartEntity.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527a" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( "VERDE" )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 4 )
				.withStockAvailable( 2 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final PartEntity part2 = new PartEntity.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527b" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( "ROJO" )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 5 )
				.withStockAvailable( 1 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<PartEntity> partList = new ArrayList<>();
		partList.add( part1 );
		partList.add( part2 );
		// When
		Mockito.when( this.requestsRepository.findAll() ).thenReturn( new ArrayList<>() );
		Mockito.when( this.partRepository.findAll() ).thenReturn( partList );
		// Test
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository, this.requestsRepository, this.requestsRepositoryV2,
				this.modelRepository );
		final List<Job> obtained = jobServiceV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 6, obtained.size() );
		Assertions.assertEquals( "112ad653-9eea-4124-ab20-9fcd92d0527b", obtained.get( 0 ).getPart().getId().toString() );
		Assertions.assertEquals( "112ad653-9eea-4124-ab20-9fcd92d0527a", obtained.get( 4 ).getPart().getId().toString() );
	}
}
