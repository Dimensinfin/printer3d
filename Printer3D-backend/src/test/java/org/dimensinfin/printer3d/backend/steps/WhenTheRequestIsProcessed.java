package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.LogWrapperLocal;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.RequestType;
import org.dimensinfin.printer3d.backend.support.inventory.coil.rest.CoilFeignClientV1;
import org.dimensinfin.printer3d.backend.support.inventory.machine.rest.MachineFeignClientV1;
import org.dimensinfin.printer3d.backend.support.inventory.machine.rest.MachineFeignClientV2;
import org.dimensinfin.printer3d.backend.support.inventory.model.rest.ModelFeignClientV1;
import org.dimensinfin.printer3d.backend.support.inventory.part.rest.PartFeignClientV1;
import org.dimensinfin.printer3d.backend.support.production.job.rest.JobFeignClientV1;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import io.cucumber.java.en.When;

public class WhenTheRequestIsProcessed extends StepSupport {
	private final PartFeignClientV1 partFeignClientV1;
	private final CoilFeignClientV1 coilFeignClientV1;
	private final MachineFeignClientV1 machineFeignClientV1;
	private final MachineFeignClientV2 machineFeignClientV2;
	private final JobFeignClientV1 jobFeignClientV1;
	private final ModelFeignClientV1 modelFeignClientV1;
//	private final RequestFeignClientV1 requestFeignClientV1;
	private final RequestFeignClientV2 requestFeignClientV2;

	// - C O N S T R U C T O R S
	public WhenTheRequestIsProcessed( final @NotNull Printer3DWorld printer3DWorld,
	                                  final @NotNull PartFeignClientV1 partFeignClientV1,
	                                  final @NotNull CoilFeignClientV1 coilFeignClientV1,
	                                  final @NotNull MachineFeignClientV1 machineFeignClientV1,
	                                  final @NotNull MachineFeignClientV2 machineFeignClientV2,
	                                  final @NotNull JobFeignClientV1 jobFeignClientV1,
	                                  final @NotNull ModelFeignClientV1 modelFeignClientV1,
//	                                  final @NotNull RequestFeignClientV1 requestFeignClientV1,
	                                  final @NotNull RequestFeignClientV2 requestFeignClientV2 ) {
		super( printer3DWorld );
		this.partFeignClientV1 = Objects.requireNonNull( partFeignClientV1 );
		this.coilFeignClientV1 = Objects.requireNonNull( coilFeignClientV1 );
		this.machineFeignClientV1 = Objects.requireNonNull( machineFeignClientV1 );
		this.machineFeignClientV2 = Objects.requireNonNull( machineFeignClientV2 );
		this.jobFeignClientV1 = Objects.requireNonNull( jobFeignClientV1 );
		this.modelFeignClientV1 = Objects.requireNonNull( modelFeignClientV1 );
//		this.requestFeignClientV1 = Objects.requireNonNull( requestFeignClientV1 );
		this.requestFeignClientV2 = requestFeignClientV2;
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

	@When("the Get Coils request is processed")
	public void the_Get_Coils_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_COILS );
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
		this.processRequestByType( RequestType.NEW_REQUESTV2);
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

	private ResponseEntity processRequest( final RequestType requestType ) throws IOException {
		switch (requestType) {
			case NEW_PART:
				Assertions.assertNotNull( this.printer3DWorld.getPart() );
				final ResponseEntity<Part> newPartResponseEntity = this.partFeignClientV1
						.newPart( this.printer3DWorld.getJwtAuthorizationToken(),
								this.printer3DWorld.getPart() );
				Assertions.assertNotNull( newPartResponseEntity );
				this.printer3DWorld.setNewPartResponseEntity( newPartResponseEntity );
				return newPartResponseEntity;
			case UPDATE_PART:
				Assertions.assertNotNull( this.printer3DWorld.getPart() );
				final ResponseEntity<Part> updatePartResponseEntity = this.partFeignClientV1
						.updatePart( this.printer3DWorld.getJwtAuthorizationToken(),
								this.printer3DWorld.getPart() );
				Assertions.assertNotNull( updatePartResponseEntity );
				this.printer3DWorld.setUpdatePartResponseEntity( updatePartResponseEntity );
				return updatePartResponseEntity;
			case UPDATE_GROUP_PART:
				Assertions.assertNotNull( this.printer3DWorld.getUpdateGroupPartRequest() );
				final UpdateGroupPartRequest request = this.printer3DWorld.getUpdateGroupPartRequest()
						.setLabel( this.printer3DWorld.getSelectionLabel() );
				final ResponseEntity<CountResponse> updateGroupPartResponseEntity = this.partFeignClientV1.updateGroupPart( request );
				Assertions.assertNotNull( updateGroupPartResponseEntity );
				this.printer3DWorld.setUpdateGroupPartResponseEntity( updateGroupPartResponseEntity );
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
			case GET_COILS:
				final ResponseEntity<CoilList> coilListResponseEntity = this.coilFeignClientV1
						.getCoils( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( coilListResponseEntity );
				this.printer3DWorld.setCoilListResponseEntity( coilListResponseEntity );
				return coilListResponseEntity;
			case GET_FINISHINGS:
				final ResponseEntity<FinishingsResponse> finishingsResponseEntity = this.coilFeignClientV1
						.getFinishings( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( finishingsResponseEntity );
				this.printer3DWorld.setFinishingsResponseEntity( finishingsResponseEntity );
				return finishingsResponseEntity;
			case GET_MACHINES_V2:
				final ResponseEntity<MachineListV2> machinesv2ResponseEntity = this.machineFeignClientV2
						.getMachines( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( machinesv2ResponseEntity );
				this.printer3DWorld.setMachineListv2ResponseEntity( machinesv2ResponseEntity );
				return machinesv2ResponseEntity;
			case CANCEL_BUILD:
				final ResponseEntity<Machine> cancelBuildResponseEntity = this.machineFeignClientV1
						.cancelBuild( this.printer3DWorld.getJwtAuthorizationToken(), this.printer3DWorld.getMachineId() );
				Assertions.assertNotNull( cancelBuildResponseEntity );
				this.printer3DWorld.setStartBuildResponseEntity( cancelBuildResponseEntity );
				return cancelBuildResponseEntity;
			case COMPLETE_BUILD:
				final ResponseEntity<Machine> completeBuildResponseEntity = this.machineFeignClientV1
						.completeBuild( this.printer3DWorld.getMachineId() );
				Assertions.assertNotNull( completeBuildResponseEntity );
				this.printer3DWorld.setStartBuildResponseEntity( completeBuildResponseEntity );
				return completeBuildResponseEntity;
			case GET_JOBS:
				final ResponseEntity<List<Job>> pendingJobsResponseEntity = this.jobFeignClientV1
						.getPendingJobs();
				Assertions.assertNotNull( pendingJobsResponseEntity );
				this.printer3DWorld.setJobListResponseEntity( pendingJobsResponseEntity );
				return pendingJobsResponseEntity;
			case NEW_MODEL:
				Assertions.assertNotNull( this.printer3DWorld.getNewModelRequest() );
				final ResponseEntity<Model> newModelResponseEntity = this.modelFeignClientV1.newModel(
						this.printer3DWorld.getJwtAuthorizationToken(),
						this.printer3DWorld.getNewModelRequest() );
				Assertions.assertNotNull( newModelResponseEntity );
				this.printer3DWorld.setModelResponseEntity( newModelResponseEntity );
				return newModelResponseEntity;
			case UPDATE_MODEL:
				Assertions.assertNotNull( this.printer3DWorld.getNewModelRequest() );
				final ResponseEntity<Model> updateModelResponseEntity = this.modelFeignClientV1.updateModel(
						this.printer3DWorld.getJwtAuthorizationToken(),
						this.printer3DWorld.getNewModelRequest() );
				Assertions.assertNotNull( updateModelResponseEntity );
				this.printer3DWorld.setModelResponseEntity( updateModelResponseEntity );
				return updateModelResponseEntity;
			case NEW_REQUESTV2:
				Assertions.assertNotNull( this.printer3DWorld.getRequestV2() );
				final ResponseEntity<RequestV2> newRequestV2ResponseEntity = this.requestFeignClientV2.newRequest(
						this.printer3DWorld.getRequestV2() );
				Assertions.assertNotNull( newRequestV2ResponseEntity );
				this.printer3DWorld.setRequestResponseEntity( newRequestV2ResponseEntity );
				return newRequestV2ResponseEntity;
			case GET_REQUESTSV2:
				final ResponseEntity<List<RequestV2>> getRequestsV2ResponseEntity = this.requestFeignClientV2.getOpenRequests();
				Assertions.assertNotNull( getRequestsV2ResponseEntity );
				this.printer3DWorld.setListRequestV2ResponseEntity( getRequestsV2ResponseEntity );
				return getRequestsV2ResponseEntity;
			case CLOSE_REQUEST:
				final ResponseEntity<RequestV2> closeRequestResponseEntity = this.requestFeignClientV2.closeRequest(
						this.printer3DWorld.getRequestId()
				);
				Assertions.assertNotNull( closeRequestResponseEntity );
				this.printer3DWorld.setCloseRequestResponseEntity( closeRequestResponseEntity );
				return closeRequestResponseEntity;
			case START_BUILDV2:
				final ResponseEntity<Machine> startBuildV2ResponseEntity = this.machineFeignClientV2
						.startBuild( this.printer3DWorld.getJwtAuthorizationToken(),
								this.printer3DWorld.getMachineId(),
								this.printer3DWorld.getJobRequest() );
				Assertions.assertNotNull( startBuildV2ResponseEntity );
				this.printer3DWorld.setStartBuildResponseEntity( startBuildV2ResponseEntity );
				return startBuildV2ResponseEntity;
			case GET_MODELS:
				final ResponseEntity<ModelList> modelListResponseEntity = this.modelFeignClientV1
						.getModels();
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
			default:
				throw new NotImplementedException( "Request {} not implemented.", requestType.name() );
		}
	}

	private void processRequestByType( final RequestType requestType ) throws IOException {
		try {
			final ResponseEntity response = this.processRequest( requestType );
			this.printer3DWorld.setHttpStatus( response.getStatusCode() );
		} catch (final DimensinfinRuntimeException runtime) {
			this.printer3DWorld.setHttpStatus( runtime.getStatus() );
			this.printer3DWorld.setApplicationException( runtime );
			LogWrapperLocal.error( runtime );
		}
	}
}
