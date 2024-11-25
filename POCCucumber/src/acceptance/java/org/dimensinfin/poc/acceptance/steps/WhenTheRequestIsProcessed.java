package org.dimensinfin.poc.acceptance.steps;

import java.io.IOException;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.dimensinfin.poc.acceptance.support.Printer3DWorld;
import org.dimensinfin.poc.acceptance.support.RequestType;
import org.dimensinfin.poc.domain.Coil;

import io.cucumber.java.en.When;

//@Slf4j
public class WhenTheRequestIsProcessed {
	protected Printer3DWorld printer3DWorld;

	// - C O N S T R U C T O R S
	@Autowired
	public WhenTheRequestIsProcessed( final Printer3DWorld printer3DWorld ) {
		this.printer3DWorld = printer3DWorld;
	}

	@When("the Get Coils v2 request is processed")
	public void the_Get_Coils_v2_request_is_processed() throws IOException {
		this.processRequestByType( RequestType.GET_COILS_V2 );
	}

	private ResponseEntity processRequest( final RequestType requestType ) throws IOException {
		switch (requestType) {
			case GET_COILS_V2:
				final ResponseEntity<Coil> coilV2ListResponseEntity = new RestTemplate()
						.getForEntity( "http://localhost:9000/api/v2/inventory/coils", Coil.class );
				this.printer3DWorld.setCoilV2ListResponseEntity( coilV2ListResponseEntity );
				return coilV2ListResponseEntity;
			default:
				throw new NotImplementedException( "Request {} not implemented.", requestType.name() );
		}
	}

	private void processRequestByType( final RequestType requestType ) throws IOException {
		try {
			final ResponseEntity<?> response = this.processRequest( requestType );
			this.printer3DWorld.setHttpStatus( response.getStatusCode() );
		} catch (final RuntimeException runtime) {
			this.printer3DWorld.setHttpStatus( HttpStatus.BAD_GATEWAY );
			this.printer3DWorld.setApplicationException( runtime );
//			log.error( runtime.toString() );
		}
	}
}
