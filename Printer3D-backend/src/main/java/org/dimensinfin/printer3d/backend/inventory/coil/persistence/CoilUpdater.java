package org.dimensinfin.printer3d.backend.inventory.coil.persistence;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

/**
 * The updater understands which fields should be changed on the persisted entity and how to change them. In this example there is an special change
 * that is then when a coil id deactivated (active=false) we should set a future time that is the time when the Coil instance should be definitely
 * removed (1 day in the future).
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.10.0
 */
public class CoilUpdater {
	private final CoilEntity coilEntity;

	// - C O N S T R U C T O R S
	public CoilUpdater( final @NotNull CoilEntity coilEntity ) {
		this.coilEntity = Objects.requireNonNull( coilEntity );
	}

	public CoilEntity update( final @NotNull UpdateCoilRequest updateData ) {
		if (null != updateData.getWeight()) this.coilEntity.setWeight( updateData.getWeight() );
		if (null != updateData.getTradeMark()) this.coilEntity.setTradeMark( updateData.getTradeMark() );
		if (null != updateData.getLabel()) this.coilEntity.setLabel( updateData.getLabel() );
		// [STORY] Coils that are set inactive should be removed definitively in the future.
		if (null != updateData.getActive()) {
			if (Boolean.TRUE.equals( updateData.getActive() ))
				this.coilEntity.setDestructionTime( null );
			else
				this.coilEntity.setDestructionTime( Instant.now().plus( 1, ChronoUnit.DAYS ) );
			this.coilEntity.setActive( updateData.getActive() );
		}
		return this.coilEntity;
	}
}
