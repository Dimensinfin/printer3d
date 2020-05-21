package org.dimensinfin.printer3d.backend.steps;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;

import io.cucumber.java.en.Then;

public class ThenThereIsAValidResponseWithReturnCode extends StepSupport {
	// - C O N S T R U C T O R S
	public ThenThereIsAValidResponseWithReturnCode( final Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("there is a valid response with return code of {string}")
	public void there_is_a_valid_response_with_return_code_of( final String httpReturnCode ) {
		Assertions.assertEquals( httpReturnCode, this.printer3DWorld.getHttpStatus().toString() );
	}
}
