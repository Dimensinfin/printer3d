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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

@RestController
//@CrossOrigin
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
		return new ResponseEntity<>( this.coilServiceV1.getCoils(), HttpStatus.OK );
	}

	@PostMapping(path = "/inventory/coils",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Coil> newCoil( final @RequestBody @Valid @NotNull Coil coil ) {
		return new ResponseEntity<>( this.coilServiceV1.newCoil( coil ), HttpStatus.CREATED );
	}

	/**
	 * Updates a Coil. All only field allowed to be changed is the weight.
	 * @param updateCoilRequest the new data to persist for this Coil
	 * @return the coil data as found on the repository.
	 * @since 0.10.0
	 */
	@PatchMapping(path = "/inventory/coils",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Coil> updateCoil( final @RequestBody @Valid @NotNull UpdateCoilRequest updateCoilRequest ) {
		return new ResponseEntity<>( this.coilServiceV1.updateCoil( updateCoilRequest ), HttpStatus.OK );
	}
}
