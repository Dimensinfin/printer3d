package org.dimensinfin.printer3d.backend.inventory.part.rest.v1;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartUpdater;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;

@Service
public class PartServiceV1 {
	private final PartRepository partRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public PartServiceV1( final @NotNull PartRepository partRepository ) {
		this.partRepository = partRepository;
	}

	public PartList getParts( final boolean activesOnly ) {
		final List<Part> parts = this.partRepository.findAll()
				.stream()
				.filter( part -> (!activesOnly || part.isActive()) )
				.collect( Collectors.toList() );
		return new PartList.Builder()
				.withPartList( parts )
				.build();
	}

	public Part newPart( final @NotNull Part newPart ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If found reject the request because this should be a new creation.
			final Optional<Part> target = this.partRepository.findById( newPart.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( ErrorInfo.PART_ALREADY_EXISTS, newPart.getId().toString() );
			return this.partRepository.save( newPart );
		} finally {
			LogWrapper.exit();
		}
	}

	public Part updatePart( final @NotNull Part updatePart ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If not found reject the request because this should be an update.
			final Optional<Part> target = this.partRepository.findById( updatePart.getId() );
			if (target.isEmpty())
				throw new DimensinfinRuntimeException( ErrorInfo.PART_NOT_FOUND, updatePart.getId().toString() );
			return this.partRepository.save( new PartUpdater( target.get() ).update( updatePart ) );
		} finally {
			LogWrapper.exit();
		}
	}
}
