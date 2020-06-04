package org.dimensinfin.printer3d.backend.inventory.machine.rest.support;

import java.time.LocalDateTime;
import java.util.Objects;

import org.dimensinfin.printer3d.client.domain.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.SetupRequest;

public class SupportMachineUpdater {
	private final Machine machine;

	// - C O N S T R U C T O R S
	public SupportMachineUpdater( final Machine machine ) {
		this.machine = Objects.requireNonNull( machine );
	}

	public Machine update( final SetupRequest setupRequest ) {
		if (null != setupRequest.getPartId()) this.machine.setCurrentJobPartId( setupRequest.getPartId() );
		if (null != setupRequest.getJobInstallmentDate())
			this.machine.setJobInstallmentDate( LocalDateTime.parse(setupRequest.getJobInstallmentDate()) );
		if (null != setupRequest.getPartInstancesCount()) this.machine.setCurrentPartInstances( setupRequest.getPartInstancesCount() );
		return this.machine;
	}
}
