package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_ID;

public class MachineControllerV1Test {

	private MachineServiceV1 machineServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.machineServiceV1 = Mockito.mock( MachineServiceV1.class );
	}

	@Test
	public void cancelBuild() {
		// Given
		final MachineV2 machine = Mockito.mock( MachineV2.class );
		// When
		Mockito.when( this.machineServiceV1.cancelBuild( Mockito.any( UUID.class ) ) ).thenReturn( machine );
		// Test
		final MachineControllerV1 machineControllerV1 = new MachineControllerV1( this.machineServiceV1 );
		final ResponseEntity<MachineV2> obtained = machineControllerV1.cancelBuild( TEST_MACHINE_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
	}

	@Test
	public void completeBuild() {
		// Given
		final MachineV2 machine = Mockito.mock( MachineV2.class );
		// When
		Mockito.when( this.machineServiceV1.completeBuild( Mockito.any( UUID.class ) ) ).thenReturn( machine );
		// Test
		final MachineControllerV1 machineControllerV1 = new MachineControllerV1( this.machineServiceV1 );
		final ResponseEntity<MachineV2> obtained = machineControllerV1.completeBuild( TEST_MACHINE_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
	}

	@Test
	public void constructorContract() {
		final MachineControllerV1 machineControllerV1 = new MachineControllerV1( this.machineServiceV1 );
		Assertions.assertNotNull( machineControllerV1 );
	}
}
