package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

public class MachineUpdaterV2 {
	private final MachineEntity machine;

	// - C O N S T R U C T O R S
	public MachineUpdaterV2( final @NotNull MachineEntity machine ) {
		this.machine = machine;
	}

	public MachineEntity update( final @NotNull JobRequest jobRequest ) {
		this.machine.setCurrentJobPartId( jobRequest.getPartId() );
		this.machine.setCurrentPartInstances( jobRequest.getCopies() );
		this.machine.setJobInstallmentDate( Instant.now() );
		return this.machine;
	}
}
