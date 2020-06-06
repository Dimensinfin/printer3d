package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.StartBuildRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_ID;

public class MachineControllerV1Test {

	private MachineServiceV1 machineServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.machineServiceV1 = Mockito.mock( MachineServiceV1.class );
	}

	@Test
	public void cancelBuild() {
		// Given
		final Machine machine = Mockito.mock( Machine.class );
		// When
		Mockito.when( this.machineServiceV1.cancelBuild( Mockito.any( UUID.class ) ) ).thenReturn( machine );
		// Test
		final MachineControllerV1 machineControllerV1 = new MachineControllerV1( this.machineServiceV1 );
		final ResponseEntity<Machine> obtained = machineControllerV1.cancelBuild( TEST_MACHINE_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
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

	@Test
	public void startBuild() {
		// Given
		final Machine machine = Mockito.mock( Machine.class );
		final StartBuildRequest startBuildRequest = new StartBuildRequest.Builder()
				.withMachineId( TEST_MACHINE_ID )
				.withPartId( TEST_PART_ID )
				.build();
		// When
		Mockito.when( this.machineServiceV1.startBuild( Mockito.any( StartBuildRequest.class ) ) ).thenReturn( machine );
		// Test
		final MachineControllerV1 machineControllerV1 = new MachineControllerV1( this.machineServiceV1 );
		final ResponseEntity<Machine> obtained = machineControllerV1.startBuild( TEST_MACHINE_ID, TEST_PART_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
	}
}
