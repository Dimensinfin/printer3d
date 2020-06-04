package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class MachineEntityTest {
	@Test
	public void clearJob() {
		// Test
		final MachineEntity machineEntity = new MachineEntity();
		machineEntity.clearJob();
		// Assertions
		Assertions.assertNotNull( machineEntity );
		Assertions.assertNull( machineEntity.getLabel() );
		Assertions.assertNull( machineEntity.getCurrentJobPartId() );
		Assertions.assertNull( machineEntity.getJobInstallmentDate() );
		Assertions.assertEquals( 1, machineEntity.getCurrentPartInstances() );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( MachineEntity.class )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final MachineEntity machineEntity = new MachineEntity();
		// Assertions
		Assertions.assertNotNull( machineEntity );
		Assertions.assertNull( machineEntity.getLabel() );
		Assertions.assertNull( machineEntity.getCurrentJobPartId() );
		Assertions.assertNull( machineEntity.getJobInstallmentDate() );
		Assertions.assertEquals( 1, machineEntity.getCurrentPartInstances() );
	}

	@Test
	public void toStringContract() {
		// Given
		final MachineEntity machineEntity = new MachineEntity();
		// Test
		final String expected = "{\"id\":null,\"label\":null,\"model\":null,\"characteristics\":null,\"currentJobPartId\":null,\"currentPartInstances\":1,\"jobInstallmentDate\":null}";
		final String obtained = machineEntity.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
