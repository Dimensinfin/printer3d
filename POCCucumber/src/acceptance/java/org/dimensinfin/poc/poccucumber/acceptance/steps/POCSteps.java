package org.dimensinfin.poc.poccucumber.acceptance.steps;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.dimensinfin.poc.poccucumber.acceptance.support.POCWorld;
import org.dimensinfin.poc.poccucumber.application.usecases.CoilServiceV2;
import org.dimensinfin.poc.poccucumber.domain.Coil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class POCSteps extends StepSupport {
	private final CoilServiceV2 coilService;

	@Autowired
	public POCSteps( @NotNull final POCWorld world, final CoilServiceV2 coilService ) {
		super( world );
		this.coilService = coilService;
	}

	@Given("a clean Coils repository")
	public void a_clean_coils_repository() {
		// Write code here that turns the phrase above into concrete actions
		this.getWorld().setCoils( new ArrayList<>() );
	}

	@When("the Get Coils v2 request is processed")
	public void the_get_coils_v2_request_is_processed() {
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

	@When("the Create Coils v2 request is processed")
	public void the_create_coils_v2_request_is_processed() {
		final String url = "http://localhost:9001/api/v2/inventory/coils"; // Adjust the URL as needed
		final HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-Type", "application/json" );

		final HttpEntity<Coil> requestEntity = new HttpEntity<>( Coil.builder()
				.name( "New Coil" )
				.build(), headers );
		final ResponseEntity<Coil> responseEntity = new RestTemplate().postForEntity( url, requestEntity, Coil.class );
		this.getWorld().setResponseEntityCoil( responseEntity );
		this.getWorld().setHttpStatus( HttpStatus.CREATED );
		this.getWorld().setCoils( this.coilService.getCoils() );
	}

}
