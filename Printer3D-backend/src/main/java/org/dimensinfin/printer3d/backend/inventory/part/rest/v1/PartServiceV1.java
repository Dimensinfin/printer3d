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
import org.dimensinfin.printer3d.backend.inventory.part.persistence.InventoryRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.client.domain.PartList;

@Service
public class PartServiceV1 {
	private final InventoryRepository inventoryRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public PartServiceV1( @NotNull final InventoryRepository inventoryRepository ) {
		this.inventoryRepository = inventoryRepository;
	}

	public Part newPart( @NotNull final Part newPart ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If found reject the request because this should be a new creation.
			final Optional<Part> target = this.inventoryRepository.findById( newPart.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( ErrorInfo.PART_ALREADY_EXISTS, newPart.getId().toString() );
			return this.inventoryRepository.save( newPart );
		} finally {
			LogWrapper.exit();
		}
	}

	public PartList partsList( final boolean activesOnly ) {
		final List<Part> parts = this.inventoryRepository.findAll()
				.stream()
				.filter( part -> (!activesOnly || part.isActive()) )
				.collect( Collectors.toList() );
		return new PartList.Builder()
				.withPartList( parts )
				.build();

	}
}
