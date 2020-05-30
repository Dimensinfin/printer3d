package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;

import io.cucumber.java.en.When;

public class P3D03FinishingsSteps extends StepSupport {
	// - C O N S T R U C T O R S
	public P3D03FinishingsSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
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
		Assertions.assertEquals( materialsCount, response.getBody().getMaterials().size() );
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
