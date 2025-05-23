package org.dimensinfin.printer3d.backend.acceptance.steps;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;

import io.cucumber.java.en.Then;

public class ThenTheExceptionResponseHas extends StepSupport {
	// - C O N S T R U C T O R S
	public ThenTheExceptionResponseHas( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("the exception response contains the message {string}")
	public void the_exception_response_contains_the_message( final String exceptionMessage ) {
		final DimensinfinRuntimeException exception = this.printer3DWorld.getApplicationException();
		Assertions.assertNotNull( exception );
		Assertions.assertTrue( exception.getMessage().toLowerCase().contains( exceptionMessage.toLowerCase() ) );
	}

	@Then("the exception response has the cause {string}")
	public void the_exception_response_has_the_cause( final String cause ) {
		final DimensinfinRuntimeException exception = this.printer3DWorld.getApplicationException();
		Assertions.assertNotNull( exception );
		Assertions.assertEquals( exception.getCauseMessage().toLowerCase(), cause.toLowerCase() );
	}

	@Then("the exception response has the message {string}")
	public void the_exception_response_has_the_message( final String exceptionMessage ) {
		final DimensinfinRuntimeException exception = this.printer3DWorld.getApplicationException();
		Assertions.assertNotNull( exception );
		Assertions.assertEquals( exception.getMessage().toLowerCase(), exceptionMessage.toLowerCase() );
	}

	@Then("the exception response name is {string}")
	public void the_exception_response_name_is( final String errorName ) {
		final DimensinfinRuntimeException exception = this.printer3DWorld.getApplicationException();
		Assertions.assertNotNull( exception );
		Assertions.assertEquals( exception.getErrorName().toLowerCase(), errorName.toLowerCase() );
	}
}
