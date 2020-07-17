package org.dimensinfin.printer3d.backend.inventory.part.rest.v1;

import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class PartControllerV1 {
	private final PartServiceV1 partServiceV1;

	// - C O N S T R U C T O R S
	@Autowired
	public PartControllerV1( @NotNull final PartServiceV1 partServiceV1 ) {
		this.partServiceV1 = partServiceV1;
	}

	@GetMapping(path = "/inventory/parts",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<PartList> getParts( final @RequestParam(name = "activesOnly") Optional<Boolean> activesOnly ) {
		final boolean activeState = activesOnly.isPresent() && activesOnly.get();
		return new ResponseEntity<>( this.partServiceV1.getParts( activeState ), HttpStatus.OK );
	}

	@PostMapping(path = "/inventory/parts",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Part> newPart( final @RequestBody @Valid @NotNull Part part ) {
		return new ResponseEntity<>( this.partServiceV1.newPart( part ), HttpStatus.CREATED );
	}

	@PatchMapping(path = "/inventory/parts/group",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CounterResponse> updateGroupPart( final @RequestBody @Valid @NotNull UpdateGroupPartRequest updateData ) {
		return new ResponseEntity<>( this.partServiceV1.updateGroupPart( updateData ), HttpStatus.OK );
	}

	@PatchMapping(path = "/inventory/parts",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Part> updatePart( final @RequestBody @Valid @NotNull Part part ) {
		return new ResponseEntity<>( this.partServiceV1.updatePart( part ), HttpStatus.OK );
	}
}
