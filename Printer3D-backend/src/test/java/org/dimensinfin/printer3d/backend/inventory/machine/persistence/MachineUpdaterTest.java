package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MachineUpdaterTest {
	@Test
	public void constructorContract() {
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final MachineUpdater machineUpdater = new MachineUpdater( machineEntity );
		Assertions.assertNotNull( machineUpdater );
	}

	@Test
	public void update() {
		// Given
		final MachineEntity machineEntity = new MachineEntity();
		final UUID partId = UUID.randomUUID();
		// Test
		final MachineEntity obtained = new MachineUpdater( machineEntity ).update( partId );
		// Assertions
		Assertions.assertEquals( partId.toString(), obtained.getCurrentJobPartId().toString() );
		Assertions.assertEquals( 1, obtained.getCurrentPartInstances() );
	}
}
