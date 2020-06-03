package org.dimensinfin.printer3d.client.inventory.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_MACHINE_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_PART_INSTANCES_COUNT;

public class SetupRequestTest {
	@Test
	public void buildContract() {
		final SetupRequest setupRequest = new SetupRequest.Builder()
				.withMachineLabel( TEST_SETUPREQUEST_MACHINE_LABEL )
				.withPartId( TEST_SETUPREQUEST_PART_ID )
				.withPartJobInstallmentDate( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE )
				.withPartInstancesCount( TEST_SETUPREQUEST_PART_INSTANCES_COUNT )
				.build();
		Assertions.assertNotNull( setupRequest );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new SetupRequest.Builder()
					.withMachineLabel( null )
					.withPartId( TEST_SETUPREQUEST_PART_ID )
					.withPartJobInstallmentDate( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE )
					.withPartInstancesCount( TEST_SETUPREQUEST_PART_INSTANCES_COUNT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new SetupRequest.Builder()
					.withMachineLabel( TEST_SETUPREQUEST_MACHINE_LABEL )
					.withPartId( null )
					.withPartJobInstallmentDate( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE )
					.withPartInstancesCount( TEST_SETUPREQUEST_PART_INSTANCES_COUNT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new SetupRequest.Builder()
					.withMachineLabel( TEST_SETUPREQUEST_MACHINE_LABEL )
					.withPartId( TEST_SETUPREQUEST_PART_ID )
					.withPartJobInstallmentDate( null )
					.withPartInstancesCount( TEST_SETUPREQUEST_PART_INSTANCES_COUNT )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new SetupRequest.Builder()
					.withMachineLabel( TEST_SETUPREQUEST_MACHINE_LABEL )
					.withPartId( TEST_SETUPREQUEST_PART_ID )
					.withPartJobInstallmentDate( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE )
					.withPartInstancesCount( null )
					.build();
		} );
	}

	@Test
	public void getterContract() {
		// Given
		final SetupRequest setupRequest = new SetupRequest.Builder()
				.withMachineLabel( TEST_SETUPREQUEST_MACHINE_LABEL )
				.withPartId( TEST_SETUPREQUEST_PART_ID )
				.withPartJobInstallmentDate( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE )
				.withPartInstancesCount( TEST_SETUPREQUEST_PART_INSTANCES_COUNT )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_SETUPREQUEST_MACHINE_LABEL, setupRequest.getMachineLabel() );
		Assertions.assertEquals( TEST_SETUPREQUEST_PART_ID.toString(), setupRequest.getPartId().toString() );
		Assertions.assertEquals( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE, setupRequest.getJobInstallmentDate().toString() );
		Assertions.assertEquals( TEST_SETUPREQUEST_PART_INSTANCES_COUNT, setupRequest.getPartInstancesCount() );
	}
}
