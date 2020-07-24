package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

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
		// When
		Mockito.when( this.machineServiceV2.getMachines() ).thenReturn( machines );
		// Test
		final MachineControllerV2 machineControllerV2 = new MachineControllerV2( this.machineServiceV2 );
		final ResponseEntity<List<MachineV2>> obtained = machineControllerV2.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 2, obtained.getBody().size() );
	}

	@Test
	public void startBuild() {
		// Given
		final UUID machineId = UUID.fromString( "825d5395-f13c-45d2-8b69-77ef10487205" );
		final JobRequest jobRequest = Mockito.mock( JobRequest.class );
		final MachineV2 machine = Mockito.mock( MachineV2.class );
		// When
		Mockito.when( this.machineServiceV2.startBuild( machineId, jobRequest ) ).thenReturn( machine );
		Mockito.when( machine.getId() ).thenReturn( machineId );
		// Test
		final MachineControllerV2 machineControllerV2 = new MachineControllerV2( this.machineServiceV2 );
		final ResponseEntity<MachineV2> obtained = machineControllerV2.startBuild( machineId, jobRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( machineId.toString(), obtained.getBody().getId().toString() );
	}
}
