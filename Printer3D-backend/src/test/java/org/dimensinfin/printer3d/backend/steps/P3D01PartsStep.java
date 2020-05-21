package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.part.PartValidator;
import org.dimensinfin.printer3d.backend.support.part.converter.CucumberTableToPartConverter;
import org.dimensinfin.printer3d.client.Part;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class P3D01PartsStep extends StepSupport {

	// - C O N S T R U C T O R S
	public P3D01PartsStep( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Given("the next NewPart request")
	public void the_next_NewPart_request( final List<Map<String, String>> dataTable ) {
		final Part part = new CucumberTableToPartConverter().convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( part );
		this.printer3DWorld.setPart( part );
	}

	@Then("the response for new Part has the next fields")
	public void the_response_for_new_Part_has_the_next_fields( final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getNewPartResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getNewPartResponseEntity().getBody() );
		Assertions.assertTrue(
				new PartValidator().validate( dataTable.get( 0 ),
						this.printer3DWorld.getNewPartResponseEntity().getBody() )
		);
	}
}
