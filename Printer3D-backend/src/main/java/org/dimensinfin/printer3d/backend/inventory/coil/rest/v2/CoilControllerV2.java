package org.dimensinfin.printer3d.backend.inventory.coil.rest.v2;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.core.utility.DimObjects;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

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
	public CoilControllerV2( final CoilServiceV2 coilServiceV2 ) {
		this.coilServiceV2 = DimObjects.requireNonNull( coilServiceV2,
				MessageFormat.format( "Injectable component {0} not available.", "CoilServiceV2" )
		);
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/inventory/coils",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<Coil>> getCoils() {
		return new ResponseEntity<>( this.coilServiceV2.getCoils(), HttpStatus.OK );
	}
}