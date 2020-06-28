package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	@GetMapping(path = "/production/requests",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<RequestV2>> getOpenRequests() {
		return new ResponseEntity<>( this.requestServiceV2.getOpenRequests(), HttpStatus.OK );
	}
}
