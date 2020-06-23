package org.dimensinfin.printer3d.backend.production.job.rest.v1;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.domain.StockManager;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR_CODE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;

public class JobServiceV1Test {

	private PartRepository partRepository;
	private RequestsRepository requestsRepository;
	private StockManager stockManager;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
		this.requestsRepository = Mockito.mock( RequestsRepository.class );
		this.stockManager = Mockito.mock( StockManager.class );
	}

	@Test
	public void constructorContract() {
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository, this.requestsRepository, this.stockManager );
		Assertions.assertNotNull( jobServiceV1 );
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
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final List<RequestEntity> requestList = new ArrayList<>();
		requestList.add( requestEntity1 );
		final Request request1 = new Request.Builder()
				.withId( UUID.randomUUID() )
				.withRequestDate( OffsetDateTime.now().format( DateTimeFormatter.ISO_OFFSET_DATE_TIME ) )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( request1PartList )
				.build();
		final PartEntity part = new PartEntity.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527b" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR_CODE )
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
		Mockito.when( this.stockManager.getStock( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) ) )
				.thenReturn( 2 );
		Mockito.when( this.stockManager.getStock( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) ) )
				.thenReturn( 0 );
		Mockito.when( this.stockManager.getStock( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) ) )
				.thenReturn( -1 );
		Mockito.when( this.partRepository.findAll() ).thenReturn( new ArrayList<>() );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		// Test
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository, this.requestsRepository, this.stockManager );
		final List<Job> obtained = jobServiceV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 1, obtained.size() );
	}

	@Test
	public void getPendingJobsOnlyStockJobs() {
		// Given
		final PartEntity part1 = new PartEntity.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527a" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR_CODE )
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
				.withColor( TEST_PART_COLOR_CODE )
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
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository, this.requestsRepository, this.stockManager );
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
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository, this.requestsRepository, this.stockManager );
		final List<Job> obtained = jobServiceV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 6, obtained.size() );
		Assertions.assertEquals( "112ad653-9eea-4124-ab20-9fcd92d0527b", obtained.get( 0 ).getPart().getId().toString() );
		Assertions.assertEquals( "112ad653-9eea-4124-ab20-9fcd92d0527a", obtained.get( 4 ).getPart().getId().toString() );
	}
}
