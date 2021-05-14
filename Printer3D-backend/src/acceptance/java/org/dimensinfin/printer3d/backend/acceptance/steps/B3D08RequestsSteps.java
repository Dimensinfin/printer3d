package org.dimensinfin.printer3d.backend.acceptance.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.production.request.rest.RequestRestErrors;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToCustomerRequestRequestV2Converter;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToRequestItemConverter;
import org.dimensinfin.printer3d.backend.support.production.request.CustomerRequestResponseV2Validator;
import org.dimensinfin.printer3d.backend.support.production.request.RequestContentValidator;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientSupport;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ITEM_ID;

public class B3D08RequestsSteps extends StepSupport {
	private final RequestFeignClientV2 requestFeignClientV2;
	private final RequestFeignClientSupport requestFeignClientSupport;

	// - C O N S T R U C T O R S
	public B3D08RequestsSteps( final @NotNull Printer3DWorld printer3DWorld,
	                           final @NotNull RequestFeignClientV2 requestFeignClientV2,
	                           final @NotNull RequestFeignClientSupport requestFeignClientSupport ) {
		super( printer3DWorld );
		this.requestFeignClientV2 = Objects.requireNonNull( requestFeignClientV2 );
		this.requestFeignClientSupport = Objects.requireNonNull( requestFeignClientSupport );
	}

	@Given("creating the next Request V2 with previous Contents")
	public void creating_the_next_Request_V2_with_previous_Contents( final List<Map<String, String>> dataTable ) {
		final CustomerRequestRequestV2 request = new CucumberTableToCustomerRequestRequestV2Converter( this.printer3DWorld.getRequestContents() )
				.convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( request );
		this.printer3DWorld.setCustomerRequestRequestV2( request );
	}

	//	@Given("creating the next incomplete Request V2 with previous Contents")
	//	public void creating_the_next_incomplete_Request_V2_with_previous_Contents( final List<Map<String, String>> dataTable ) {
	//		final CustomerRequestRequestV2 request =
	//				new CucumberTableToRequestV2TestingConverter( this.printer3DWorld.getRequestContents() ).convert( dataTable.get( 0 ) );
	//		Assertions.assertNotNull( request );
	//		this.printer3DWorld.setCustomerRequestRequestV2( request );
	//	}

	@Then("the Request V2 with id {string} has the next list of contents")
	public void the_Request_V2_with_id_has_the_next_list_of_contents( final String requestId,
	                                                                  final List<Map<String, String>> dataTable ) {
		final ResponseEntity<List<CustomerRequestResponseV2>> requests = this.printer3DWorld.getListRequestV2ResponseEntity();
		Assertions.assertNotNull( requests );
		Assertions.assertNotNull( requests.getBody() );
		for (final CustomerRequestResponseV2 request : requests.getBody()) {
			if (request.getId().toString().equalsIgnoreCase( requestId )) {
				for (final Map<String, String> row : dataTable) {
					for (final RequestItem content : request.getContents()) {
						if (content.getItemId().toString().equalsIgnoreCase( row.get( ITEM_ID ) ))
							Assertions.assertTrue( new RequestContentValidator().validate( row, content ) );
					}
				}
			}
		}
	}

	@Then("the list of Requests on the response is empty")
	public void the_list_of_Requests_on_the_response_is_empty() throws IOException {
		final ResponseEntity<List<CustomerRequestResponseV2>> requests = this.requestFeignClientV2.getOpenRequests();
		Assertions.assertNotNull( requests );
		Assertions.assertNotNull( requests.getBody() );
		Assertions.assertEquals( 0, requests.getBody().size() );
	}

	@Given("the next Request Contents List")
	public void the_next_Request_Contents_List( final List<Map<String, String>> dataTable ) {
		final CucumberTableToRequestItemConverter converter = new CucumberTableToRequestItemConverter();
		final List<RequestItem> requestContents = new ArrayList<>();
		for (final Map<String, String> row : dataTable)
			requestContents.add( converter.convert( row ) );
		this.printer3DWorld.setRequestContents( requestContents );
	}

	@Then("the number of records processed is {string}")
	public void the_number_of_records_processed_is( final String recordCount ) {
		Assertions.assertNotNull( this.printer3DWorld.getCounterResponseResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getCounterResponseResponseEntity().getBody() );
		Assertions.assertEquals( Integer.parseInt( recordCount ), this.printer3DWorld.getCounterResponseResponseEntity().getBody().getRecords() );
	}

	@Then("the Request Response returned has the next data")
	public void the_request_returned_has_the_next_data( final List<Map<String, String>> dataTable ) {
		final ResponseEntity<CustomerRequestResponseV2> request = this.printer3DWorld.getCustomerRequestResponseV2();
		Assertions.assertTrue( new CustomerRequestResponseV2Validator().validate( dataTable.get( 0 ), request.getBody() ) );
	}

	@Then("the resulting list of Requests has a request with id {string} with the next data")
	public void the_resulting_list_of_Requests_has_a_request_with_id_with_the_next_data( final String requestId,
	                                                                                     final List<Map<String, String>> dataTable ) {
		final ResponseEntity<List<CustomerRequestResponseV2>> requests = this.printer3DWorld.getListRequestV2ResponseEntity();
		Assertions.assertNotNull( requests );
		Assertions.assertNotNull( requests.getBody() );
		for (final CustomerRequestResponseV2 request : requests.getBody()) {
			if (request.getId().toString().equalsIgnoreCase( requestId )) {
				Assertions.assertTrue( new CustomerRequestResponseV2Validator().validate( dataTable.get( 0 ), request ) );
			}
		}
	}

	private CustomerRequestRequestV2 searchRequest( final String requestId, final List<CustomerRequestRequestV2> requests ) {
		for (final CustomerRequestRequestV2 request : requests)
			if (request.getId().toString().equals( requestId )) return request;
		throw new DimensinfinRuntimeException( RequestRestErrors.errorREQUESTNOTFOUND( UUID.fromString( requestId ) ) );
	}
}
