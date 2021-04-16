package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

public class PartGroupUpdater {
	private final PartEntity partEntity;

	// - C O N S T R U C T O R S
	public PartGroupUpdater( final @NotNull PartEntity partEntity ) {
		this.partEntity = Objects.requireNonNull( partEntity );
	}

	public PartEntity update( final @NotNull UpdateGroupPartRequest updateData ) {
		if (null != updateData.getProject()) partEntity.setProject( updateData.getProject() );
		if (null != updateData.getDescription()) partEntity.setDescription( updateData.getDescription() );
		if (null != updateData.getBuildTime()) partEntity.setBuildTime( updateData.getBuildTime() );
		if (null != updateData.getWeight()) partEntity.setWeight( updateData.getWeight() );
		if (null != updateData.getImagePath()) partEntity.setImagePath( updateData.getImagePath() );
		if (null != updateData.getModelPath()) partEntity.setModelPath( updateData.getModelPath() );
		return partEntity;
	}
}
