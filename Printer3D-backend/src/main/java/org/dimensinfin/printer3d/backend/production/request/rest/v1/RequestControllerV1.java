package org.dimensinfin.printer3d.backend.production.request.rest.v1;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class RequestControllerV1 {
	protected final RequestServiceV1 requestServiceV1;

	// - C O N S T R U C T O R S
	public RequestControllerV1( final @NotNull RequestServiceV1 requestServiceV1 ) {
		this.requestServiceV1 = Objects.requireNonNull( requestServiceV1 );
	}

	// - G E T T E R S   &   S E T T E R S
//	@GetMapping(path = "/production/requests",
//			consumes = "application/json",
//			produces = "application/json")
//	public ResponseEntity<RequestList> getOpenRequests() {
//		return new ResponseEntity<>( this.requestServiceV1.getOpenRequests(), HttpStatus.OK );
//	}
//
//	@PutMapping(path = "/production/requests/{requestId}/close",
//			consumes = "application/json",
//			produces = "application/json")
//	public ResponseEntity<Request> closeRequest( final @PathVariable @NotNull UUID requestId ) {
//		return new ResponseEntity<>( this.requestServiceV1.closeRequest( requestId ), HttpStatus.OK );
//	}
//
//	@PostMapping(path = "/production/requests",
//			consumes = "application/json",
//			produces = "application/json")
//	public ResponseEntity<Request> newRequest( final @RequestBody @Valid @NotNull Request request ) {
//		return new ResponseEntity<>( this.requestServiceV1.newRequest( request ), HttpStatus.CREATED );
//	}
}
