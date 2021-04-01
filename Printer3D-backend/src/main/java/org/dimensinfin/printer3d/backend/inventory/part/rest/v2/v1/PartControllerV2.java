package org.dimensinfin.printer3d.backend.inventory.part.rest.v2.v1;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.6.0
 */
@RestController
@Validated
@RequestMapping("/api/v2")
public class PartControllerV2 {
	private final PartServiceV2 partServiceV2;

	// - C O N S T R U C T O R S
	@Autowired
	public PartControllerV2( @NotNull final PartServiceV2 partServiceV2 ) {
		this.partServiceV2 = partServiceV2;
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/inventory/parts",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<Part>> getParts() {
		return new ResponseEntity<>( this.partServiceV2.getParts(), HttpStatus.OK );
	}
}
