package org.dimensinfin.printer3d.backend.steps;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;

import io.cucumber.java.en.Then;

public class ThenThereIsAExceptionResponseWithReturnCode extends StepSupport {
	// - C O N S T R U C T O R S
	public ThenThereIsAExceptionResponseWithReturnCode( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("there is a exception response with return code of {string}")
	public void there_is_a_exception_response_with_return_code_of( final String httpReturnCode ) {
		Assertions.assertEquals( httpReturnCode, this.printer3DWorld.getHttpStatus().toString() );
	}
}
