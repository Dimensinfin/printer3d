package org.dimensinfin.printer3d.backend.inventory.part.rest.support;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.core.dto.CounterResponse;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.2.0
 */
//@Profile({ "local", "acceptance", "test" })
@RestController
//@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class PartControllerSupport {
	private PartServiceSupport partServiceSupport;

	// - C O N S T R U C T O R
	@Autowired
	public PartControllerSupport( @NotNull final PartServiceSupport partServiceSupport ) {
		this.partServiceSupport = Objects.requireNonNull( partServiceSupport );
	}
	@GetMapping(path = "/inventory/parts/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CounterResponse> deleteAllParts() {
		return new ResponseEntity<>( this.partServiceSupport.deleteAllParts(), HttpStatus.OK );
	}
}
