package org.dimensinfin.printer3d.backend.inventory.part.rest.v1;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.common.exception.ErrorInfoN;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartToPartEntityConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartGroupUpdater;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartUpdater;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

@Service
public class PartServiceV1 {
	private final PartRepository partRepository;
	private final PartEntityToPartConverter partConverter;
	private final ErrorInfoN PART_ALREADY_EXISTS = new ErrorInfoN.Builder()
			.withCode( "PART_ALREADY_EXISTS" )
			.withHttpStatus( HttpStatus.CONFLICT )
			.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".already.exists" )
			.withMessageTemplate( "The Part [{0}] already exists. Use the Update endpoint." )
			.build();

	// - C O N S T R U C T O R S
	@Autowired
	public PartServiceV1( final @NotNull PartRepository partRepository,
	                      final @NotNull PartEntityToPartConverter partConverter ) {
		this.partRepository = partRepository;
		this.partConverter = partConverter;
	}

	public PartList getParts( final boolean activesOnly ) {
		final List<Part> parts = this.partRepository.findAll()
				.stream()
				.filter( part -> (!activesOnly || part.isActive()) )
				.map( part -> this.partConverter.convert( part ) )
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
				throw new RepositoryConflictException( PART_ALREADY_EXISTS, newPart.getId().toString() );
			final PartEntity partEntity = new PartToPartEntityConverter().convert( newPart );
			try {
				return new PartEntityToPartConverter().convert( this.partRepository.save( partEntity ) );
			} catch (final DataIntegrityViolationException die) {
				throw new RepositoryConflictException( ErrorInfo.PART_REPOSITORY_CONFLICT, die );
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
	public CountResponse updateGroupPart( final @NotNull UpdateGroupPartRequest updateData ) {
		LogWrapper.enter();
		try {
			// Search for the Parts that have the same label.
			final List<PartEntity> targets = this.partRepository.findByLabel( updateData.getLabel() );
			int counter = 0;
			for (PartEntity partEntity : targets) {
				this.partRepository.save( new PartGroupUpdater( partEntity ).update( updateData ) );
				counter++;
			}
			return new CountResponse.Builder().withRecords( counter ).build();
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
				throw new DimensinfinRuntimeException( ErrorInfo.PART_NOT_FOUND, updatePart.getId().toString() );
			return new PartEntityToPartConverter().convert(
					this.partRepository.save( new PartUpdater( target.get() ).update( updatePart ) )
			);
		} finally {
			LogWrapper.exit();
		}
	}
}
