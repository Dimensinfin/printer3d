package org.dimensinfin.poc.poccucumber.acceptance.steps;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.dimensinfin.poc.poccucumber.acceptance.support.POCWorld;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class POCSteps extends StepSupport {
	@Autowired
	public POCSteps( @NotNull final POCWorld world ) {
		super( world );
	}

	@Given("a clean Coils repository")
	public void a_clean_coils_repository() {
		// Write code here that turns the phrase above into concrete actions
		this.getWorld().setCoils( new ArrayList<>() );
	}

	@When("the Get Coils v2 request is processed")
	public void the_get_coils_v2_request_is_processed() {
		// Write code here that turns the phrase above into concrete actions
		this.getWorld().setHttpStatus( HttpStatus.OK );
	}

	@Then("there is a valid response with return code of {string}")
	public void there_is_a_valid_response_with_return_code_of( final String status ) {
		Assertions.assertEquals( this.getWorld().getHttpStatus().toString(), status );
	}

	@Then("the list of Coils has {int} items")
	public void the_list_of_coils_has_items( final Integer itemCount ) {
		Assertions.assertEquals( itemCount.intValue(), this.getWorld().getCoils().size() );
	}
}
