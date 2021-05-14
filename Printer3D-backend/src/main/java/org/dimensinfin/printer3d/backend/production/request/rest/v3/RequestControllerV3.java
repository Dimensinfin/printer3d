package org.dimensinfin.printer3d.backend.production.request.rest.v3;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

@RestController
@Validated
@RequestMapping("/api/v3")
public class RequestControllerV3 {
	protected final RequestServiceV3 requestServiceV3;

	// - C O N S T R U C T O R S
	public RequestControllerV3( final @NotNull RequestServiceV3 requestServiceV3 ) {
		this.requestServiceV3 = requestServiceV3;
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/production/requests/closed",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<CustomerRequestResponseV2>> getClosedRequestsV3() {
		return new ResponseEntity<>( this.requestServiceV3.getClosedRequestsV3(), HttpStatus.OK );
	}

	@GetMapping(path = "/production/requests/open",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<CustomerRequestResponseV2>> getOpenRequestsV3() {
		return new ResponseEntity<>( this.requestServiceV3.getOpenRequestsV3(), HttpStatus.OK );
	}
}
