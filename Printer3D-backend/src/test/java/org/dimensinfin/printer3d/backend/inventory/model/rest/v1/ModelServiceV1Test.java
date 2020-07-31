package org.dimensinfin.printer3d.backend.inventory.model.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelUpdater;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelRequestConstants.TEST_MODELREQUEST_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;

public class ModelServiceV1Test {

	private ModelRepository modelRepository;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
		Assertions.assertNotNull( modelServiceV1 );
	}

	@Test
	public void getModels() {
		final List<ModelEntity> modelEntityList = new ArrayList<>();
		modelEntityList.add( new ModelEntity.Builder()
				.withId( UUID.fromString( "9ed337f1-eb5a-48ff-969f-6991e0f75918" ) )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build()
		);
		modelEntityList.add( new ModelEntity.Builder()
				.withId( UUID.fromString( "b783c038-04cd-4290-a7c5-9820f9e38991" ) )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build()
		);
		// When
		Mockito.when( this.modelRepository.findAll() ).thenReturn( modelEntityList );
		// Test
		final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
		final ModelList obtained = modelServiceV1.getModels();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getCount() );
		Assertions.assertEquals( 2, obtained.getModels().size() );
	}

	@Test
	public void newModel() {
		// Given
		final ModelRequest modelRequest = new ModelRequest.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withPartIdList( new ArrayList<>() )
				.build();
		final List<UUID> partIdList = new ArrayList<>();
		partIdList.add( TEST_PART_ID );
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.withPartIdList( partIdList )
				.build();
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( TEST_PART_ID )
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
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.modelRepository.save( Mockito.any( ModelEntity.class ) ) ).thenReturn( modelEntity );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		// Test
		final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
		final Model obtained = modelServiceV1.newModel( modelRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_PART_ID.toString(), obtained.getPartList().get( 0 ).getId().toString() );
	}

	@Test
	public void newModelException() {
		// Given
		final ModelRequest modelRequest = new ModelRequest.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withPartIdList( new ArrayList<>() )
				.build();
		final List<UUID> partIdList = new ArrayList<>();
		partIdList.add( TEST_PART_ID );
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.withPartIdList( partIdList )
				.build();
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( TEST_PART_ID )
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
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		Mockito.when( this.modelRepository.save( Mockito.any( ModelEntity.class ) ) ).thenReturn( modelEntity );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		// Test
		final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			modelServiceV1.newModel( modelRequest );
		} );
	}

	@Test
	public void updateModel() {
		// Given
		final ModelRequest modelRequest = new ModelRequest.Builder()
				.withId( TEST_MODELREQUEST_ID )
				.withLabel( TEST_MODELREQUEST_LABEL )
				.withPrice( TEST_MODELREQUEST_PRICE )
				.withStockLevel( TEST_MODELREQUEST_STOCK_LEVEL )
				.withImagePath( TEST_MODELREQUEST_IMAGE_PATH )
				.withActive( TEST_MODELREQUEST_ACTIVE )
				.build();
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		// When
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		Mockito.when( this.modelRepository.save( Mockito.any(ModelEntity.class) ) ).thenReturn( new ModelUpdater( modelEntity ).update( modelRequest ) );
		// Test
		final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
		final Model obtained = modelServiceV1.updateModel( modelRequest );
		// Assertions
		Assertions.assertEquals( TEST_MODEL_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_MODELREQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_MODELREQUEST_PRICE, obtained.getPrice(), 0.01 );
		Assertions.assertEquals( TEST_MODELREQUEST_STOCK_LEVEL, obtained.getStockLevel() );
		Assertions.assertEquals( TEST_MODELREQUEST_IMAGE_PATH, obtained.getImagePath() );
		Assertions.assertFalse( obtained.isActive() );
	}
}
