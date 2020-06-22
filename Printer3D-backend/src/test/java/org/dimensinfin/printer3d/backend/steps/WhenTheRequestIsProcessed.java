package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

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
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientV1;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.StartBuildRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;

import io.cucumber.java.en.When;

public class WhenTheRequestIsProcessed extends StepSupport {
	private final PartFeignClientV1 partFeignClientV1;
	private final CoilFeignClientV1 coilFeignClientV1;
	private final MachineFeignClientV1 machineFeignClientV1;
	private final MachineFeignClientV2 machineFeignClientV2;
	private final JobFeignClientV1 jobFeignClientV1;
	private final ModelFeignClientV1 modelFeignClientV1;
	private final RequestFeignClientV1 requestFeignClientV1;

	// - C O N S T R U C T O R S
	public WhenTheRequestIsProcessed( final @NotNull Printer3DWorld printer3DWorld,
	                                  final @NotNull PartFeignClientV1 partFeignClientV1,
	                                  final @NotNull CoilFeignClientV1 coilFeignClientV1,
	                                  final @NotNull MachineFeignClientV1 machineFeignClientV1,
	                                  final @NotNull MachineFeignClientV2 machineFeignClientV2,
	                                  final @NotNull JobFeignClientV1 jobFeignClientV1,
	                                  final @NotNull ModelFeignClientV1 modelFeignClientV1,
	                                  final @NotNull RequestFeignClientV1 requestFeignClientV1 ) {
		super( printer3DWorld );
		this.partFeignClientV1 = Objects.requireNonNull( partFeignClientV1 );
		this.coilFeignClientV1 = Objects.requireNonNull( coilFeignClientV1 );
		this.machineFeignClientV1 = Objects.requireNonNull( machineFeignClientV1 );
		this.machineFeignClientV2 = Objects.requireNonNull( machineFeignClientV2 );
		this.jobFeignClientV1 = Objects.requireNonNull( jobFeignClientV1 );
		this.modelFeignClientV1 = Objects.requireNonNull( modelFeignClientV1 );
		this.requestFeignClientV1 = Objects.requireNonNull( requestFeignClientV1 );
	}

	@When("the Add Model Part request with model {string} and part {string} is processed")
	public void the_Add_Model_Part_request_with_model_and_part_is_processed( final String modelId, final String partId ) throws IOException {
		this.printer3DWorld.setModelId( UUID.fromString( modelId ) );
		this.printer3DWorld.setPartId( UUID.fromString( partId ) );
		this.processRequestByType( RequestType.ADD_MODEL_PART );
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

	@When("the Get Machines request is processed")
	public void the_Get_Machines_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_MACHINES_V1 );
	}

	@When("the Get Parts request is processed")
	public void the_Get_Parts_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_PARTS );
	}

	@When("the Get Requests request is processed")
	public void the_Get_Requests_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_REQUESTS );
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

	@When("the New Request request is processed")
	public void the_New_Request_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_REQUEST );
	}

	@When("the Remove Model Part request with model {string} and part {string} is processed")
	public void the_Remove_Model_Part_request_with_model_and_part_is_processed( final String modelId, final String partId ) throws IOException {
		this.printer3DWorld.setModelId( UUID.fromString( modelId ) );
		this.printer3DWorld.setPartId( UUID.fromString( partId ) );
		this.processRequestByType( RequestType.REMOVE_MODEL_PART );
	}

	@When("the Start Build V2 for for Machine {string} request is processed")
	public void the_Start_Build_V2_for_for_Machine_request_is_processed( final String machineId ) throws IOException {
		this.printer3DWorld.setMachineId( UUID.fromString( machineId ) );
		Assertions.assertNotNull( this.printer3DWorld.getJobRequest() );
		this.processRequestByType( RequestType.START_BUILDV2 );
	}

	@When("the Start Build for Part {string} for Machine {string} request is processed")
	public void the_Start_Build_for_Part_for_Machine_request_is_processed( final String partId, final String machineId ) throws IOException {
		this.printer3DWorld.setPartId( UUID.fromString( partId ) );
		this.printer3DWorld.setMachineId( UUID.fromString( machineId ) );
		this.processRequestByType( RequestType.START_BUILD );
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
				this.printer3DWorld.setNewCoilResponseEntity( newCoilResponseEntity );
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
			case GET_MACHINES_V1:
				final ResponseEntity<MachineList> machinesResponseEntity = this.machineFeignClientV1
						.getMachines( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( machinesResponseEntity );
				this.printer3DWorld.setMachineListResponseEntity( machinesResponseEntity );
				return machinesResponseEntity;
			case GET_MACHINES_V2:
				final ResponseEntity<MachineListV2> machinesv2ResponseEntity = this.machineFeignClientV2
						.getMachines( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( machinesv2ResponseEntity );
				this.printer3DWorld.setMachineListv2ResponseEntity( machinesv2ResponseEntity );
				return machinesv2ResponseEntity;
			case START_BUILD:
				final ResponseEntity<Machine> startBuildResponseEntity = this.machineFeignClientV1
						.startBuild( this.printer3DWorld.getJwtAuthorizationToken(),
								new StartBuildRequest.Builder()
										.withMachineId( this.printer3DWorld.getMachineId() )
										.withPartId( this.printer3DWorld.getPartId() )
										.build() );
				Assertions.assertNotNull( startBuildResponseEntity );
				this.printer3DWorld.setStartBuildResponseEntity( startBuildResponseEntity );
				return startBuildResponseEntity;
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
			case ADD_MODEL_PART:
				Assertions.assertNotNull( this.printer3DWorld.getModelId() );
				Assertions.assertNotNull( this.printer3DWorld.getPartId() );
				final ResponseEntity<Model> addModelPartResponseEntity = this.modelFeignClientV1.addModelPart(
						this.printer3DWorld.getJwtAuthorizationToken(),
						this.printer3DWorld.getModelId(),
						this.printer3DWorld.getPartId() );
				Assertions.assertNotNull( addModelPartResponseEntity );
				this.printer3DWorld.setModelResponseEntity( addModelPartResponseEntity );
				return addModelPartResponseEntity;
			case REMOVE_MODEL_PART:
				Assertions.assertNotNull( this.printer3DWorld.getModelId() );
				Assertions.assertNotNull( this.printer3DWorld.getPartId() );
				final ResponseEntity<Model> removeModelPartResponseEntity = this.modelFeignClientV1.removeModelPart(
						this.printer3DWorld.getJwtAuthorizationToken(),
						this.printer3DWorld.getModelId(),
						this.printer3DWorld.getPartId() );
				Assertions.assertNotNull( removeModelPartResponseEntity );
				this.printer3DWorld.setModelResponseEntity( removeModelPartResponseEntity );
				return removeModelPartResponseEntity;
			case NEW_REQUEST:
				Assertions.assertNotNull( this.printer3DWorld.getNewRequest() );
				final ResponseEntity<Request> newRequestResponseEntity = this.requestFeignClientV1.newRequest(
						this.printer3DWorld.getNewRequest() );
				Assertions.assertNotNull( newRequestResponseEntity );
				this.printer3DWorld.setRequestResponseEntity( newRequestResponseEntity );
				return newRequestResponseEntity;
			case GET_REQUESTS:
				final ResponseEntity<RequestList> getRequestsResponseEntity = this.requestFeignClientV1.getOpenRequests();
				Assertions.assertNotNull( getRequestsResponseEntity );
				this.printer3DWorld.setRequestListResponseEntity( getRequestsResponseEntity );
				return getRequestsResponseEntity;
			case CLOSE_REQUEST:
				final ResponseEntity<Request> closeRequestResponseEntity = this.requestFeignClientV1.closeRequest(
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
			default:
				throw new NotImplementedException( "Request {} not implemented.", requestType.name() );
		}
	}

	private void processRequestByType( final RequestType requestType ) throws IOException {
		try {
			final ResponseEntity response = this.processRequest( requestType );
			this.printer3DWorld.setHttpStatus( response.getStatusCode() );
		} catch (final RuntimeException runtime) {
			LogWrapperLocal.error( runtime );
		}
	}
}
