package org.dimensinfin.printer3d.backend.inventory.machine.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.SetupRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_MACHINE_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_PART_INSTANCES_COUNT;

public class SupportMachineUpdaterTest {

	@Test
	public void update() {
		// Given
		final MachineEntity machine = new MachineEntity();
		final SetupRequest setupRequest = new SetupRequest.Builder()
				.withMachineLabel( TEST_SETUPREQUEST_MACHINE_LABEL )
				.withPartId( TEST_SETUPREQUEST_PART_ID )
				.withPartJobInstallmentDate( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE )
				.withPartInstancesCount( TEST_SETUPREQUEST_PART_INSTANCES_COUNT )
				.build();
		// Test
		final MachineEntity obtained = new SupportMachineUpdater( machine ).update( setupRequest );
		// Assertions
		Assertions.assertEquals( TEST_SETUPREQUEST_PART_ID.toString(), obtained.getCurrentJobPartId().toString() );
		Assertions.assertEquals( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE, obtained.getJobInstallmentDate().toString() );
		Assertions.assertEquals( TEST_SETUPREQUEST_PART_INSTANCES_COUNT, obtained.getCurrentPartInstances() );
	}
}
