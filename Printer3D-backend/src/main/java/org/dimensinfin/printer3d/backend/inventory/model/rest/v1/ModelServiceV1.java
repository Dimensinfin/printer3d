package org.dimensinfin.printer3d.backend.inventory.model.rest.v1;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.model.converter.ModelEntityToModelConverter;
import org.dimensinfin.printer3d.backend.inventory.model.converter.ModelRequestToModelEntityConverter;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelUpdater;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.rest.v2.PartServiceV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

@Service
public class ModelServiceV1 {
	protected final ModelRepository modelRepository;
	protected final PartRepository partRepository;
	private final PartServiceV2 partServiceV2;

	// - C O N S T R U C T O R S
	public ModelServiceV1( @NotNull final ModelRepository modelRepository,
	                       @NotNull final PartRepository partRepository,
	                       @NotNull final PartServiceV2 partServiceV2 ) {
		this.modelRepository = Objects.requireNonNull( modelRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
		this.partServiceV2 = partServiceV2;
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * The complete list of models stored on the backend repository.
	 *
	 * @return the complete list of models.
	 */
	public List<Model> getModels() {
		final List<Part> partList = this.partServiceV2.getPartsV2();
		return this.modelRepository.findAll()
				.stream()
				.filter( model -> this.noMissingParts( model, partList ) )
				.map( modelEntity -> new ModelEntityToModelConverter().convert( modelEntity ) )
				.collect( Collectors.toList() );
	}

	/**
	 * After the <code>ModelEntity</code> creation the service will compose the Model to be returned to the frontend. On this conversion the
	 * <code>partList</code> is scanned to read from the backend repository the Part instances that should be attached to the model.
	 *
	 * @param modelRequest the model creation fields that are collected at the frontend UI.
	 * @return an instance of <code>Model</code> with the Parts expanded con contain all the Part fields generated from the persisted partUI
	 */
	public Model newModel( final @NotNull ModelRequest modelRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Model by id. If found reject the request because this should be a new creation.
			final Optional<ModelEntity> target = this.modelRepository.findById( modelRequest.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorMODELALREADYEXISTS( modelRequest.getId() ) );
			LogWrapper.info( "ModelEntity: " + target.toString() );
			// Save the entity to the repository.
			final ModelEntity modelEntity = this.modelRepository.save(
					new ModelRequestToModelEntityConverter().convert( modelRequest )
			);
			return this.constructModel( modelEntity );
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * Updates a Model. All the fields are editable but the id. So the endpoint just replaces the contents.
	 *
	 * @param modelRequest new data to be the Model contents
	 * @return the new Model contents
	 * @since 0.8.0
	 */
	public Model updateModel( final @NotNull ModelRequest modelRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Model by id. If not found reject the request because this should be an update.
			final ModelEntity modelEntity = this.modelRepository.findById( modelRequest.getId() )
					.orElseThrow( () -> new DimensinfinRuntimeException( Printer3DErrorInfo.errorMODELNOTFOUND( modelRequest.getId() ),
							"Model not found while trying to update it." ) );
			ModelEntity updatedEntity = new ModelUpdater( modelEntity ).update( modelRequest );
			updatedEntity = this.modelRepository.save( updatedEntity );
			return new ModelEntityToModelConverter().convert( updatedEntity );
		} finally {
			LogWrapper.exit();
		}
	}

	private Model constructModel( final ModelEntity entity ) {
		// Add the parts to the model to bw responded.
		final Model model = new ModelEntityToModelConverter().convert( entity );
		for (final UUID partId : model.getPartIdList()) {
			final Optional<PartEntity> partEntityOpt = this.partRepository.findById( partId );
			partEntityOpt.ifPresent( partEntity -> model.addPart( new PartEntityToPartConverter().convert( partEntity ) ) );
		}
		return model;
	}

	/**
	 * Make sure all the requested parts are found on the list of available parts. That list have removed parts with missing coils.
	 *
	 * @param model the model to use as the part source.
	 * @return false if there is any missing part.
	 */
	private boolean noMissingParts( final ModelEntity model, final List<Part> partList ) {
		final List<UUID> partIds = model.getPartIdList();
		for (final UUID partId : partIds) {
			if (!this.partFound( partList, partId )) return false;
		}
		return true;
	}

	private boolean partFound( final List<Part> partList, final UUID partId ) {
		for (final Part part : partList) {
			if (part.getId().toString().equals( partId.toString() )) return true;
		}
		return false;
	}
}
