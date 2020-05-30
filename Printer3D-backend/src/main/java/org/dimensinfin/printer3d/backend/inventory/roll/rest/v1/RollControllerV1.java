package org.dimensinfin.printer3d.backend.inventory.roll.rest.v1;

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

import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Coil;
import org.dimensinfin.printer3d.client.domain.RollList;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public
class RollControllerV1 {
	private final RollServiceV1 rollServiceV1;

	// - C O N S T R U C T O R S
	@Autowired
	public RollControllerV1( final @NotNull RollServiceV1 rollServiceV1 ) {
		this.rollServiceV1 = Objects.requireNonNull( rollServiceV1 );
	}

	@PostMapping(path = "/inventory/rolls",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Coil> newRoll( final @RequestBody @Valid @NotNull Coil coil ) {
		return new ResponseEntity<>( this.rollServiceV1.newRoll( coil ), HttpStatus.CREATED );
	}

	@GetMapping(path = "/inventory/rolls",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<RollList> getRolls() {
		return new ResponseEntity<>( this.rollServiceV1.getRolls(), HttpStatus.OK );
	}
}
