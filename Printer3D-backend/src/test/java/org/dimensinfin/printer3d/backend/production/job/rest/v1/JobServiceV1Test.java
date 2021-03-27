package org.dimensinfin.printer3d.backend.production.job.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.domain.RequestJobGenerator;
import org.dimensinfin.printer3d.backend.production.domain.RequestPartCollector;
import org.dimensinfin.printer3d.backend.production.domain.StockLevelJobGenerator;
import org.dimensinfin.printer3d.backend.production.domain.StockManager;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

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
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_WEIGHT;

public class JobServiceV1Test {
	private static final String PART_IDENTIFIER = "49f536a4-9086-42d5-b61c-35adc73b3e58";
	private PartRepository partRepository;
	private ModelRepository modelRepository;
	private RequestPartCollector requestPartCollector;
	private RequestJobGenerator requestJobGenerator;
	private StockLevelJobGenerator stockLevelJobGenerator;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.requestPartCollector = Mockito.mock( RequestPartCollector.class );
		this.requestJobGenerator = Mockito.mock( RequestJobGenerator.class );
		this.stockLevelJobGenerator = Mockito.mock( StockLevelJobGenerator.class );
	}

	@Test
	public void constructorContract() {
		final JobServiceV1 jobServiceV1 = new JobServiceV1(
				this.partRepository,
				this.modelRepository,
				this.requestPartCollector,
				this.requestJobGenerator,
				this.stockLevelJobGenerator );
		Assertions.assertNotNull( jobServiceV1 );
	}

	@Test
	public void getPendingJobs() {
		// Given
		final StockManager stockManager = Mockito.mock( StockManager.class );
		final Part part = Mockito.mock( Part.class );
		final List<Job> jobList = new ArrayList<>();
		jobList.add( new Job.Builder().withPart( part ).withPriority( 2 ).build() );
		jobList.add( new Job.Builder().withPart( part ).withPriority( 1 ).build() );
		// When
		Mockito.when( this.requestJobGenerator.generateMissingRequestJobs( Mockito.any( StockManager.class ) ) )
				.thenReturn( jobList );
		Mockito.when( this.stockLevelJobGenerator.generateStockLevelJobs( Mockito.any( StockManager.class ) ) )
				.thenReturn( jobList );
		Mockito.when( this.requestPartCollector.collectRequestPartsFromRepository( Mockito.any( StockManager.class ) ) )
				.thenReturn( stockManager );
		// Test
		final JobServiceV1 jobServiceV1 = new JobServiceV1(
				this.partRepository,
				this.modelRepository,
				this.requestPartCollector,
				this.requestJobGenerator,
				this.stockLevelJobGenerator );
		final List<Job> obtained = jobServiceV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 4, obtained.size() );
	}

	@Test
	public void getPendingJobsModels() {
		// Given
		final StockManager stockManager = Mockito.mock( StockManager.class );
		final Part part = Mockito.mock( Part.class );
		final List<Job> jobList = new ArrayList<>();
		jobList.add( new Job.Builder().withPart( part ).withPriority( 2 ).build() );
		jobList.add( new Job.Builder().withPart( part ).withPriority( 1 ).build() );
		final List<UUID> partIdList = new ArrayList<>();
		partIdList.add( UUID.fromString( PART_IDENTIFIER ) );
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.withPartIdList( partIdList )
				.build();
		final List<ModelEntity> modelList = new ArrayList<>();
		modelList.add( modelEntity );
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( UUID.fromString( PART_IDENTIFIER ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withWeight( TEST_PART_WEIGHT )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 5 )
				.withStockAvailable( 0 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( true )
				.build();
		final List<PartEntity> stockPartList = new ArrayList<>();
		stockPartList.add( partEntity );
		// When
		Mockito.when( this.requestJobGenerator.generateMissingRequestJobs( Mockito.any( StockManager.class ) ) )
				.thenReturn( jobList );
		Mockito.when( this.stockLevelJobGenerator.generateStockLevelJobs( Mockito.any( StockManager.class ) ) )
				.thenReturn( jobList );
		Mockito.when( this.requestPartCollector.collectRequestPartsFromRepository( Mockito.any( StockManager.class ) ) )
				.thenReturn( stockManager );
		Mockito.when( this.modelRepository.findAll() ).thenReturn( modelList );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		// Test
		final JobServiceV1 jobServiceV1 = new JobServiceV1(
				this.partRepository,
				this.modelRepository,
				this.requestPartCollector,
				this.requestJobGenerator,
				this.stockLevelJobGenerator );
		final List<Job> obtained = jobServiceV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 4, obtained.size() );
	}
}
