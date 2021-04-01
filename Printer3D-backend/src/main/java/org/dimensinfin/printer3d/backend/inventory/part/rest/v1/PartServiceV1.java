package org.dimensinfin.printer3d.backend.inventory.part.rest.v1;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartToPartEntityConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartGroupUpdater;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartUpdater;
import org.dimensinfin.printer3d.backend.inventory.part.rest.PartRestErrors;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

@Service
public class PartServiceV1 {
	protected final PartRepository partRepository;
	protected final PartEntityToPartConverter partConverter;

	// - C O N S T R U C T O R S
	@Autowired
	public PartServiceV1( @NotNull final PartRepository partRepository,
	                      @NotNull final PartEntityToPartConverter partConverter ) {
		this.partRepository = partRepository;
		this.partConverter = partConverter;
	}

	public PartList getParts( final boolean activesOnly ) {
		final List<Part> parts = this.partRepository.findAll()
				.stream()
				.filter( part -> (!activesOnly || part.isActive()) )
				.map( this.partConverter::convert )
				.collect( Collectors.toList() );
		return new PartList.Builder()
				.withPartList( parts )
				.build();
	}

	/**
	 * The Printer3D user interface should have requested the Part contents to the user. This endpoint should validate all the fields against the
	 * validation requirements and create a new record on the Inventory repository for the new Part.
	 *
	 * @param newPart the new part fields filled at the frontend user interface.
	 * @return the Part persisted.
	 */
	public Part newPart( final @NotNull Part newPart ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If found reject the request because this should be a new creation.
			final Optional<PartEntity> target = this.partRepository.findById( newPart.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( PartRestErrors.errorPARTALREADYEXISTS( newPart.getId() ) );
			final PartEntity partEntity = new PartToPartEntityConverter().convert( newPart );
			try {
				return new PartEntityToPartConverter().convert( this.partRepository.save( partEntity ) );
			} catch (final DataIntegrityViolationException die) {
				throw new DimensinfinRuntimeException( PartRestErrors.errorPARTREPOSITORYCONFLICT( newPart.getId(), die.getMessage() ) );
			}
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * Usually there a re a set of parts that share most of the field values changing only the finishings (material and color). Because the
	 * frontend will group them into sets and there is no purpose on having different values for same parts this endpoint will take care of
	 * changing the et values to the whole set of parts that have the same Label.
	 */
	public CounterResponse updateGroupPart( final @NotNull UpdateGroupPartRequest updateData ) {
		LogWrapper.enter();
		try {
			// Search for the Parts that have the same label.
			final List<PartEntity> targets = this.partRepository.findByLabel( updateData.getLabel() );
			int counter = 0;
			for (final PartEntity partEntity : targets) {
				LogWrapper.info( MessageFormat.format( "Updating part [{0}] with label: {1}",
						partEntity.getId(),
						partEntity.getLabel() )
				);
				this.partRepository.save( new PartGroupUpdater( partEntity ).update( updateData ) );
				counter++;
			}
			return new CounterResponse.Builder().withRecords( counter ).build();
		} finally {
			LogWrapper.exit();
		}
	}

	public Part updatePart( final @NotNull Part updatePart ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If not found reject the request because this should be an update.
			final Optional<PartEntity> target = this.partRepository.findById( updatePart.getId() );
			if (target.isEmpty())
				throw new DimensinfinRuntimeException( PartRestErrors.errorPARTNOTFOUND( updatePart.getId() ),
						"Part not found while trying to update it." );
			return new PartEntityToPartConverter().convert(
					this.partRepository.save( new PartUpdater( target.get() ).update( updatePart ) )
			);
		} finally {
			LogWrapper.exit();
		}
	}
}
