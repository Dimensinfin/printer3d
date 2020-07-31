package org.dimensinfin.printer3d.backend.inventory.machine.rest.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.SetupRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_MACHINE_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.SetupRequest.TEST_SETUPREQUEST_PART_INSTANCES_COUNT;

public class MachineControllerSupportTest {
	private MachineRepository machineRepository;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.machineRepository = Mockito.mock( MachineRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository, this.partRepository );
		Assertions.assertNotNull( machineControllerSupport );
	}

	@Test
	public void setupMachine() {
		// Given
		final MachineEntity machine = new MachineEntity();
		final List<MachineEntity> machines = new ArrayList<>();
		machines.add( machine );
		final SetupRequest setupRequest = new SetupRequest.Builder()
				.withMachineLabel( TEST_SETUPREQUEST_MACHINE_LABEL )
				.withPartId( TEST_SETUPREQUEST_PART_ID )
				.withPartJobInstallmentDate( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE )
				.withPartInstancesCount( TEST_SETUPREQUEST_PART_INSTANCES_COUNT )
				.build();
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		// When
		Mockito.when( this.machineRepository.findByLabel( Mockito.anyString() ) ).thenReturn( machines );
		Mockito.when( this.machineRepository.save( Mockito.any( MachineEntity.class ) ) ).thenReturn( machine );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		Mockito.when( partEntity.getBuildTime() ).thenReturn( 15 );
		// Test
		final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository, this.partRepository );
		final ResponseEntity<Boolean> obtained = machineControllerSupport.setupMachine( setupRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertTrue( obtained.getBody() );
	}

	@Test
	public void setupMachinePartNotFound() {
		// Given
		final MachineEntity machine = new MachineEntity();
		final List<MachineEntity> machines = new ArrayList<>();
		machines.add( machine );
		final SetupRequest setupRequest = new SetupRequest.Builder()
				.withMachineLabel( TEST_SETUPREQUEST_MACHINE_LABEL )
				.withPartId( TEST_SETUPREQUEST_PART_ID )
				.withPartJobInstallmentDate( TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE )
				.withPartInstancesCount( TEST_SETUPREQUEST_PART_INSTANCES_COUNT )
				.build();
		// When
		Mockito.when( this.machineRepository.findByLabel( Mockito.anyString() ) ).thenReturn( machines );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Test
		final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository, this.partRepository );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			 machineControllerSupport.setupMachine( setupRequest );
		} );
	}

	@Test
	public void setupMachineEmptyList() {
		// Given
		final List<MachineEntity> machines = new ArrayList<>();
		final SetupRequest setupRequest = Mockito.mock( SetupRequest.class );
		// When
		Mockito.when( this.machineRepository.findByLabel( Mockito.anyString() ) ).thenReturn( machines );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository, this.partRepository );
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
			final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository, this.partRepository );
			machineControllerSupport.setupMachine( setupRequest );
		} );
	}

	@Test
	public void setupMachineNullList() {
		// Given
		final List<MachineEntity> machines = new ArrayList<>();
		final SetupRequest setupRequest = Mockito.mock( SetupRequest.class );
		// When
		Mockito.when( this.machineRepository.findByLabel( Mockito.anyString() ) ).thenReturn( null );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final MachineControllerSupport machineControllerSupport = new MachineControllerSupport( this.machineRepository, this.partRepository );
			machineControllerSupport.setupMachine( setupRequest );
		} );
	}
}
