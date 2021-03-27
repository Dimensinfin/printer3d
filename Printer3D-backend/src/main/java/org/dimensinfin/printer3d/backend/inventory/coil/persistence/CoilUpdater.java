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
		if (null != updateData.getWeight()) this.coilEntity.setWeight( updateData.getWeight() );
		if (null != updateData.getTradeMark()) this.coilEntity.setTradeMark( updateData.getTradeMark() );
		if (null != updateData.getLabel()) this.coilEntity.setLabel( updateData.getLabel() );
		if (null != updateData.getActive()) this.coilEntity.setActive( updateData.getActive() );
		return this.coilEntity;
	}
}
