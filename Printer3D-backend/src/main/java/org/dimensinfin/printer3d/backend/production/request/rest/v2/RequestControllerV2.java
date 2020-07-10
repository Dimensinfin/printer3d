package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v2")
public class RequestControllerV2 {
	protected final RequestServiceV2 requestServiceV2;

	// - C O N S T R U C T O R S
	public RequestControllerV2( final @NotNull RequestServiceV2 requestServiceV2 ) {
		this.requestServiceV2 = requestServiceV2;
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/production/requests",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<RequestV2>> getOpenRequests() {
		return new ResponseEntity<>( this.requestServiceV2.getOpenRequests(), HttpStatus.OK );
	}

	@PutMapping(path = "/production/requests/{requestId}/close",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<RequestV2> closeRequest( final @PathVariable @NotNull UUID requestId ) {
		return new ResponseEntity<>( this.requestServiceV2.closeRequest( requestId ), HttpStatus.OK );
	}

	@PostMapping(path = "/production/requests",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<RequestV2> newRequest( final @RequestBody @Valid @NotNull RequestV2 request ) {
		return new ResponseEntity<>( this.requestServiceV2.newRequest( request ), HttpStatus.CREATED );
	}
}
