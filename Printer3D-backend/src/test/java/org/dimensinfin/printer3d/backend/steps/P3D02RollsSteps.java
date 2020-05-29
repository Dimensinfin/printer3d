package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;
import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.part.PartValidator;
import org.dimensinfin.printer3d.backend.support.roll.CucumberTableToRollConverter;
import org.dimensinfin.printer3d.backend.support.roll.RollValidator;
import org.dimensinfin.printer3d.client.domain.RollList;

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
				new RollValidator( this.printer3DWorld ).validate( dataTable.get( 0 ),
						this.printer3DWorld.getNewRollResponseEntity().getBody() )
		);
	}

	@Then("the list of Rolls has {string} items")
	public void the_list_of_Rolls_has_items( final String rollCount ) {
		final ResponseEntity<RollList> rollListResponseEntity = this.printer3DWorld.getRollListResponseEntity();
		Assertions.assertNotNull( rollListResponseEntity );
		Assertions.assertNotNull( rollListResponseEntity.getBody() );
		Assertions.assertEquals( Integer.parseInt( rollCount ), rollListResponseEntity.getBody().getCount() );
	}

	@Given("the next New Roll request")
	public void the_next_New_Roll_request( final List<Map<String, String>> dataTable ) {
		final Roll roll = new CucumberTableToRollConverter( this.printer3DWorld ).convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( roll );
		this.printer3DWorld.setRoll( roll );
	}@Then("the item {string} of the list of Rolls has the next fields")
	public void the_item_of_the_list_of_Rolls_has_the_next_fields(final String row, final List<Map<String, String>> dataTable) {
		Assertions.assertNotNull( this.printer3DWorld.getRollListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getRollListResponseEntity().getBody().getRolls() );
		Assertions.assertNotNull( this.printer3DWorld.getRollListResponseEntity().getBody().getRolls().get( Integer.parseInt( row ) - 1 ) );
		final Roll record = this.printer3DWorld.getRollListResponseEntity().getBody().getRolls().get( Integer.parseInt( row ) - 1 );
		Assertions.assertTrue(
				new  RollValidator().validate( dataTable.get( 0 ), record )
		);
	}
}
