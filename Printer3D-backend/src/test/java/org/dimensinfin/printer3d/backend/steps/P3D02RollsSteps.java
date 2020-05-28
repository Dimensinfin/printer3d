package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.roll.CucumberTableToRollConverter;
import org.dimensinfin.printer3d.backend.support.roll.RollValidator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class P3D02RollsSteps extends StepSupport {
	// - C O N S T R U C T O R S
	public P3D02RollsSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("the Roll repository has a record for new Roll with the next fields")
	public void the_Roll_repository_has_a_record_for_new_Roll_with_the_next_fields( final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getNewRollResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getNewRollResponseEntity().getBody() );
		Assertions.assertTrue(
				new RollValidator(this.printer3DWorld).validate( dataTable.get( 0 ),
						this.printer3DWorld.getNewRollResponseEntity().getBody() )
		);
	}

	@Given("the next New Roll request")
	public void the_next_New_Roll_request( final List<Map<String, String>> dataTable ) {
		final Roll roll = new CucumberTableToRollConverter( this.printer3DWorld ).convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( roll );
		this.printer3DWorld.setRoll( roll );
	}
}
