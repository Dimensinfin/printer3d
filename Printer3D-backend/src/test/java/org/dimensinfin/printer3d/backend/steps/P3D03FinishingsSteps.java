package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Finishing;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;

import io.cucumber.java.en.When;

public class P3D03FinishingsSteps extends StepSupport {
	// - C O N S T R U C T O R S
	public P3D03FinishingsSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@When("the list of colors  of material {string} is ordered alphabetically")
	public void the_list_of_colors_of_material_is_ordered_alphabetically( final String materialName ) {
		final ResponseEntity<FinishingsResponse> response = this.printer3DWorld.getFinishingsResponseEntity();
		Assertions.assertNotNull( response );
		for (final Finishing material : response.getBody().getMaterials())
			if (material.getMaterial().equalsIgnoreCase( materialName ))
				Assertions.assertTrue( this.checkIfSorted( material.getColors() ) );
	}

	@When("the {string} material record has {int} colors")
	public void the_material_record_has_colors( final String materialName, final Integer colorCount ) {
		final ResponseEntity<FinishingsResponse> response = this.printer3DWorld.getFinishingsResponseEntity();
		Assertions.assertNotNull( response );
		for (final Finishing material : response.getBody().getMaterials())
			if (material.getMaterial().equalsIgnoreCase( materialName )) {
				Assertions.assertEquals( colorCount.intValue(), material.getColors().size() );
			}
	}

	@When("the material records are ordered alphabetically")
	public void the_material_records_are_ordered_alphabetically() {
		final ResponseEntity<FinishingsResponse> response = this.printer3DWorld.getFinishingsResponseEntity();
		Assertions.assertNotNull( response );
		Assertions.assertTrue( this.checkIfSorted( response.getBody().getMaterials()
				.stream()
				.map( finishing -> finishing.getMaterial() )
				.collect( Collectors.toList() ) )
		);
	}

	@When("the response has {int} material records")
	public void the_response_has_material_records( final Integer materialsCount ) {
		final ResponseEntity<FinishingsResponse> response = this.printer3DWorld.getFinishingsResponseEntity();
		Assertions.assertNotNull( response );
		Assertions.assertEquals( materialsCount.intValue(), response.getBody().getMaterials().size() );
	}

	private boolean checkIfSorted( final List<String> input ) {
		String previous = ""; // empty string: guaranteed to be less than or equal to any other
		for (final String current : input) {
			if (current.compareTo( previous ) < 0)
				return false;
			previous = current;
		}
		return true;
	}
}
