package org.dimensinfin.printer3d.backend.production.domain;

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
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
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
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_WEIGHT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;

public class RequestPartCollectorTest {
	private static final String PART_IDENTIFIER = "fefe34e8-4430-4571-aa5a-95f70f3cf296";
	private static final String MODEL_IDENTIFIER = "749b3f62-9f7a-4171-ae1f-1b352b1e6247";
	private static final String REQUEST_IDENTIFIER = "f3f8e312-9a03-48d4-b909-a6955c4c95a7";

	private ModelRepository modelRepository;
	private RequestsRepositoryV2 requestsRepositoryV2;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
	}

	@Test
	public void collectRequestPartsFromRepositoryModel() {
		// Given
		final StockManager stockManager = new StockManager( this.partRepository );
		final List<UUID> modelPartList = new ArrayList<>();
		modelPartList.add( UUID.fromString( PART_IDENTIFIER ) );
		modelPartList.add( UUID.fromString( PART_IDENTIFIER ) );
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( UUID.fromString( MODEL_IDENTIFIER ) )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.withPartIdList( modelPartList )
				.build();
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
				.withStockAvailable( 2 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( true )
				.build();
		final List<PartEntity> stockPartList = new ArrayList<>();
		stockPartList.add( partEntity );
		final RequestItem requestItem = new RequestItem.Builder()
				.withItemId( UUID.fromString( MODEL_IDENTIFIER ) )
				.withQuantity( 1 )
				.withType( RequestContentType.MODEL )
				.build();
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( requestItem );
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( UUID.fromString( REQUEST_IDENTIFIER ) )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.OPEN )
				.withContents( contents )
				.withAmount( TEST_REQUEST_AMOUNT )
				.build();
		final List<RequestEntityV2> requestList = new ArrayList<>();
		requestList.add( requestEntityV2 );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestList );
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		// Tests
		final RequestPartCollector requestPartCollector = new RequestPartCollector( this.modelRepository, this.requestsRepositoryV2 );
		final StockManager obtained = requestPartCollector.collectRequestPartsFromRepository( stockManager.clean().startStock() );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 0, obtained.getStock( UUID.fromString( PART_IDENTIFIER ) ) );
	}

	@Test
	public void collectRequestPartsFromRepositoryPart() {
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
				.withStockAvailable( 2 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( true )
				.build();
		final List<PartEntity> stockPartList = new ArrayList<>();
		stockPartList.add( partEntity );
		final RequestItem requestItem = new RequestItem.Builder()
				.withItemId( UUID.fromString( PART_IDENTIFIER ) )
				.withQuantity( 1 )
				.withType( RequestContentType.PART )
				.build();
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( requestItem );
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( UUID.fromString( REQUEST_IDENTIFIER ) )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( RequestState.OPEN )
				.withContents( contents )
				.withAmount( TEST_REQUEST_AMOUNT )
				.build();
		final List<RequestEntityV2> requestList = new ArrayList<>();
		requestList.add( requestEntityV2 );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestList );
		// Tests
		final RequestPartCollector requestPartCollector = new RequestPartCollector( this.modelRepository, this.requestsRepositoryV2 );
		final StockManager obtained = requestPartCollector.collectRequestPartsFromRepository( stockManager.clean().startStock() );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 1, obtained.getStock( UUID.fromString( PART_IDENTIFIER ) ) );
	}

	@Test
	public void constructorContract() {
		final RequestPartCollector requestPartCollector = new RequestPartCollector( this.modelRepository, this.requestsRepositoryV2 );
		Assertions.assertNotNull( requestPartCollector );
	}
}
