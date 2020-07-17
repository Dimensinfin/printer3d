package org.dimensinfin.printer3d.backend.inventory.model.persistence;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelRequest;

public class ModelUpdater {
	private final ModelEntity modelEntity;

	// - C O N S T R U C T O R S
	public ModelUpdater( final @NotNull ModelEntity modelEntity ) {
		this.modelEntity = Objects.requireNonNull( modelEntity );
	}

	public ModelEntity update( final @NotNull ModelRequest updateData ) {
		return new ModelEntity.Builder()
				.withId( this.modelEntity.getId() )
				.withLabel( updateData.getLabel() )
				.withPrice( updateData.getPrice() )
				.withStockLevel( updateData.getStockLevel() )
				.withImagePath( updateData.getImagePath() )
				.withPartIdList( updateData.getPartIdList() )
				.withActive( true )
				.build();
	}
}
