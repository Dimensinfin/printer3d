package org.dimensinfin.printer3d.backend.acceptance.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.model.CucumberTableToNewModelRequestConverter;
import org.dimensinfin.printer3d.backend.support.inventory.model.ModelValidator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelRequest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class P3D07ModelsSteps extends StepSupport {
	// - C O N S T R U C T O R S
	public P3D07ModelsSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("the model with id {string} has the next fields")
	public void the_model_with_id_has_the_next_fields( final String modelId, final List<Map<String, String>> dataTable ) {
		final List<Model> models = this.printer3DWorld.getModelListResponseEntity().getBody();
		Assertions.assertNotNull( models );
		for (final Model model : models) {
			if (model.getId().toString().equalsIgnoreCase( modelId ))
				Assertions.assertTrue( new ModelValidator().validate( dataTable.get( 0 ), model ) );
			return;
		}
		throw new DimensinfinRuntimeException( "The target Model not found on the results," );
	}

	@Given("the next New Model request")
	public void the_next_New_Model_request( final List<Map<String, String>> dataTable ) {
		final ModelRequest modelRequest = new CucumberTableToNewModelRequestConverter().convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( modelRequest );
		this.printer3DWorld.setModelRequest( modelRequest );
	}

	@Given("the next Update Model request")
	public void the_next_Update_Model_request( final List<Map<String, String>> dataTable ) {
		final ModelRequest modelRequest = new CucumberTableToNewModelRequestConverter().convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( modelRequest );
		this.printer3DWorld.setModelRequest( modelRequest );
	}

	@Then("the number of Models is {int}")
	public void the_number_of_Models_is( final Integer modelCount ) {
		Assertions.assertNotNull( this.printer3DWorld.getModelListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getModelListResponseEntity().getBody() );
		Assertions.assertEquals( modelCount, this.printer3DWorld.getModelListResponseEntity().getBody().size() );
	}

	@Then("the response for Model requests has the next fields")
	public void the_response_for_Model_requests_has_the_next_fields( final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getModelResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getModelResponseEntity().getBody() );
		Assertions.assertTrue(
				new ModelValidator().validate( dataTable.get( 0 ),
						this.printer3DWorld.getModelResponseEntity().getBody() )
		);
	}
}
