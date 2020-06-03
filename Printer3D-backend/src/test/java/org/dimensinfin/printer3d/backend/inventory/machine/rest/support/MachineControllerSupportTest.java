package org.dimensinfin.printer3d.backend.inventory.machine.rest.support;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.Machine;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.client.inventory.rest.SetupRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_MACHINE_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_PART_INSTANCES_COUNT;

public class MachineControllerSupportTest {
	private MachineRepository machineRepository;

	@BeforeEach
	public void beforeEach() {
		this.machineRepository = Mockito.mock( MachineRepository.class );
	}

	@Test
	public void constructorContract() {
		final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository );
		Assertions.assertNotNull( machineControllerSupport );
	}

	@Test
	public void setupMachine() {
		// Given
		final Machine machine = new Machine();
		final List<Machine> machines = new ArrayList<>();
		machines.add( machine );
		final SetupRequest setupRequest = new SetupRequest.Builder()
				.withMachineLabel( TEST_SETUPREQUEST_MACHINE_LABEL )
				.withPartId( TEST_SETUPREQUEST_PART_ID )
				.withPartJobInstallmentDate( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE )
				.withPartInstancesCount( TEST_SETUPREQUEST_PART_INSTANCES_COUNT )
				.build();
		// When
		Mockito.when( this.machineRepository.findByLabel( Mockito.anyString() ) ).thenReturn( machines );
		// Test
		final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository );
		final ResponseEntity<Boolean> obtained = machineControllerSupport.setupMachine( setupRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertTrue( obtained.getBody() );
	}

	@Test
	public void setupMachineEmptyLit() {
		// Given
		final List<Machine> machines = new ArrayList<>();
		final SetupRequest setupRequest = Mockito.mock( SetupRequest.class );
		// When
		Mockito.when( this.machineRepository.findByLabel( Mockito.anyString() ) ).thenReturn( machines );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository );
			machineControllerSupport.setupMachine( setupRequest );
		} );
	}

	@Test
	public void setupMachineNotFound() {
		// Given
		final SetupRequest setupRequest = Mockito.mock( SetupRequest.class );
		// When
		Mockito.when( this.machineRepository.findByLabel( Mockito.anyString() ) ).thenReturn( null );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository );
			machineControllerSupport.setupMachine( setupRequest );
		} );
	}
}
