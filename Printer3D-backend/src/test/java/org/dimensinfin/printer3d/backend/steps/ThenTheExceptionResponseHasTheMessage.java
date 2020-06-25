package org.dimensinfin.printer3d.backend.steps;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;

import io.cucumber.java.en.Then;

public class ThenTheExceptionResponseHasTheMessage extends StepSupport {
	// - C O N S T R U C T O R S
	public ThenTheExceptionResponseHasTheMessage( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("the exception response contains the message {string}")
	public void the_exception_response_contains_the_message( final String exceptionMessage ) {
		final DimensinfinRuntimeException exception = this.printer3DWorld.getApplicationException();
		Assertions.assertNotNull( exception );
		Assertions.assertTrue( exception.getMessage().toLowerCase().contains( exceptionMessage.toLowerCase() ) );
	}

	@Then("the exception response has the message {string}")
	public void the_exception_response_has_the_message( final String exceptionMessage ) {
		final DimensinfinRuntimeException exception = this.printer3DWorld.getApplicationException();
		Assertions.assertNotNull( exception );
		Assertions.assertTrue( exception.getMessage().toLowerCase().contains( exceptionMessage.toLowerCase() ) );
	}
}
