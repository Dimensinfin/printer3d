package org.dimensinfin.printer3d.backend.acceptance.steps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants;
import org.dimensinfin.printer3d.backend.support.inventory.part.CucumberTableToPartConverter;
import org.dimensinfin.printer3d.backend.support.inventory.part.CucumberTableToUpdateGroupPartRequestConverter;
import org.dimensinfin.printer3d.backend.support.inventory.part.PartValidator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class B3D01PartsSteps extends StepSupport {

	// - C O N S T R U C T O R S
	public B3D01PartsSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("the item {string} of the list of Parts has the next fields")
	public void the_item_of_the_list_of_Parts_has_the_next_fields( final String partId, final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getPartListV2ResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getPartListV2ResponseEntity().getBody() );
		for (final Part part : this.printer3DWorld.getPartListV2ResponseEntity().getBody()) {
			if (part.getId().toString().equalsIgnoreCase( partId ))
				Assertions.assertTrue(
						new PartValidator().validate( dataTable.get( 0 ), part )
				);
		}
	}

	@Then("the list of Parts has {string} items")
	public void the_list_of_Parts_has_items( final String partCount ) {
		final ResponseEntity<List<Part>> partListResponseEntity = this.printer3DWorld.getPartListV2ResponseEntity();
		Assertions.assertNotNull( partListResponseEntity );
		Assertions.assertNotNull( partListResponseEntity.getBody() );
		Assertions.assertEquals( Integer.parseInt( partCount ), partListResponseEntity.getBody().size() );
	}

	@Given("the next NewPart request")
	public void the_next_NewPart_request( final List<Map<String, String>> dataTable ) {
		final Part part = new CucumberTableToPartConverter().convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( part );
		this.printer3DWorld.setPart( part );
	}

	@Given("the next Update Group Part request")
	public void the_next_Update_Group_Part_request( final List<Map<String, String>> dataTable ) {
		final UpdateGroupPartRequest updateGroupPartRequest = new CucumberTableToUpdateGroupPartRequestConverter()
				.convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( updateGroupPartRequest );
		this.printer3DWorld.setUpdateGroupPartRequest( updateGroupPartRequest );
	}

	@Given("the next Update Part request")
	public void the_next_Update_Part_request( final List<Map<String, String>> dataTable ) {
		final Part part = new CucumberTableToPartConverter().convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( part );
		this.printer3DWorld.setPart( part );
	}

	@When("the number of Parts is {int}")
	public void the_number_of_parts_is( final Integer partCount ) {
		Assertions.assertNotNull( this.printer3DWorld.getPartListV2ResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getPartListV2ResponseEntity().getBody() );
		Assertions.assertEquals( partCount, this.printer3DWorld.getPartListV2ResponseEntity().getBody().size() );
	}

	@When("the Part with id {string} of the list of Parts has the next fields")
	public void the_part_with_id_of_the_list_of_parts_has_the_next_fields( final String identifier, final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getPartListV2ResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getPartListV2ResponseEntity().getBody() );
		final List<Part> target = this.printer3DWorld.getPartListV2ResponseEntity().getBody()
				.stream()
				.filter( part -> part.getId().toString().equalsIgnoreCase( identifier ) )
				.collect( Collectors.toList() );
		Assertions.assertNotNull( target );
		Assertions.assertEquals( 1, target.size() );
		Assertions.assertTrue( new PartValidator().validate( dataTable.get( 0 ), target.get( 0 ) ) );
	}

	@Then("the response for Update Part has the next fields")
	public void the_response_for_Update_Part_has_the_next_fields( final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getPartResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getPartResponseEntity().getBody() );
		Assertions.assertTrue(
				new PartValidator().validate( dataTable.get( 0 ),
						this.printer3DWorld.getPartResponseEntity().getBody() )
		);
	}

	@Then("the response for new Part has the next fields")
	public void the_response_for_new_Part_has_the_next_fields( final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getPartResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getPartResponseEntity().getBody() );
		Assertions.assertTrue(
				new PartValidator().validate( dataTable.get( 0 ),
						this.printer3DWorld.getPartResponseEntity().getBody() )
		);
	}

	@Then("we have Part with id {string} with the next values")
	public void we_have_part_with_id_with_the_next_values( final String partId, final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getPartListV2ResponseEntity() );
		Assertions.assertFalse( this.printer3DWorld.getPartListV2ResponseEntity().getBody().isEmpty() );
		for (final Part partRecord : this.printer3DWorld.getPartListV2ResponseEntity().getBody()) {
			if (partRecord.getId().toString().equalsIgnoreCase( partId )) {
				Assertions.assertTrue(
						new PartValidator().validate( dataTable.get( 0 ), partRecord )
				);
			}
		}
	}

	@Then("we have the next list of Parts at the repository")
	public void we_have_the_next_list_of_Parts_at_the_repository( final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getPartListV2ResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getPartListV2ResponseEntity().getBody() );
		for (final Map<String, String> row : dataTable) {
			final String rowId = row.get( AcceptanceFieldMapConstants.ID );
			for (final Part part : this.printer3DWorld.getPartListV2ResponseEntity().getBody()) {
				if (part.getId().toString().equalsIgnoreCase( rowId ))
					Assertions.assertTrue(
							new PartValidator().validate( row, part )
					);
			}
		}
	}
}
