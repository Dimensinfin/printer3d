package org.dimensinfin.printer3d.backend.inventory.model.rest.v1;

import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class ModelControllerV1 {
	private final ModelServiceV1 modelServiceV1;

	// - C O N S T R U C T O R S
	public ModelControllerV1( final @NotNull ModelServiceV1 modelServiceV1 ) {
		this.modelServiceV1 = Objects.requireNonNull( modelServiceV1 );
	}

	@PostMapping(path = "/inventory/models",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Model> newModel( final @RequestBody @Valid @NotNull NewModelRequest modelRequest ) {
		return new ResponseEntity<>( this.modelServiceV1.newModel( modelRequest ), HttpStatus.CREATED );
	}

}
