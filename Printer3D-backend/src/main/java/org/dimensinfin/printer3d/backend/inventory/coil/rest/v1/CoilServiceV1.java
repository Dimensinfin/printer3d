package org.dimensinfin.printer3d.backend.inventory.coil.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilUpdater;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

@Service
public class CoilServiceV1 {
	private final CoilRepository coilRepository;

	// - C O N S T R U C T O R S
	public CoilServiceV1( final @NotNull CoilRepository coilRepository ) {
		this.coilRepository = Objects.requireNonNull( coilRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	@Deprecated
	public CoilList getCoils() {
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
				throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorCOILALREADYEXISTS( newCoil.getId() ) );
			return this.coilRepository.save( newCoil.complete() );
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * Updates a Coil. The only field allowed to be changed is the weight.
	 *
	 * @since 0.10.0
	 */
	public Coil updateCoil( final @NotNull UpdateCoilRequest updateCoilRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Model by id. If not found reject the request because this should be an update.
			final Optional<Coil> target = this.coilRepository.findById( updateCoilRequest.getId() );
			if (target.isEmpty())
				throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorCOILNOTFOUND( updateCoilRequest.getId() ) );
			final Coil coilEntity = new CoilUpdater( target.get() ).update( updateCoilRequest );
			return this.coilRepository.save( coilEntity );
		} finally {
			LogWrapper.exit();
		}

	}
}
