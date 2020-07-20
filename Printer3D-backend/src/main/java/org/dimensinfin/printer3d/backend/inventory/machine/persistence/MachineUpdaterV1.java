package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import java.time.Instant;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class MachineUpdaterV1 {
	private final MachineEntity machine;

	// - C O N S T R U C T O R S
	public MachineUpdaterV1( final @NotNull MachineEntity machine ) {
		this.machine = machine;
	}

	public MachineEntity update( final @NotNull UUID partId ) {
		this.machine.setCurrentJobPartId( partId );
		this.machine.setCurrentPartInstances( 1 );
		this.machine.setJobInstallmentDate( Instant.now() );
		return this.machine;
	}
}
