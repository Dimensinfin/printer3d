package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.RequestType;
import org.dimensinfin.printer3d.backend.support.part.rest.v1.PartFeignClientV1;
import org.dimensinfin.printer3d.client.part.domain.PartList;

import io.cucumber.java.en.When;

public class WhenTheRequestIsProcessed extends StepSupport {
	private final PartFeignClientV1 partFeignClientV1;

	// - C O N S T R U C T O R S
	public WhenTheRequestIsProcessed( @NotNull final Printer3DWorld printer3DWorld,
	                                  @NotNull final PartFeignClientV1 partFeignClientV1 ) {
		super( printer3DWorld );
		this.partFeignClientV1 = Objects.requireNonNull( partFeignClientV1 );
	}

	@When("the New Part request is processed")
	public void the_New_Part_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.NEW_PART );
	}
	@When("the Get Parts request is processed")
	public void the_Get_Parts_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.PART_LIST );
	}


	//	/**
	//	 * If there is an exception I have to check if this is the application format or the springboot format.
	//	 */
	//	//	@When("the {string} request is processed")
	//	public void the_request_is_processed( final String apiRequestName ) throws IOException {
	//		try {
	//			final RequestType requestType = RequestType.from( apiRequestName );
	//			final ResponseEntity innoDentalResponse = this.processRequest( requestType );
	//			this.innoDentalWorld.setHttpStatus( innoDentalResponse.getStatusCode() );
	//		} catch (final RuntimeException runtime) {
	//			if (runtime instanceof InnoDentalRuntimeException) {
	//				this.innoDentalWorld.setHttpStatus( ((InnoDentalRuntimeException) runtime).getHttpStatus() );
	//				this.innoDentalWorld.setErrorInfo( ((InnoDentalRuntimeException) runtime).getErrorInfo() );
	//				this.innoDentalWorld.setErrorMessage( runtime.getMessage() );
	//			} else {
	//				final InvalidRequestException exception = new InvalidRequestException( runtime );
	//				this.innoDentalWorld.setHttpStatus( exception.getHttpStatus() );
	//				this.innoDentalWorld.setErrorInfo( exception.getErrorInfo() );
	//				this.innoDentalWorld.setErrorMessage( exception.getMessage() );
	//			}
	//		}
	//	}

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
			case PART_LIST:
				final ResponseEntity<PartList> partListResponseEntity = this.partFeignClientV1
						.partList( this.printer3DWorld.getJwtAuthorizationToken() );
				Assertions.assertNotNull( partListResponseEntity );
				this.printer3DWorld.setPartListResponseEntity( partListResponseEntity );
				return partListResponseEntity;
			default:
				throw new NotImplementedException( "Request {} not implemented.", requestType.name() );
		}
	}

	private void processRequestByType( final RequestType requestType ) throws IOException {
		try {
			final ResponseEntity innoDentalResponse = this.processRequest( requestType );
			this.printer3DWorld.setHttpStatus( innoDentalResponse.getStatusCode() );
		} catch (final RuntimeException runtime) {
			//			if (runtime instanceof RuntimeException) {
			//				this.printer3DWorld.setHttpStatus( ((InnoDentalRuntimeException) runtime).getHttpStatus() );
			//				this.printer3DWorld.setErrorInfo( ((InnoDentalRuntimeException) runtime).getErrorInfo() );
			//				this.printer3DWorld.setErrorMessage( runtime.getMessage() );
			//			} else {
			//				final InvalidRequestException exception = new InvalidRequestException( runtime );
			//				this.printer3DWorld.setHttpStatus( exception.getHttpStatus() );
			//				this.printer3DWorld.setErrorInfo( exception.getErrorInfo() );
			//				this.printer3DWorld.setErrorMessage( exception.getMessage() );
			//			}
		}
	}
}
