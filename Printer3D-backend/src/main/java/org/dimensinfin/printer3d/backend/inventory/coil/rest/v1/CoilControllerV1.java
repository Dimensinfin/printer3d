package org.dimensinfin.printer3d.backend.inventory.coil.rest.v1;

import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.client.domain.CoilList;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public
class CoilControllerV1 {
	private final CoilServiceV1 coilServiceV1;

	// - C O N S T R U C T O R S
	@Autowired
	public CoilControllerV1( final @NotNull CoilServiceV1 coilServiceV1 ) {
		this.coilServiceV1 = Objects.requireNonNull( coilServiceV1 );
	}

// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/inventory/coils",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CoilList> getCoils() {
		return new ResponseEntity<>( this.coilServiceV1.getRolls(), HttpStatus.OK );
	}

	@PostMapping(path = "/inventory/coils",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Coil> newCoil( final @RequestBody @Valid @NotNull Coil coil ) {
		return new ResponseEntity<>( this.coilServiceV1.newCoil( coil ), HttpStatus.CREATED );
	}
}
