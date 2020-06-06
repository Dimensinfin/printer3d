package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.part.PartValidator;
import org.dimensinfin.printer3d.backend.support.part.converter.CucumberTableToPartConverter;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class P3D01PartsSteps extends StepSupport {

	// - C O N S T R U C T O R S
	public P3D01PartsSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("the item {string} of the list of Parts has the next fields")
	public void the_item_of_the_list_of_Parts_has_the_next_fields( final String partId, final List<Map<String, String>> dataTable ) {
		Assertions.assertNotNull( this.printer3DWorld.getPartListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getPartListResponseEntity().getBody().getParts() );
		for ( Part part : this.printer3DWorld.getPartListResponseEntity().getBody().getParts()){
			if ( part.getId().toString().equalsIgnoreCase( partId ))
				Assertions.assertTrue(
						new PartValidator().validate( dataTable.get( 0 ), part )
				);
		}
	}

	@Then("the list of Parts has {string} items")
	public void the_list_of_Parts_has_items( final String partCount ) {
		final ResponseEntity<PartList> partListResponseEntity = this.printer3DWorld.getPartListResponseEntity();
		Assertions.assertNotNull( partListResponseEntity );
		Assertions.assertNotNull( partListResponseEntity.getBody() );
		Assertions.assertEquals( Integer.parseInt( partCount ), partListResponseEntity.getBody().getCount() );
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
