package org.dimensinfin.printer3d.backend.inventory.model.rest.v1;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.core.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateModelCompositionRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_ID;

public class ModelServiceV1Test {

	private ModelRepository modelRepository;
	private PartRepository partRepository;

	@Test
	public void addModelPart() {
		// Given
		final UpdateModelCompositionRequest modelCompositionRequest = new UpdateModelCompositionRequest.Builder()
				.withModelId( TEST_MODEL_ID )
				.withPartId( TEST_PART_ID )
				.build();
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//				.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		// When
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		Mockito.when( this.modelRepository.save( modelEntity ) ).thenReturn( modelEntity );
		// Test
		final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
		final Model obtained = modelServiceV1.addModelPart( modelCompositionRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_MODEL_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( 1, obtained.getPartIdentifierList().size() );
		Assertions.assertTrue( obtained.getPartIdentifierList().contains( TEST_PART_ID ) );
	}

	@Test
	public void addModelPartNotFound() {
		// Given
		final UpdateModelCompositionRequest modelCompositionRequest = new UpdateModelCompositionRequest.Builder()
				.withModelId( TEST_MODEL_ID )
				.withPartId( TEST_PART_ID )
				.build();
		// When
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Exceptions
		Assertions.assertThrows( InvalidRequestException.class, () -> {
			final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
			modelServiceV1.addModelPart( modelCompositionRequest );
		} );
	}

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
	public void newModel() {
	}

	@Test
	public void removeModelPart() {
		// Given
		final UpdateModelCompositionRequest modelCompositionRequest = new UpdateModelCompositionRequest.Builder()
				.withModelId( TEST_MODEL_ID )
				.withPartId( TEST_PART_ID )
				.build();
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
//				.withStockAvailable( TEST_MODEL_STOCK_AVAILABLE )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build();
		// When
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		Mockito.when( this.modelRepository.save( modelEntity ) ).thenReturn( modelEntity );
		// Test
		final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
		modelServiceV1.addModelPart( modelCompositionRequest );
		Model obtained = modelServiceV1.removeModelPart( modelCompositionRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_MODEL_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( 0, obtained.getPartIdentifierList().size() );
		// Test
		modelServiceV1.addModelPart( modelCompositionRequest );
		modelServiceV1.removeModelPart( new UpdateModelCompositionRequest.Builder()
				.withModelId( TEST_MODEL_ID )
				.withPartId( UUID.randomUUID() )
				.build()
		);
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_MODEL_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( 1, obtained.getPartIdentifierList().size() );
		Assertions.assertTrue( obtained.getPartIdentifierList().contains( TEST_PART_ID ) );
	}

	@Test
	public void removeModelPartNotFound() {
		// Given
		final UpdateModelCompositionRequest modelCompositionRequest = new UpdateModelCompositionRequest.Builder()
				.withModelId( TEST_MODEL_ID )
				.withPartId( TEST_PART_ID )
				.build();
		// When
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Exceptions
		Assertions.assertThrows( InvalidRequestException.class, () -> {
			final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
			modelServiceV1.removeModelPart( modelCompositionRequest );
		} );
	}
}
