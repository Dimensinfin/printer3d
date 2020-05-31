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
class RollControllerV1 {
	private final CoilServiceV1 coilviceV1;

	// - C O N S T R U C T O R S
	@Autowired
	public RollControllerV1( final @NotNull CoilServiceV1 coilviceV1 ) {
		this.coilviceV1 = Objects.requireNonNull( coilviceV1 );
	}

// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/inventory/rolls",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CoilList> getRolls() {
		return new ResponseEntity<>( this.coilviceV1.getRolls(), HttpStatus.OK );
	}

	@PostMapping(path = "/inventory/rolls",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Coil> newRoll( final @RequestBody @Valid @NotNull Coil coil ) {
		return new ResponseEntity<>( this.coilviceV1.newCoil( coil ), HttpStatus.CREATED );
	}
}
