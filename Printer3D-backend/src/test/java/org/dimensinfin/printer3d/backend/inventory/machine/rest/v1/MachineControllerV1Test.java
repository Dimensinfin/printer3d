package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.machine.persistence.Machine;
import org.dimensinfin.printer3d.client.domain.MachineList;

public class MachineControllerV1Test {

	private MachineServiceV1 machineServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.machineServiceV1 = Mockito.mock( MachineServiceV1.class );
	}

	@Test
	public void constructorContract() {
		final MachineControllerV1 machineControllerV1 = new MachineControllerV1( this.machineServiceV1 );
		Assertions.assertNotNull( machineControllerV1 );
	}

	@Test
	public void getMachines() {
		//Given
		final Machine machine = Mockito.mock( Machine.class );
		final List<Machine> machineList = new ArrayList<>();
		machineList.add( machine );
		machineList.add( machine );
		machineList.add( machine );
		final MachineList machineListContainer = new MachineList.Builder().withMachines( machineList ).build();
		// When
		Mockito.when( this.machineServiceV1.getMachines() ).thenReturn( machineListContainer );
		// Test
		final MachineControllerV1 machineControllerV1 = new MachineControllerV1( this.machineServiceV1 );
		final ResponseEntity<MachineList> obtained = machineControllerV1.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertNotNull( obtained.getBody().getMachines() );
		Assertions.assertEquals( 3, obtained.getBody().getMachines().size() );
	}
}
