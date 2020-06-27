package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public class PartUpdater {
	private final PartEntity partEntity;

	// - C O N S T R U C T O R S
	public PartUpdater( final @NotNull PartEntity partEntity ) {
		this.partEntity = Objects.requireNonNull( partEntity );
	}

	public PartEntity update( final @NotNull Part updateData ) {
		this.partEntity.setDescription( updateData.getDescription() );
		this.partEntity.setStockLevel( updateData.getStockLevel() );
		this.partEntity.setStockAvailable( updateData.getStockAvailable() );
		this.partEntity.setCost( updateData.getCost() );
		this.partEntity.setPrice( updateData.getPrice() );
		this.partEntity.setActive( updateData.isActive() );
		return this.partEntity;
	}
}
