package org.dimensinfin.printer3d.backend.inventory.coil.persistence;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

public class CoilUpdater {
	private final Coil coilEntity;

	// - C O N S T R U C T O R S
	public CoilUpdater( final @NotNull Coil coilEntity ) {
		this.coilEntity = Objects.requireNonNull( coilEntity );
	}

	public Coil update( final @NotNull UpdateCoilRequest updateData ) {
		return this.coilEntity.setWeight( updateData.getWeight() );
	}
}
