package org.dimensinfin.printer3d.backend.inventory.roll.rest.v1;

import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.RollRepository;

@Service
public class RollServiceV1 {
	private final RollRepository rollRepository;

	public RollServiceV1( final @NotNull  RollRepository rollRepository ) {
		this.rollRepository = Objects.requireNonNull(rollRepository);
	}

	public Roll newRoll( final Roll newRoll ) {
		LogWrapper.enter();
		try {
			// Search for the Roll by id. If found reject the request because this should be a new creation.
			final Optional<Roll> target = this.rollRepository.findById( newRoll.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( ErrorInfo.ROLL_ALREADY_EXISTS, newRoll.getId().toString() );
			return this.rollRepository.save( newRoll );
		} finally {
			LogWrapper.exit();
		}
	}
}
