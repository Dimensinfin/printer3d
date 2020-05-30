package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.RequestType;
import org.dimensinfin.printer3d.backend.support.part.rest.v1.PartFeignClientV1;
import org.dimensinfin.printer3d.backend.support.roll.rest.v1.RollFeignClientV1;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;
import org.dimensinfin.printer3d.client.domain.PartList;
import org.dimensinfin.printer3d.client.domain.RollList;

import io.cucumber.java.en.When;

public class WhenTheRequestIsProcessed extends StepSupport {
	private final PartFeignClientV1 partFeignClientV1;
	private final RollFeignClientV1 rollFeignClientV1;

	// - C O N S T R U C T O R S
	public WhenTheRequestIsProcessed( final @NotNull Printer3DWorld printer3DWorld,
	                                  final @NotNull PartFeignClientV1 partFeignClientV1,
	                                  final @NotNull RollFeignClientV1 rollFeignClientV1 ) {
		super( printer3DWorld );
		this.partFeignClientV1 = Objects.requireNonNull( partFeignClientV1 );
		this.rollFeignClientV1 = Objects.requireNonNull( rollFeignClientV1 );
	}

	@When("the Get Finishings request is processed")
	public void the_Get_Finishings_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_FINISHINGS );
	}

	@When("the Get Parts request is processed")
	public void the_Get_Parts_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_PARTS );
	}

	@When("the Get Rolls request is processed")
	public void the_Get_Rolls_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_ROLLS );
	}

	@When("the New Part request is processed")
	public void the_New_Part_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_PART );
	}

	@When("the New Roll request is processed")
	public void the_New_Roll_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_ROLL );
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
			case NEW_ROLL:
				Assertions.assertNotNull( this.printer3DWorld.getCoil() );
				final ResponseEntity<Coil> newRollResponseEntity = this.rollFeignClientV1
						.newRoll( this.printer3DWorld.getJwtAuthorizationToken(),
								this.printer3DWorld.getCoil() );
				Assertions.assertNotNull( newRollResponseEntity );
				this.printer3DWorld.setNewRollResponseEntity( newRollResponseEntity );
				return newRollResponseEntity;
			case GET_ROLLS:
				final ResponseEntity<RollList> rollListResponseEntity = this.rollFeignClientV1
						.getRolls( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( rollListResponseEntity );
				this.printer3DWorld.setRollListResponseEntity( rollListResponseEntity );
				return rollListResponseEntity;
			case GET_FINISHINGS:
				final ResponseEntity<FinishingsResponse> finishingsResponseEntity = this.rollFeignClientV1
						.getFinishings( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( finishingsResponseEntity );
				this.printer3DWorld.setFinishingsResponseEntity( finishingsResponseEntity );
				return finishingsResponseEntity;
			default:
				throw new NotImplementedException( "Request {} not implemented.", requestType.name() );
		}
	}

	private void processRequestByType( final RequestType requestType ) throws IOException {
		try {
			final ResponseEntity innoDentalResponse = this.processRequest( requestType );
			this.printer3DWorld.setHttpStatus( innoDentalResponse.getStatusCode() );
		} catch (final RuntimeException runtime) {
		}
	}
}
