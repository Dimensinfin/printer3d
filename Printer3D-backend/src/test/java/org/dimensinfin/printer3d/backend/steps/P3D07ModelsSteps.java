package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.model.CucumberTableToNewModelRequestConverter;
import org.dimensinfin.printer3d.backend.support.inventory.model.ModelValidator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class P3D07ModelsSteps extends StepSupport {
	// - C O N S T R U C T O R S
	public P3D07ModelsSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Given("the next New Model request")
	public void the_next_New_Model_request( final List<Map<String, String>> dataTable ) {
		final NewModelRequest modelRequest = new CucumberTableToNewModelRequestConverter().convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( modelRequest );
		this.printer3DWorld.setNewModelRequest( modelRequest );
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
