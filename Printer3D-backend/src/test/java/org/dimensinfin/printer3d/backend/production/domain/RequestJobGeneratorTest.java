package org.dimensinfin.printer3d.backend.production.domain;

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
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

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

public class RequestJobGeneratorTest {
	private static final String PART_IDENTIFIER = "fefe34e8-4430-4571-aa5a-95f70f3cf296";

	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final RequestJobGenerator requestJobGenerator = new RequestJobGenerator( this.partRepository );
		Assertions.assertNotNull( requestJobGenerator );
	}

	@Test
	public void generateMissingRequestJobsMissing() {
		// Given
		final StockManager stockManager = new StockManager( this.partRepository );
		final PartEntity partEntity1 = new PartEntity.Builder()
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
				.withStockAvailable( -2 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( true )
				.build();
		final PartEntity partEntity2 = new PartEntity.Builder()
				.withId( UUID.fromString( "49f536a4-9086-42d5-b61c-35adc73b3e58" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withWeight( TEST_PART_WEIGHT )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 5 )
				.withStockAvailable( -4 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( true )
				.build();
		final List<PartEntity> stockPartList = new ArrayList<>();
		stockPartList.add( partEntity1 );
		stockPartList.add( partEntity2 );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		Mockito.when( this.partRepository.findById( UUID.fromString( PART_IDENTIFIER ) ) )
				.thenReturn( Optional.of( partEntity1 ) );
		Mockito.when( this.partRepository.findById( UUID.fromString( "49f536a4-9086-42d5-b61c-35adc73b3e58" ) ) )
				.thenReturn( Optional.of( partEntity2 ) );
		// Tests
		final RequestJobGenerator requestJobGenerator = new RequestJobGenerator( this.partRepository );
		final List<Job> obtained = requestJobGenerator.generateMissingRequestJobs( stockManager.clean().startStock() );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 6, obtained.size() );
		Assertions.assertEquals(
				UUID.fromString( "49f536a4-9086-42d5-b61c-35adc73b3e58" ).toString(),
				obtained.get( 3 ).getPart().getId().toString()
		);
		Assertions.assertEquals(
				UUID.fromString( PART_IDENTIFIER ).toString(),
				obtained.get( 4 ).getPart().getId().toString()
		);
	}

	@Test
	public void generateMissingRequestJobsOK() {
		// Given
		final StockManager stockManager = new StockManager( this.partRepository );
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
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		// Tests
		final RequestJobGenerator requestJobGenerator = new RequestJobGenerator( this.partRepository );
		final List<Job> obtained = requestJobGenerator.generateMissingRequestJobs( stockManager.clean().startStock() );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 0, obtained.size() );
	}
}
