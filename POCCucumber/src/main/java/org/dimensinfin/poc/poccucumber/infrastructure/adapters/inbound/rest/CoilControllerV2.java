package org.dimensinfin.poc.poccucumber.infrastructure.adapters.inbound.rest;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.poc.poccucumber.application.usecases.CoilServiceV2;
import org.dimensinfin.poc.poccucumber.domain.Coil;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.14.0
 */
@RestController
@Validated
@RequestMapping("/api/v2")
public class CoilControllerV2 {
	private final CoilServiceV2 coilServiceV2;

	// - C O N S T R U C T O R S
	public CoilControllerV2( @NotNull final CoilServiceV2 coilServiceV2 ) {
		this.coilServiceV2 = coilServiceV2;
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/inventory/coils",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<Coil>> getCoils() {
		return new ResponseEntity<>( this.coilServiceV2.getCoils(), HttpStatus.OK );
	}

	@PostMapping(
			value = "/inventory/coils",
			produces = { "application/json" },
			consumes = { "application/json" }
	)
	public ResponseEntity<Coil> createPart( @Valid @RequestBody final Coil coilDto ) {
		return ResponseEntity.ok( ConverterFactory.toCoil( this.coilServiceV2.createCoil( coilDto ) ) );
	}
}