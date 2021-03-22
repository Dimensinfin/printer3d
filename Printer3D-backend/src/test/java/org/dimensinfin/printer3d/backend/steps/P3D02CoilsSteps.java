package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.coil.CoilValidator;
import org.dimensinfin.printer3d.backend.support.inventory.coil.CucumberTableToCoilConverter;
import org.dimensinfin.printer3d.backend.support.inventory.coil.CucumberTableToUpdateCoilRequestConverter;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class P3D02CoilsSteps extends StepSupport {
	// - C O N S T R U C T O R S
	public P3D02CoilsSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("the Coil repository has a record for new Coil with the next fields")
	public void the_Coil_repository_has_a_record_for_new_Coil_with_the_next_fields( final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getCoilResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getCoilResponseEntity().getBody() );
		Assertions.assertTrue(
				new CoilValidator( this.printer3DWorld ).validate( dataTable.get( 0 ),
						this.printer3DWorld.getCoilResponseEntity().getBody() )
		);
	}

	@Then("the coil with id {string} has the next data")
	public void the_coil_with_id_has_the_next_data( final String coilId, final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getCoilListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getCoilListResponseEntity().getBody().getCoils() );
		for (final Coil coil : this.printer3DWorld.getCoilListResponseEntity().getBody().getCoils()) {
			if (coil.getId().toString().equalsIgnoreCase( coilId )) {
				Assertions.assertTrue(
						new CoilValidator( this.printer3DWorld ).validate( dataTable.get( 0 ), coil )
				);
			}
		}
	}

	@Then("the coil with id {string} of the list of Coils has the next fields")
	public void the_coil_with_id_of_the_list_of_Coils_has_the_next_fields( final String identifier, final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getCoilV2ListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getCoilV2ListResponseEntity().getBody() );
		this.printer3DWorld.getCoilV2ListResponseEntity().getBody()
				.stream()
				.filter( coil -> coil.getId().toString().equalsIgnoreCase( identifier ) )
				.forEach( coil -> Assertions.assertTrue(
						new CoilValidator( this.printer3DWorld ).validate( dataTable.get( 0 ), coil ) )
				);
	}

	@Then("the item {string} of the list of Coils has the next fields")
	public void the_item_of_the_list_of_Coils_has_the_next_fields( final String row, final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getCoilListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getCoilListResponseEntity().getBody().getCoils() );
		Assertions.assertNotNull( this.printer3DWorld.getCoilListResponseEntity().getBody().getCoils().get( Integer.parseInt( row ) - 1 ) );
		final Coil record = this.printer3DWorld.getCoilListResponseEntity().getBody().getCoils().get( Integer.parseInt( row ) - 1 );
		Assertions.assertTrue(
				new CoilValidator( this.printer3DWorld ).validate( dataTable.get( 0 ), record )
		);
	}

	@When("the item with id {string} of the list of Coils has the next fields")
	public void the_item_with_id_of_the_list_of_Coils_has_the_next_fields( final String targetId, final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getCoilListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getCoilListResponseEntity().getBody().getCoils() );
		for (final Coil coil : this.printer3DWorld.getCoilListResponseEntity().getBody().getCoils()) {
			if (coil.getId().toString().equals( targetId ))
				Assertions.assertTrue(
						new CoilValidator( this.printer3DWorld ).validate( dataTable.get( 0 ), coil )
				);
		}
	}

	@Then("the list of Coils has {int} items")
	public void the_list_of_Coils_has_items( final Integer coilCount ) {
		Assertions.assertNotNull( this.printer3DWorld.getCoilV2ListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getCoilV2ListResponseEntity().getBody() );
		Assertions.assertEquals( coilCount, this.printer3DWorld.getCoilV2ListResponseEntity().getBody().size() );
	}

	@Given("the next New Coil request")
	public void the_next_New_Coil_request( final List<Map<String, String>> dataTable ) {
		final Coil coil = new CucumberTableToCoilConverter( this.printer3DWorld ).convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( coil );
		this.printer3DWorld.setCoil( coil );
	}

	@Given("the next Update Coil request")
	public void the_next_Update_Coil_request( final List<Map<String, String>> dataTable ) {
		final UpdateCoilRequest updateCoilRequest = new CucumberTableToUpdateCoilRequestConverter().convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( updateCoilRequest );
		this.printer3DWorld.setUpdateCoilRequest( updateCoilRequest );
	}

	@Then("the number of Active {string} Coils is {int}")
	public void the_number_of_Active_Coils_is( final String activeState, final Integer recordCount ) {
		Assertions.assertNotNull( this.printer3DWorld.getCoilV2ListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getCoilV2ListResponseEntity().getBody() );
		final long count = this.printer3DWorld.getCoilV2ListResponseEntity().getBody()
				.stream()
				.filter( coil -> coil.getActive().equals( Boolean.parseBoolean( activeState ) ) )
				.count();
		Assertions.assertEquals( recordCount, (int) count );
	}
}
