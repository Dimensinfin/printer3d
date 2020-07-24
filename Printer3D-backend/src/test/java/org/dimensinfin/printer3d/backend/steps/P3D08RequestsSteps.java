package org.dimensinfin.printer3d.backend.steps;

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
import org.dimensinfin.printer3d.backend.production.request.rest.v2.RequestServiceV2;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToPartRequestConverter;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToRequestItemConverter;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToRequestV2Converter;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToRequestV2TestingConverter;
import org.dimensinfin.printer3d.backend.support.production.request.RequestContentValidator;
import org.dimensinfin.printer3d.backend.support.production.request.RequestV2Validator;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientSupport;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientV1;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ITEM_ID;

public class P3D08RequestsSteps extends StepSupport {
	private static final CucumberTableToPartRequestConverter cucumberTableToPartRequestConverter = new CucumberTableToPartRequestConverter();
	private final RequestFeignClientV1 requestFeignClientV1;
	private final RequestFeignClientV2 requestFeignClientV2;
	private final RequestFeignClientSupport requestFeignClientSupport;

	// - C O N S T R U C T O R S
	public P3D08RequestsSteps( final @NotNull Printer3DWorld printer3DWorld,
	                           final @NotNull RequestFeignClientV1 requestFeignClientV1,
	                           final @NotNull RequestFeignClientV2 requestFeignClientV2,
	                           final @NotNull RequestFeignClientSupport requestFeignClientSupport ) {
		super( printer3DWorld );
		this.requestFeignClientV1 = Objects.requireNonNull( requestFeignClientV1 );
		this.requestFeignClientV2 = Objects.requireNonNull( requestFeignClientV2 );
		this.requestFeignClientSupport = Objects.requireNonNull( requestFeignClientSupport );
	}

	@Given("creating the next Request V2 with previous Contents")
	public void creating_the_next_Request_V2_with_previous_Contents( final List<Map<String, String>> dataTable ) {
		final RequestV2 request = new CucumberTableToRequestV2Converter( this.printer3DWorld.getRequestContents() ).convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( request );
		this.printer3DWorld.setRequestV2( request );
	}

	@Given("creating the next incomplete Request V2 with previous Contents")
	public void creating_the_next_incomplete_Request_V2_with_previous_Contents( final List<Map<String, String>> dataTable ) {
		final RequestV2 request =
				new CucumberTableToRequestV2TestingConverter( this.printer3DWorld.getRequestContents() ).convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( request );
		this.printer3DWorld.setRequestV2( request );
	}

	@Then("the Request V2 with id {string} has the next list of contents")
	public void the_Request_V2_with_id_has_the_next_list_of_contents( final String requestId,
	                                                                  final List<Map<String, String>> dataTable ) {
		final ResponseEntity<List<RequestV2>> requests = this.printer3DWorld.getListRequestV2ResponseEntity();
		Assertions.assertNotNull( requests );
		Assertions.assertNotNull( requests.getBody() );
		for (RequestV2 request : requests.getBody()) {
			if (request.getId().toString().equalsIgnoreCase( requestId )) {
				for (Map<String, String> row : dataTable) {
					for (RequestItem content : request.getContents()) {
						if (content.getItemId().toString().equalsIgnoreCase( row.get( ITEM_ID ) ))
							Assertions.assertTrue( new RequestContentValidator().validate( row, content ) );
					}
				}
			}
		}
	}

	@Then("the list of Requests on the response is empty")
	public void the_list_of_Requests_on_the_response_is_empty() throws IOException {
		final ResponseEntity<List<RequestV2>> requests = this.requestFeignClientV2.getOpenRequests();
		Assertions.assertNotNull( requests );
		Assertions.assertNotNull( requests.getBody() );
		Assertions.assertEquals( 0, requests.getBody().size() );
	}

	@Given("the next Request Contents List")
	public void the_next_Request_Contents_List( final List<Map<String, String>> dataTable ) {
		final CucumberTableToRequestItemConverter converter = new CucumberTableToRequestItemConverter();
		final List<RequestItem> requestContents = new ArrayList<>();
		for (Map<String, String> row : dataTable)
			requestContents.add( converter.convert( row ) );
		this.printer3DWorld.setRequestContents( requestContents );
	}

	@Then("the number of records processed is {string}")
	public void the_number_of_records_processed_is( final String recordCount ) {
		Assertions.assertNotNull( this.printer3DWorld.getCounterResponseResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getCounterResponseResponseEntity().getBody() );
		Assertions.assertEquals( Integer.parseInt( recordCount ), this.printer3DWorld.getCounterResponseResponseEntity().getBody().getRecords() );
	}

	@Then("the request returned has the next data")
	public void the_request_returned_has_the_next_data( final List<Map<String, String>> dataTable ) {
		final ResponseEntity<RequestV2> request = this.printer3DWorld.getRequestV2ResponseEntity();
		Assertions.assertTrue( new RequestV2Validator().validate( dataTable.get( 0 ), request.getBody() ) );
	}

	@Then("the resulting list of Requests has a request with id {string} with the next data")
	public void the_resulting_list_of_Requests_has_a_request_with_id_with_the_next_data( final String requestId,
	                                                                                     final List<Map<String, String>> dataTable ) {
		final ResponseEntity<List<RequestV2>> requests = this.printer3DWorld.getListRequestV2ResponseEntity();
		Assertions.assertNotNull( requests );
		Assertions.assertNotNull( requests.getBody() );
		for (RequestV2 request : requests.getBody()) {
			if (request.getId().toString().equalsIgnoreCase( requestId )) {
				Assertions.assertTrue( new RequestV2Validator().validate( dataTable.get( 0 ), request ) );
			}
		}
	}

	private RequestV2 searchRequest( final String requestId, final List<RequestV2> requests ) {
		for (RequestV2 request : requests)
			if (request.getId().toString().equals( requestId )) return request;
		throw new DimensinfinRuntimeException( RequestServiceV2.errorREQUESTNOTFOUND( UUID.fromString( requestId ) ) );
	}
}
