package org.dimensinfin.poc.poccucumber.acceptance.steps;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.poc.poccucumber.acceptance.support.POCWorld;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
public class POCSteps extends StepSupport {
	@Autowired
	public POCSteps( @NotNull final POCWorld world ) {
		//		super( world );
	}

	@Given("a clean Coils repository")
	public void a_clean_coils_repository() {
		// Write code here that turns the phrase above into concrete actions

	}

	@When("the Get Coils v2 request is processed")
	public void the_get_coils_v2_request_is_processed() {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("there is a valid response with return code of {string}")
	public void there_is_a_valid_response_with_return_code_of( String string ) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}

	@Then("the list of Coils has {int} items")
	public void the_list_of_coils_has_items( Integer int1 ) {
		// Write code here that turns the phrase above into concrete actions
		throw new io.cucumber.java.PendingException();
	}
}
