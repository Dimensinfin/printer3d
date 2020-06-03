package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class MachineUpdater {
	private final Machine machine;

	// - C O N S T R U C T O R S
	public MachineUpdater( final @NotNull Machine machine ) {
		this.machine = machine;
	}

	public Machine update( final @NotNull UUID partId ) {
		this.machine.setCurrentJobPartId( partId );
		this.machine.setCurrentPartInstances( 1 );
		this.machine.setJobInstallmentDate( LocalDateTime.now() );
		return this.machine;
	}
}
