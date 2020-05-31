package org.dimensinfin.printer3d.backend.inventory.coil.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.domain.CoilList;

@Service
public class CoilServiceV1 {
	private final CoilRepository coilRepository;

	// - C O N S T R U C T O R S
	public CoilServiceV1( final @NotNull CoilRepository coilRepository ) {
		this.coilRepository = Objects.requireNonNull( coilRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	public CoilList getRolls() {
		final List<Coil> coils = new ArrayList<>( this.coilRepository.findAll() );
		return new CoilList.Builder()
				.withCoilList( coils )
				.build();

	}

	public Coil newCoil( final Coil newCoil ) {
		LogWrapper.enter();
		try {
			// Search for the Roll by id. If found reject the request because this should be a new creation.
			final Optional<Coil> target = this.coilRepository.findById( newCoil.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( ErrorInfo.ROLL_ALREADY_EXISTS, newCoil.getId().toString() );
			return this.coilRepository.save( newCoil );
		} finally {
			LogWrapper.exit();
		}
	}
}
