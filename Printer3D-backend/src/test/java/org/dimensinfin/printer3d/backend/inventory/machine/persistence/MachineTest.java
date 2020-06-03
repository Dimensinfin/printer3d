package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class MachineTest {
	@Test
	public void clearJob() {
		// Test
		final Machine machine = new Machine();
		machine.clearJob();
		// Assertions
		Assertions.assertNotNull( machine );
		Assertions.assertNull( machine.getLabel() );
		Assertions.assertNull( machine.getCurrentJobPartId() );
		Assertions.assertNull( machine.getJobInstallmentDate() );
		Assertions.assertEquals( 1, machine.getCurrentPartInstances() );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( Machine.class )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final Machine machine = new Machine();
		// Assertions
		Assertions.assertNotNull( machine );
		Assertions.assertNull( machine.getLabel() );
		Assertions.assertNull( machine.getCurrentJobPartId() );
		Assertions.assertNull( machine.getJobInstallmentDate() );
		Assertions.assertEquals( 1, machine.getCurrentPartInstances() );
	}

	@Test
	public void toStringContract() {
		// Given
		final Machine machine = new Machine();
		// Test
		final String expected = "{\"id\":null,\"label\":null,\"model\":null,\"characteristics\":null,\"currentJobPartId\":null,\"currentPartInstances\":1,\"jobInstallmentDate\":null}";
		final String obtained = machine.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );

	}
}
