package org.dimensinfin.printer3d.backend.inventory.model.rest.v1;

import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateModelCompositionRequest;

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

	// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/inventory/models",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<ModelList> getModels() {
		return new ResponseEntity<>( this.modelServiceV1.getModels(), HttpStatus.OK );
	}

	/**
	 * @deprecated This endpoint has been superseded by a new version.
	 */
	@PutMapping(path = "/inventory/models/{modelId}/addPart/{partId}",
			consumes = "application/json",
			produces = "application/json")
	@Deprecated
	public ResponseEntity<Model> addModelPart( final @PathVariable @NotNull UUID modelId,
	                                           final @PathVariable @NotNull UUID partId ) {
		return new ResponseEntity<>( this.modelServiceV1.addModelPart( new UpdateModelCompositionRequest.Builder()
				.withModelId( modelId )
				.withPartId( partId )
				.build() ), HttpStatus.OK );
	}

	@PostMapping(path = "/inventory/models",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Model> newModel( final @RequestBody @Valid @NotNull NewModelRequest modelRequest ) {
		return new ResponseEntity<>( this.modelServiceV1.newModel( modelRequest ), HttpStatus.CREATED );
	}

	/**
	 * @deprecated This endpoint has been superseded by a new version.
	 */
	@PutMapping(path = "/inventory/models/{modelId}/removePart/{partId}",
			consumes = "application/json",
			produces = "application/json")
	@Deprecated
	public ResponseEntity<Model> removeModelPart( final @PathVariable @NotNull UUID modelId,
	                                              final @PathVariable @NotNull UUID partId ) {
		return new ResponseEntity<>( this.modelServiceV1.removeModelPart( new UpdateModelCompositionRequest.Builder()
				.withModelId( modelId )
				.withPartId( partId )
				.build() ), HttpStatus.OK );
	}

	/**
	 * Updates a Model. All the fields are editable but the id. So the endpoint just replaces the contents.
	 *
	 * @param modelRequest new data to be the Model contents
	 * @return the new Model contents
	 * @since 0.8.0
	 */
	@PatchMapping(path = "/inventory/models",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Model> updateModel( final @RequestBody @Valid @NotNull NewModelRequest modelRequest ) {
		return new ResponseEntity<>( this.modelServiceV1.updateModel( modelRequest ), HttpStatus.OK );
	}

}
