package org.dimensinfin.printer3d.backend.inventory.model.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
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
	public void newModel() {
		// Given
		final ModelRequest modelRequest =		 new ModelRequest.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withPartIdList( new ArrayList<>() )
				.build();
		// Test
		final ModelServiceV1 modelServiceV1 = new ModelServiceV1( this.modelRepository, this.partRepository );
		final Model obtained = modelServiceV1.newModel( modelRequest );
	}

	@Test
	public void getModels() {
		final List<ModelEntity> modelEntityList = new ArrayList<>();
		modelEntityList.add( new ModelEntity.Builder()
				.withId( UUID.fromString("9ed337f1-eb5a-48ff-969f-6991e0f75918") )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.build()
		);
		modelEntityList.add( new ModelEntity.Builder()
				.withId( UUID.fromString("b783c038-04cd-4290-a7c5-9820f9e38991") )
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
}
