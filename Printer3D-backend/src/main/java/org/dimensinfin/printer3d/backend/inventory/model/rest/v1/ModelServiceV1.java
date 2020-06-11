package org.dimensinfin.printer3d.backend.inventory.model.rest.v1;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.model.converter.ModelEntityToModelConverter;
import org.dimensinfin.printer3d.backend.inventory.model.converter.NewModelRequestToModelEntityConverter;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateModelCompositionRequest;

@Service
public class ModelServiceV1 {
	private final ModelRepository modelRepository;
	private final PartRepository partRepository;

	// - C O N S T R U C T O R S
	public ModelServiceV1( final @NotNull ModelRepository modelRepository,
	                       final @NotNull PartRepository partRepository ) {
		this.modelRepository = Objects.requireNonNull( modelRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
	}

	public Model addModelPart( final UpdateModelCompositionRequest modelCompositionRequest ) {
		LogWrapper.enter();
		try {
			// Search for the model at the repository.
			final Optional<ModelEntity> modelOptional = this.modelRepository.findById( modelCompositionRequest.getModelId() );
			if (modelOptional.isEmpty())
				throw new InvalidRequestException( ErrorInfo.MODEL_NOT_FOUND.getErrorMessage( modelCompositionRequest.getModelId() ) );
			final ModelEntity modelEntity = this.modelRepository.save( modelOptional.get().addPart( modelCompositionRequest.getPartId() ) );
			return this.constructModel( modelEntity );
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * After the <code>ModelEntity</code> creation the service will compose the Model to be returned to the frontend. On this conversion the
	 * <code>partList</code> is scanned to read from the backend repository the Part instances that should be attached to the model.
	 *
	 * @param newModelRequest the model creation fields that are collected at the frontend UI.
	 * @return an instance of <code>Model</code> with the Parts expanded con contain all the Part fields generated from the persisted partUI
	 */
	public Model newModel( final @NotNull NewModelRequest newModelRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Model by id. If found reject the request because this should be a new creation.
			final Optional<ModelEntity> target = this.modelRepository.findById( newModelRequest.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( ErrorInfo.MODEL_ALREADY_EXISTS, newModelRequest.getId().toString() );
			// Save the entity to the repository.
			final ModelEntity modelEntity = this.modelRepository.save(
					new NewModelRequestToModelEntityConverter().convert( newModelRequest )
			);
			return this.constructModel( modelEntity );
		} finally {
			LogWrapper.exit();
		}
	}

	public Model removeModelPart( final UpdateModelCompositionRequest modelCompositionRequest ) {
		LogWrapper.enter();
		try {
			// Search for the model at the repository.
			final Optional<ModelEntity> modelOptional = this.modelRepository.findById( modelCompositionRequest.getModelId() );
			if (modelOptional.isEmpty())
				throw new InvalidRequestException( ErrorInfo.MODEL_NOT_FOUND.getErrorMessage( modelCompositionRequest.getModelId() ) );
			final ModelEntity modelEntity = this.modelRepository.save( modelOptional.get().removePart( modelCompositionRequest.getPartId() ) );
			return this.constructModel( modelEntity );
		} finally {
			LogWrapper.exit();
		}
	}

	private Model constructModel( final ModelEntity entity ) {
		// Add the parts to the model to bw responded.
		final Model model = new ModelEntityToModelConverter().convert( entity );
		for (final UUID partId : model.getPartIdentifierList())
			this.partRepository.findById( partId ).ifPresent( model::addPart );
		return model;
	}
}
