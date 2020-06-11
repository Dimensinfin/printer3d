package org.dimensinfin.printer3d.backend.inventory.machine.rest.support;

import java.time.OffsetDateTime;
import java.util.Objects;

import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.SetupRequest;

public class SupportMachineUpdater {
	private final MachineEntity machine;

	// - C O N S T R U C T O R S
	public SupportMachineUpdater( final MachineEntity machine ) {
		this.machine = Objects.requireNonNull( machine );
	}

	public MachineEntity update( final SetupRequest setupRequest ) {
		if (null != setupRequest.getPartId()) this.machine.setCurrentJobPartId( setupRequest.getPartId() );
		if (null != setupRequest.getJobInstallmentDate())
			this.machine.setJobInstallmentDate( OffsetDateTime.parse(setupRequest.getJobInstallmentDate()) );
		if (null != setupRequest.getPartInstancesCount()) this.machine.setCurrentPartInstances( setupRequest.getPartInstancesCount() );
		return this.machine;
	}
}
