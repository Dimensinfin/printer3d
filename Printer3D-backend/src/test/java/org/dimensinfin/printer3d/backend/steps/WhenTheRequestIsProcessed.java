package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.Machine;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.RequestType;
import org.dimensinfin.printer3d.backend.support.inventory.machine.rest.MachineFeignClientV1;
import org.dimensinfin.printer3d.backend.support.inventory.part.rest.PartFeignClientV1;
import org.dimensinfin.printer3d.backend.support.roll.rest.v1.CoilFeignClientV1;
import org.dimensinfin.printer3d.client.domain.CoilList;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;
import org.dimensinfin.printer3d.client.domain.MachineList;
import org.dimensinfin.printer3d.client.domain.PartList;
import org.dimensinfin.printer3d.client.domain.StartBuildRequest;

import io.cucumber.java.en.When;

public class WhenTheRequestIsProcessed extends StepSupport {
	private final PartFeignClientV1 partFeignClientV1;
	private final CoilFeignClientV1 coilFeignClientV1;
	private final MachineFeignClientV1 machineFeignClientV1;

	// - C O N S T R U C T O R S
	public WhenTheRequestIsProcessed( final @NotNull Printer3DWorld printer3DWorld,
	                                  final @NotNull PartFeignClientV1 partFeignClientV1,
	                                  final @NotNull CoilFeignClientV1 coilFeignClientV1,
	                                  final @NotNull MachineFeignClientV1 machineFeignClientV1 ) {
		super( printer3DWorld );
		this.partFeignClientV1 = Objects.requireNonNull( partFeignClientV1 );
		this.coilFeignClientV1 = Objects.requireNonNull( coilFeignClientV1 );
		this.machineFeignClientV1 = Objects.requireNonNull( machineFeignClientV1 );
	}

	@When("the Cancel Build for Machine {string} request is processed")
	public void the_Cancel_Build_for_Machine_request_is_processed( final String machineId ) throws IOException {
		this.printer3DWorld.setMachineId( UUID.fromString( machineId ) );
		this.processRequestByType( RequestType.CANCEL_BUILD );
	}

	@When("the Get Coils request is processed")
	public void the_Get_Coils_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_COILS );
	}

	@When("the Get Finishings request is processed")
	public void the_Get_Finishings_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_FINISHINGS );
	}

	@When("the Get Machines request is processed")
	public void the_Get_Machines_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_MACHINES );
	}

	@When("the Get Parts request is processed")
	public void the_Get_Parts_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_PARTS );
	}

	@When("the New Coil request is processed")
	public void the_New_Coil_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_COIL );
	}

	@When("the New Part request is processed")
	public void the_New_Part_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_PART );
	}

	@When("the Start Build for Part {string} for Machine {string} request is processed")
	public void the_Start_Build_for_Part_for_Machine_request_is_processed( final String partId, final String machineId ) throws IOException {
		this.printer3DWorld.setPartId( UUID.fromString( partId ) );
		this.printer3DWorld.setMachineId( UUID.fromString( machineId ) );
		this.processRequestByType( RequestType.START_BUILD );
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
			case GET_MACHINES:
				final ResponseEntity<MachineList> machinesResponseEntity = this.machineFeignClientV1
						.getMachines( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( machinesResponseEntity );
				this.printer3DWorld.setMachineListResponseEntity( machinesResponseEntity );
				return machinesResponseEntity;
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
			default:
				throw new NotImplementedException( "Request {} not implemented.", requestType.name() );
		}
	}

	private void processRequestByType( final RequestType requestType ) throws IOException {
		try {
			final ResponseEntity response = this.processRequest( requestType );
			this.printer3DWorld.setHttpStatus( response.getStatusCode() );
		} catch (final RuntimeException runtime) {
			LogWrapper.error( runtime );
		}
	}
}
