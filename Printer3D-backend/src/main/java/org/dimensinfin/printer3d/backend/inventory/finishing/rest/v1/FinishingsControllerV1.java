package org.dimensinfin.printer3d.backend.inventory.finishing.rest.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.domain.FinishingsResponse;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class FinishingsControllerV1 {
	private final FinishingsServiceV1 finishingsServiceV1;

	public FinishingsControllerV1( final FinishingsServiceV1 finishingsServiceV1 ) {
		this.finishingsServiceV1 = finishingsServiceV1;
	}

	@GetMapping(path = "/inventory/finishings",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<FinishingsResponse> getFinishings() {
		return new ResponseEntity<>( this.finishingsServiceV1.getFinishings(), HttpStatus.OK );
	}
}
