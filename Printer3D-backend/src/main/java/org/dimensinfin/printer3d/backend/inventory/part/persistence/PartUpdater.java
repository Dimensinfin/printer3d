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

	public PartEntity update( final @NotNull Part udpateData ) {
		this.partEntity.setDescription( udpateData.getDescription() );
		this.partEntity.setStockLevel( udpateData.getStockLevel() );
		this.partEntity.setStockAvailable( udpateData.getStockAvailable() );
		this.partEntity.setCost( udpateData.getCost() );
		this.partEntity.setPrice( udpateData.getPrice() );
		this.partEntity.setActive( udpateData.isActive() );
		return this.partEntity;
	}
}
