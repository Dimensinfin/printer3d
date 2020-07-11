package org.dimensinfin.printer3d.backend.inventory.model.rest.v1;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ID;

public class ModelControllerV1Test {

	private ModelServiceV1 modelServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.modelServiceV1 = Mockito.mock( ModelServiceV1.class );
	}

	@Test
	public void constructorContract() {
		final ModelControllerV1 modelControllerV1 = new ModelControllerV1( this.modelServiceV1 );
		Assertions.assertNotNull( modelControllerV1 );
	}

	@Test
	public void getModels() {
		// Given
		final ModelList models = Mockito.mock( ModelList.class );
		// When
		Mockito.when( this.modelServiceV1.getModels() ).thenReturn( models );
		Mockito.when( models.getModels() ).thenReturn( new ArrayList<>() );
		Mockito.when( models.getCount() ).thenReturn( 2 );
		// Test
		final ModelControllerV1 modelControllerV1 = new ModelControllerV1( this.modelServiceV1 );
		final ResponseEntity<ModelList> obtained = modelControllerV1.getModels();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertNotNull( obtained.getBody().getModels() );
		Assertions.assertEquals( 2, obtained.getBody().getCount() );
	}

	@Test
	public void newModel() {
		// Given
		final NewModelRequest newModelRequest = Mockito.mock( NewModelRequest.class );
		final Model model = Mockito.mock( Model.class );
		// When
		Mockito.when( this.modelServiceV1.newModel( Mockito.any( NewModelRequest.class ) ) ).thenReturn( model );
		Mockito.when( model.getId() ).thenReturn( TEST_MODEL_ID );
		// Test
		final ModelControllerV1 modelControllerV1 = new ModelControllerV1( this.modelServiceV1 );
		final ResponseEntity<Model> obtained = modelControllerV1.newModel( newModelRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( TEST_MODEL_ID.toString(), obtained.getBody().getId().toString() );
	}

	@Test
	public void updateModel() {
		// Given
		final NewModelRequest modelRequest = Mockito.mock( NewModelRequest.class );
		final Model model = Mockito.mock( Model.class );
		// When
		Mockito.when( this.modelServiceV1.updateModel( modelRequest ) ).thenReturn( model );
		// Test
		final ModelControllerV1 modelControllerV1 = new ModelControllerV1( this.modelServiceV1 );
		final ResponseEntity<Model> obtained = modelControllerV1.updateModel( modelRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
	}
}
