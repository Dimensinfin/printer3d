package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;

public class MachineControllerV2Test {

	private MachineServiceV2 machineServiceV2;

	@BeforeEach
	public void beforeEach() {
		this.machineServiceV2 = Mockito.mock( MachineServiceV2.class );
	}

	@Test
	public void constructorContract() {
		final MachineControllerV2 machineControllerV2 = new MachineControllerV2( this.machineServiceV2 );
		Assertions.assertNotNull( machineControllerV2 );
	}

	@Test
	public void getMachines() {
		// Given
		final MachineV2 machine = Mockito.mock( MachineV2.class );
		final List<MachineV2> machines = new ArrayList<>();
		machines.add( machine );
		machines.add( machine );
		final MachineListV2 machineList = new MachineListV2.Builder()
				.withMachines( machines ).build();
		// When
		Mockito.when( this.machineServiceV2.getMachines() ).thenReturn( machineList );
		// Test
		final MachineControllerV2 machineControllerV2 = new MachineControllerV2( this.machineServiceV2 );
		final ResponseEntity<MachineListV2> obtained = machineControllerV2.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertNotNull( obtained.getBody().getMachines() );
		Assertions.assertEquals( 2, obtained.getBody().getMachines().size() );
	}
}
