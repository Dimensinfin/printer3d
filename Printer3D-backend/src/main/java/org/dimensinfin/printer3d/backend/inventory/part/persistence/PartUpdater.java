package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public class PartUpdater {
	private final Part part;

	// - C O N S T R U C T O R S
	public PartUpdater( final @NotNull Part part ) {
		this.part = Objects.requireNonNull( part );
	}

	public Part update( final @NotNull Part udpateData ) {
		this.part.setStockLevel( udpateData.getStockLevel() );
		this.part.setStockAvailable( udpateData.getStockAvailable() );
		this.part.setCost( udpateData.getCost() );
		this.part.setPrice( udpateData.getPrice() );
		this.part.setActive( udpateData.isActive() );
		return this.part;
	}
}
