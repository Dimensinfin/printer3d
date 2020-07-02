package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_ID;

public class MachineUpdaterV2Test {
	@Test
	public void constructorContract() {
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final MachineUpdaterV2 machineUpdaterV1 = new MachineUpdaterV2( machineEntity );
		Assertions.assertNotNull( machineUpdaterV1 );
	}

	@Test
	public void update() {
		// Given
		final MachineEntity machineEntity = new MachineEntity();
		final UUID partId = UUID.randomUUID();
		final JobRequest jobRequest = new JobRequest.Builder()
				.withPartId( TEST_JOB_PART_ID )
				.withCopies( 3 )
				.build();
		// Test
		final MachineEntity obtained = new MachineUpdaterV2( machineEntity ).update( jobRequest );
		// Assertions
		Assertions.assertEquals( TEST_JOB_PART_ID.toString(), obtained.getCurrentJobPartId().toString() );
		Assertions.assertNotNull(  obtained.getJobInstallmentDate() );
		Assertions.assertEquals( 3, obtained.getCurrentPartInstances() );
	}
}
