package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToNewRequestConverter;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToPartRequestConverter;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToRequestItemConverter;
import org.dimensinfin.printer3d.backend.support.production.request.CucumberTableToRequestV2Converter;
import org.dimensinfin.printer3d.backend.support.production.request.RequestContentValidator;
import org.dimensinfin.printer3d.backend.support.production.request.RequestV2Validator;
import org.dimensinfin.printer3d.backend.support.production.request.RequestValidator;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientSupport;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientV1;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientV2;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
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

	@Given("creating the next Request V1 with previous Contents on repository")
	public void creating_the_next_Request_V1_with_previous_Contents_on_repository( final List<Map<String, String>> dataTable ) throws IOException {
		final RequestV2 request = new CucumberTableToRequestV2Converter( this.printer3DWorld.getRequestContents() ).convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( request );
		this.printer3DWorld.setRequestV2( request );
		// Creating the request at the repository using the endpoint
		final ResponseEntity<RequestV2> requestResponse = this.requestFeignClientV2.newRequest( request );
		Assertions.assertNotNull( requestResponse );
	}

	@Then("the Request V2 with id {string} the next list of contents")
	public void the_Request_V2_with_id_the_next_list_of_contents( final String requestId,
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
		final ResponseEntity<RequestList> requests = this.requestFeignClientV1.getOpenRequests();
		Assertions.assertNotNull( requests );
		Assertions.assertNotNull( requests.getBody() );
		Assertions.assertEquals( 0, requests.getBody().getRequests().size() );
	}

	@Given("the next New Request request with the current Part Request List")
	public void the_next_New_Request_request_with_the_current_Part_Request_List( final List<Map<String, String>> dataTable ) {
		final Request request = new CucumberTableToNewRequestConverter( this.printer3DWorld.getPartRequestList() ).convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( request );
		this.printer3DWorld.setNewRequest( request );
	}

	/**
	 * Builds a list of Part Request records to be used on next New Request backend endpoint calls. THis will help to simplify the test data on the
	 * cucumber feature files.
	 */
	@Given("the next Part Request List")
	public void the_next_Part_Request_List( final List<Map<String, String>> dataTable ) {
		final List<PartRequest> partRequests = new ArrayList<>();
		for (Map<String, String> row : dataTable)
			partRequests.add( cucumberTableToPartRequestConverter.convert( row ) );
		this.printer3DWorld.setPartRequestList( partRequests );
	}

	@Given("the next Request Contents List")
	public void the_next_Request_Contents_List( final List<Map<String, String>> dataTable ) {
		final CucumberTableToRequestItemConverter converter = new CucumberTableToRequestItemConverter();
		final List<RequestItem> requestContents = new ArrayList<>();
		for (Map<String, String> row : dataTable)
			requestContents.add( converter.convert( row ) );
		this.printer3DWorld.setRequestContents( requestContents );
	}

	/**
	 * Reads the content of the Request repository without processing or any other data manipulation. The request of the list of open requests will do
	 * some reprocessing with the part storage. This endpoint should not generate any of that effect.
	 */
	@Then("the repository list of Requests has the next contents")
	public void the_repository_list_of_Requests_has_the_next_contents( final List<Map<String, String>> dataTable ) throws IOException {
		final ResponseEntity<RequestList> requests = this.requestFeignClientSupport.getRepositoryRequests();
		Assertions.assertNotNull( requests );
		Assertions.assertNotNull( requests.getBody() );
		for (Map<String, String> row : dataTable) {
			final Request record = this.searchRequest( row.get( ID ), requests.getBody() );
			Assertions.assertTrue( new RequestValidator().validate( row, record ) );
		}
	}

	@Then("the response to Get Requests has the next contents")
	public void the_response_to_Get_Requests_has_the_next_contents( final List<Map<String, String>> dataTable ) throws IOException {
		final ResponseEntity<RequestList> requests = this.requestFeignClientV1.getOpenRequests();
		Assertions.assertNotNull( requests );
		Assertions.assertNotNull( requests.getBody() );
		for (Map<String, String> row : dataTable) {
			final Request record = this.searchRequest( row.get( ID ), requests.getBody() );
			Assertions.assertTrue( new RequestValidator().validate( row, record ) );
		}
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

	private Request searchRequest( final String requestId, final RequestList requests ) {
		for (Request request : requests.getRequests())
			if (request.getId().toString().equals( requestId )) return request;
		throw new DimensinfinRuntimeException( ErrorInfo.REQUEST_NOT_FOUND.getErrorMessage( requestId ) );
	}
}
