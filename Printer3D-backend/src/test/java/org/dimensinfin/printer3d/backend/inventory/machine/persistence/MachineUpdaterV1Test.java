package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MachineUpdaterV1Test {
	@Test
	public void constructorContract() {
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final MachineUpdaterV1 machineUpdaterV1 = new MachineUpdaterV1( machineEntity );
		Assertions.assertNotNull( machineUpdaterV1 );
	}

	@Test
	public void update() {
		// Given
		final MachineEntity machineEntity = new MachineEntity();
		final UUID partId = UUID.randomUUID();
		// Test
		final MachineEntity obtained = new MachineUpdaterV1( machineEntity ).update( partId );
		// Assertions
		Assertions.assertEquals( partId.toString(), obtained.getCurrentJobPartId().toString() );
		Assertions.assertEquals( 1, obtained.getCurrentPartInstances() );
	}
}
