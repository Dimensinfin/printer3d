package org.dimensinfin.printer3d.backend.production.request.rest.v1;

import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class RequestControllerV1 {
	protected final RequestServiceV1 requestServiceV1;

	public RequestControllerV1( final @NotNull RequestServiceV1 requestServiceV1 ) {
		this.requestServiceV1 = Objects.requireNonNull(requestServiceV1);
	}

	@PostMapping(path = "/production/requests",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Request> newRequest( final @RequestBody @Valid @NotNull Request request ) {
		return new ResponseEntity<>( this.requestServiceV1.newRequest( request ), HttpStatus.CREATED );
	}
	@GetMapping(path = "/production/requests",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<RequestList> getOpenRequests( ) {
		return new ResponseEntity<>( this.requestServiceV1.getOpenRequests(  ), HttpStatus.OK );
	}

}
