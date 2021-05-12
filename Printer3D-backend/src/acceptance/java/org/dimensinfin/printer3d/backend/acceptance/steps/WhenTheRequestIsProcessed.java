package org.dimensinfin.printer3d.backend.acceptance.steps;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.RequestType;
import org.dimensinfin.printer3d.backend.support.accounting.rest.AccountFeignClientV1;
import org.dimensinfin.printer3d.backend.support.inventory.coil.rest.CoilFeignClientV1;
import org.dimensinfin.printer3d.backend.support.inventory.coil.rest.CoilFeignClientV2;
import org.dimensinfin.printer3d.backend.support.inventory.machine.rest.MachineFeignClientV1;
import org.dimensinfin.printer3d.backend.support.inventory.machine.rest.MachineFeignClientV2;
import org.dimensinfin.printer3d.backend.support.inventory.model.rest.ModelFeignClientV1;
import org.dimensinfin.printer3d.backend.support.inventory.part.rest.PartFeignClientV1;
import org.dimensinfin.printer3d.backend.support.inventory.part.rest.PartFeignClientV2;
import org.dimensinfin.printer3d.backend.support.production.job.rest.JobFeignClientV1;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientV2;
import org.dimensinfin.printer3d.backend.support.rest.Printer3DFeignClientSupport;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

import io.cucumber.java.en.When;

public class WhenTheRequestIsProcessed extends StepSupport {
	private final PartFeignClientV1 partFeignClientV1;
	private final PartFeignClientV2 partFeignClientV2;
	private final CoilFeignClientV1 coilFeignClientV1;
	private final CoilFeignClientV2 coilFeignClientV2;
	private final MachineFeignClientV1 machineFeignClientV1;
	private final MachineFeignClientV2 machineFeignClientV2;
	private final JobFeignClientV1 jobFeignClientV1;
	private final ModelFeignClientV1 modelFeignClientV1;
	private final RequestFeignClientV2 requestFeignClientV2;
	private final AccountFeignClientV1 accountFeignClientV1;
	private final Printer3DFeignClientSupport printer3DFeignClientSupport;

	// - C O N S T R U C T O R S
	public WhenTheRequestIsProcessed( @NotNull final Printer3DWorld printer3DWorld,
	                                  @NotNull final PartFeignClientV1 partFeignClientV1,
	                                  @NotNull final PartFeignClientV2 partFeignClientV2,
	                                  @NotNull final CoilFeignClientV1 coilFeignClientV1,
	                                  @NotNull final CoilFeignClientV2 coilFeignClientV2,
	                                  @NotNull final MachineFeignClientV1 machineFeignClientV1,
	                                  @NotNull final MachineFeignClientV2 machineFeignClientV2,
	                                  @NotNull final JobFeignClientV1 jobFeignClientV1,
	                                  @NotNull final ModelFeignClientV1 modelFeignClientV1,
	                                  @NotNull final RequestFeignClientV2 requestFeignClientV2,
	                                  @NotNull final AccountFeignClientV1 accountFeignClientV1,
	                                  @NotNull final Printer3DFeignClientSupport printer3DFeignClientSupport ) {
		super( printer3DWorld );
		this.partFeignClientV1 = Objects.requireNonNull( partFeignClientV1 );
		this.partFeignClientV2 = partFeignClientV2;
		this.coilFeignClientV1 = Objects.requireNonNull( coilFeignClientV1 );
		this.coilFeignClientV2 = coilFeignClientV2;
		this.machineFeignClientV1 = Objects.requireNonNull( machineFeignClientV1 );
		this.machineFeignClientV2 = Objects.requireNonNull( machineFeignClientV2 );
		this.jobFeignClientV1 = Objects.requireNonNull( jobFeignClientV1 );
		this.modelFeignClientV1 = Objects.requireNonNull( modelFeignClientV1 );
		this.requestFeignClientV2 = requestFeignClientV2;
		this.accountFeignClientV1 = accountFeignClientV1;
		this.printer3DFeignClientSupport = printer3DFeignClientSupport;
	}

	@When("the Accounting Week Income request is processed")
	public void the_Accounting_Week_Income_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.ACCOUNTING_REQUEST_BY_WEEK );
	}

	@When("the Accounting Week Income request is processed with week count {int}")
	public void the_Accounting_Week_Income_request_is_processed_with_week_count( final Integer weekCount ) throws IOException {
		this.printer3DWorld.setWeekCount( weekCount );
		this.processRequestByType( RequestType.ACCOUNTING_REQUEST_BY_WEEK );
	}

	@When("the Cancel Build for Machine labeled {string} request is processed")
	public void the_Cancel_Build_for_Machine_labeled_request_is_processed( final String machineName ) throws IOException {
		final UUID machineId = this.findMachineByLabel( machineName );
		this.printer3DWorld.setMachineId( machineId );
		this.processRequestByType( RequestType.CANCEL_BUILD );
	}

	@When("the Cancel Build for Machine {string} request is processed")
	public void the_Cancel_Build_for_Machine_request_is_processed( final String machineId ) throws IOException {
		this.printer3DWorld.setMachineId( UUID.fromString( machineId ) );
		this.processRequestByType( RequestType.CANCEL_BUILD );
	}

	@When("the Complete Job request for Machine {string} is processed")
	public void the_Complete_Job_request_for_Machine_is_processed( final String machineId ) throws IOException {
		this.printer3DWorld.setMachineId( UUID.fromString( machineId ) );
		this.processRequestByType( RequestType.COMPLETE_BUILD );
	}

	@When("the Complete Request request for request {string} is processed")
	public void the_Complete_Request_request_for_request_is_processed( final String requestId ) throws IOException {
		Objects.requireNonNull( requestId );
		this.printer3DWorld.setRequestId( UUID.fromString( requestId ) );
		this.processRequestByType( RequestType.CLOSE_REQUEST );
	}

	@When("the Delete Request request for request {string} is processed")
	public void the_Delete_Request_request_for_request_is_processed( final String requestId ) throws IOException {
		this.printer3DWorld.setRequestId( UUID.fromString( requestId ) );
		this.processRequestByType( RequestType.DELETE_REQUEST );
	}

	@When("the Get Coils v2 request is processed")
	public void the_Get_Coils_v2_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_COILS_V2 );
	}

	@When("the Get Finishings request is processed")
	public void the_Get_Finishings_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_FINISHINGS );
	}

	@When("the Get Jobs request is processed")
	public void the_Get_Jobs_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_JOBS );
	}

	@When("the Get Machines V2 request is processed")
	public void the_Get_Machines_V2_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_MACHINES_V2 );
	}

	@When("the Get Models request is processed")
	public void the_Get_Models_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_MODELS );
	}

	@When("the Get Parts request is processed")
	public void the_Get_Parts_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_PARTS );
	}

	@When("the Get Requests V2 request is processed")
	public void the_Get_Requests_V2_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_REQUESTSV2 );
	}

	@When("the New Coil request is processed")
	public void the_New_Coil_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_COIL );
	}

	@When("the New Model request is processed")
	public void the_New_Model_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_MODEL );
	}

	@When("the New Part request is processed")
	public void the_New_Part_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_PART );
	}

	@When("the New Request V2 request is processed")
	public void the_New_Request_V2_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_REQUESTV2 );
	}

	@When("the Start Build V2 for Machine labeled {string} request is processed")
	public void the_Start_Build_V2_for_Machine_labeled_request_is_processed( final String machineLabel ) throws IOException {
		final UUID machineId = this.findMachineByLabel( machineLabel );
		this.printer3DWorld.setMachineId( machineId );
		Assertions.assertNotNull( this.printer3DWorld.getJobRequest() );
		this.processRequestByType( RequestType.START_BUILDV2 );
	}

	@When("the Start Build V2 for Machine {string} request is processed")
	public void the_Start_Build_V2_for_for_Machine_request_is_processed( final String machineId ) throws IOException {
		this.printer3DWorld.setMachineId( UUID.fromString( machineId ) );
		Assertions.assertNotNull( this.printer3DWorld.getJobRequest() );
		this.processRequestByType( RequestType.START_BUILDV2 );
	}

	@When("the Update Coil request is processed")
	public void the_Update_Coil_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.UPDATE_COIL );
	}

	@When("the Update Group Parts with label {string} request is processed")
	public void the_Update_Group_Parts_with_label_request_is_processed( final String selectionLabel ) throws IOException {
		this.printer3DWorld.setSelectionLabel( selectionLabel );
		this.processRequestByType( RequestType.UPDATE_GROUP_PART );
	}

	@When("the Update Model request is processed")
	public void the_Update_Model_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.UPDATE_MODEL );
	}

	@When("the Update Part request is processed")
	public void the_Update_Part_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.UPDATE_PART );
	}

	@When("the Extract Closed Requests request is processed")
	public void the_extract_closed_requests_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.ACCOUNTING_CLOSED_REQUESTS_CSV_DATA );
	}

	@When("the Get Parts V2 request is processed")
	public void the_get_parts_v2_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_PARTS_V2 );
	}

	@When("the scheduler time is reset")
	public void the_scheduler_time_is_reset() throws IOException {
		this.processRequestByType( RequestType.SCHEDULER_COIL_RESET );
	}

	private ResponseEntity processRequest( final RequestType requestType ) throws IOException {
		switch (requestType) {
			case NEW_PART:
				Assertions.assertNotNull( this.printer3DWorld.getPart() );
				final ResponseEntity<Part> newPartResponseEntity = this.partFeignClientV1
						.newPart( this.printer3DWorld.getJwtAuthorizationToken(),
								this.printer3DWorld.getPart() );
				Assertions.assertNotNull( newPartResponseEntity );
				this.printer3DWorld.setPartResponseEntity( newPartResponseEntity );
				return newPartResponseEntity;
			case UPDATE_PART:
				Assertions.assertNotNull( this.printer3DWorld.getPart() );
				final ResponseEntity<Part> updatePartResponseEntity = this.partFeignClientV1
						.updatePart( this.printer3DWorld.getJwtAuthorizationToken(),
								this.printer3DWorld.getPart() );
				Assertions.assertNotNull( updatePartResponseEntity );
				this.printer3DWorld.setPartResponseEntity( updatePartResponseEntity );
				return updatePartResponseEntity;
			case UPDATE_GROUP_PART:
				Assertions.assertNotNull( this.printer3DWorld.getUpdateGroupPartRequest() );
				final UpdateGroupPartRequest request = this.printer3DWorld.getUpdateGroupPartRequest()
						.setLabel( this.printer3DWorld.getSelectionLabel() );
				final ResponseEntity<CounterResponse> updateGroupPartResponseEntity = this.partFeignClientV1.updateGroupPart( request );
				Assertions.assertNotNull( updateGroupPartResponseEntity );
				this.printer3DWorld.setCounterResponseResponseEntity( updateGroupPartResponseEntity );
				return updateGroupPartResponseEntity;
			case GET_PARTS:
				final ResponseEntity<PartList> partListResponseEntity = this.partFeignClientV1
						.getParts( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( partListResponseEntity );
				this.printer3DWorld.setPartListResponseEntity( partListResponseEntity );
				return partListResponseEntity;
			case NEW_COIL:
				Assertions.assertNotNull( this.printer3DWorld.getCoil() );
				final ResponseEntity<Coil> newCoilResponseEntity = this.coilFeignClientV1
						.newCoil( this.printer3DWorld.getJwtAuthorizationToken(),
								this.printer3DWorld.getCoil() );
				Assertions.assertNotNull( newCoilResponseEntity );
				this.printer3DWorld.setCoilResponseEntity( newCoilResponseEntity );
				return newCoilResponseEntity;
			case GET_FINISHINGS:
				final ResponseEntity<FinishingsResponse> finishingsResponseEntity = this.coilFeignClientV1
						.getFinishings( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( finishingsResponseEntity );
				this.printer3DWorld.setFinishingsResponseEntity( finishingsResponseEntity );
				return finishingsResponseEntity;
			case GET_MACHINES_V2:
				final ResponseEntity<List<MachineV2>> machinesV2ResponseEntity = this.machineFeignClientV2
						.getMachines( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( machinesV2ResponseEntity );
				this.printer3DWorld.setListMachineV2ResponseEntity( machinesV2ResponseEntity );
				return machinesV2ResponseEntity;
			case CANCEL_BUILD:
				final ResponseEntity<MachineV2> cancelBuildResponseEntity = this.machineFeignClientV1
						.cancelBuild( this.printer3DWorld.getJwtAuthorizationToken(), this.printer3DWorld.getMachineId() );
				Assertions.assertNotNull( cancelBuildResponseEntity );
				this.printer3DWorld.setMachineResponseEntity( cancelBuildResponseEntity );
				return cancelBuildResponseEntity;
			case COMPLETE_BUILD:
				final ResponseEntity<MachineV2> completeBuildResponseEntity = this.machineFeignClientV1
						.completeBuild( this.printer3DWorld.getMachineId() );
				Assertions.assertNotNull( completeBuildResponseEntity );
				this.printer3DWorld.setMachineResponseEntity( completeBuildResponseEntity );
				return completeBuildResponseEntity;
			case GET_JOBS:
				final ResponseEntity<List<Job>> pendingJobsResponseEntity = this.jobFeignClientV1
						.getPendingJobs();
				Assertions.assertNotNull( pendingJobsResponseEntity );
				this.printer3DWorld.setListJobResponseEntity( pendingJobsResponseEntity );
				return pendingJobsResponseEntity;
			case NEW_MODEL:
				Assertions.assertNotNull( this.printer3DWorld.getModelRequest() );
				final ResponseEntity<Model> newModelResponseEntity = this.modelFeignClientV1.newModel(
						this.printer3DWorld.getJwtAuthorizationToken(),
						this.printer3DWorld.getModelRequest() );
				Assertions.assertNotNull( newModelResponseEntity );
				this.printer3DWorld.setModelResponseEntity( newModelResponseEntity );
				return newModelResponseEntity;
			case UPDATE_MODEL:
				Assertions.assertNotNull( this.printer3DWorld.getModelRequest() );
				final ResponseEntity<Model> updateModelResponseEntity = this.modelFeignClientV1.updateModel(
						this.printer3DWorld.getJwtAuthorizationToken(),
						this.printer3DWorld.getModelRequest() );
				Assertions.assertNotNull( updateModelResponseEntity );
				this.printer3DWorld.setModelResponseEntity( updateModelResponseEntity );
				return updateModelResponseEntity;
			case NEW_REQUESTV2:
				Assertions.assertNotNull( this.printer3DWorld.getRequestV2() );
				final ResponseEntity<CustomerRequestRequestV2> newRequestV2ResponseEntity = this.requestFeignClientV2.newRequest(
						this.printer3DWorld.getRequestV2() );
				Assertions.assertNotNull( newRequestV2ResponseEntity );
				this.printer3DWorld.setRequestV2ResponseEntity( newRequestV2ResponseEntity );
				return newRequestV2ResponseEntity;
			case GET_REQUESTSV2:
				final ResponseEntity<List<CustomerRequestRequestV2>> getRequestsV2ResponseEntity = this.requestFeignClientV2.getOpenRequests();
				Assertions.assertNotNull( getRequestsV2ResponseEntity );
				this.printer3DWorld.setListRequestV2ResponseEntity( getRequestsV2ResponseEntity );
				return getRequestsV2ResponseEntity;
			case START_BUILDV2:
				final ResponseEntity<MachineV2> startBuildV2ResponseEntity = this.machineFeignClientV2
						.startBuild( this.printer3DWorld.getJwtAuthorizationToken(),
								this.printer3DWorld.getMachineId(),
								this.printer3DWorld.getJobRequest() );
				Assertions.assertNotNull( startBuildV2ResponseEntity );
				this.printer3DWorld.setMachineResponseEntity( startBuildV2ResponseEntity );
				return startBuildV2ResponseEntity;
			case GET_MODELS:
				final ResponseEntity<List<Model>> modelListResponseEntity = this.modelFeignClientV1
						.getModels( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( modelListResponseEntity );
				this.printer3DWorld.setModelListResponseEntity( modelListResponseEntity );
				return modelListResponseEntity;
			case UPDATE_COIL:
				Assertions.assertNotNull( this.printer3DWorld.getUpdateCoilRequest() );
				final ResponseEntity<Coil> updateCoilResponseEntity = this.coilFeignClientV1.updateCoil(
						this.printer3DWorld.getUpdateCoilRequest() );
				Assertions.assertNotNull( updateCoilResponseEntity );
				this.printer3DWorld.setCoilResponseEntity( updateCoilResponseEntity );
				return updateCoilResponseEntity;
			case CLOSE_REQUEST:
				final ResponseEntity<CustomerRequestRequestV2> closeRequestResponseEntity = this.requestFeignClientV2.closeRequest(
						this.printer3DWorld.getRequestId()
				);
				Assertions.assertNotNull( closeRequestResponseEntity );
				this.printer3DWorld.setRequestV2ResponseEntity( closeRequestResponseEntity );
				return closeRequestResponseEntity;
			case DELETE_REQUEST:
				Assertions.assertNotNull( this.printer3DWorld.getRequestId() );
				final ResponseEntity<CounterResponse> deleteRequestResponseEntity = this.requestFeignClientV2.deleteRequest(
						this.printer3DWorld.getRequestId() );
				Assertions.assertNotNull( deleteRequestResponseEntity );
				this.printer3DWorld.setCounterResponseResponseEntity( deleteRequestResponseEntity );
				return deleteRequestResponseEntity;
			case ACCOUNTING_REQUEST_BY_WEEK:
				final Integer weekCount = this.printer3DWorld.getWeekCount();
				final ResponseEntity<List<WeekAmount>> accountingRequestWeekResponseEntity = this.accountFeignClientV1
						.getRequestsAmountPerWeek( weekCount );
				Assertions.assertNotNull( accountingRequestWeekResponseEntity );
				this.printer3DWorld.setListWeekAmountResponseEntity( accountingRequestWeekResponseEntity );
				return accountingRequestWeekResponseEntity;
			case GET_COILS_V2:
				final ResponseEntity<List<Coil>> coilV2ListResponseEntity = this.coilFeignClientV2
						.getCoils( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( coilV2ListResponseEntity );
				this.printer3DWorld.setCoilV2ListResponseEntity( coilV2ListResponseEntity );
				return coilV2ListResponseEntity;
			case GET_PARTS_V2:
				final ResponseEntity<List<Part>> partListV2ResponseEntity = this.partFeignClientV2
						.getPartsV2( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( partListV2ResponseEntity );
				this.printer3DWorld.setPartListV2ResponseEntity( partListV2ResponseEntity );
				return partListV2ResponseEntity;
			case SCHEDULER_COIL_RESET:
				return this.printer3DFeignClientSupport.resetCoilScheduler();
			case ACCOUNTING_CLOSED_REQUESTS_CSV_DATA:
				final ResponseEntity<String> closedRequestsDataResponseEntity = this.accountFeignClientV1.downloadClosedRequestsData();
				Assertions.assertNotNull( closedRequestsDataResponseEntity );
				this.printer3DWorld.setClosedRequestsDataResponseEntity( closedRequestsDataResponseEntity );
				return closedRequestsDataResponseEntity;
			default:
				throw new NotImplementedException( "Request {} not implemented.", requestType.name() );
		}
	}

	private void processRequestByType( final RequestType requestType ) throws IOException {
		try {
			final ResponseEntity<?> response = this.processRequest( requestType );
			this.printer3DWorld.setHttpStatus( response.getStatusCode() );
		} catch (final DimensinfinRuntimeException runtime) {
			this.printer3DWorld.setHttpStatus( runtime.getHttpStatus() );
			this.printer3DWorld.setApplicationException( runtime );
			LogWrapper.error( runtime );
		}
	}
}
