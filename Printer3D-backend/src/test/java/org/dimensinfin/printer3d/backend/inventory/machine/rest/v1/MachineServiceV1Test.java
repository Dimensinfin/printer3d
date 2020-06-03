package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.core.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.Machine;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.domain.MachineList;

public class MachineServiceV1Test {

	private MachineRepository machineRepository;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.machineRepository = Mockito.mock( MachineRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		Assertions.assertNotNull( machineServiceV1 );
	}

	@Test
	public void getMachinesCompleted() {
		// Given
		final Machine machine = Mockito.mock( Machine.class );
		final List<Machine> repositoryMachineList = new ArrayList<>();
		repositoryMachineList.add( machine );
		repositoryMachineList.add( machine );
		final Part part = Mockito.mock( Part.class );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( repositoryMachineList );
		Mockito.when( machine.getCurrentJobPartId() ).thenReturn( UUID.fromString( "413447bc-d416-465d-835a-9ca93cabfc72" ) );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		Mockito.when( part.getBuildTime() ).thenReturn( 30 );
		Mockito.when( machine.getJobInstallmentDate() ).thenReturn( LocalDateTime.now().minus( Duration.ofMinutes( 45 ) ) );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		final MachineList obtained = machineServiceV1.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getMachines().size() );
		Assertions.assertNotNull( obtained.getMachines().get( 0 ).getCurrentJobPartId() );
		Mockito.verify( machine, Mockito.times( 2 ) ).clearJob();
		Mockito.verify( this.machineRepository, Mockito.times( 2 ) ).save( Mockito.any( Machine.class ) );
		Mockito.verify( part, Mockito.times( 2 ) ).incrementStock( Mockito.anyInt() );
		Mockito.verify( this.partRepository, Mockito.times( 2 ) ).save( Mockito.any( Part.class ) );
	}

	@Test
	public void getMachinesException() {
		// Given
		final Machine machine = Mockito.mock( Machine.class );
		final List<Machine> repositoryMachineList = new ArrayList<>();
		repositoryMachineList.add( machine );
		repositoryMachineList.add( machine );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( repositoryMachineList );
		Mockito.when( machine.getCurrentJobPartId() ).thenReturn( UUID.fromString( "413447bc-d416-465d-835a-9ca93cabfc72" ) );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.ofNullable( null ) );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		Assertions.assertThrows( InvalidRequestException.class, () -> {
			machineServiceV1.getMachines();
		} );
	}

	@Test
	public void getMachinesIdle() {
		final Machine machine = Mockito.mock( Machine.class );
		// Given
		final List<Machine> repositoryMachineList = new ArrayList<>();
		repositoryMachineList.add( machine );
		repositoryMachineList.add( machine );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( repositoryMachineList );
		Mockito.when( machine.getCurrentJobPartId() ).thenReturn( null );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		final MachineList obtained = machineServiceV1.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getMachines().size() );
		Assertions.assertNull( obtained.getMachines().get( 0 ).getCurrentJobPartId() );
	}

	@Test
	public void getMachinesRunning() {
		// Given
		final Machine machine = Mockito.mock( Machine.class );
		final List<Machine> repositoryMachineList = new ArrayList<>();
		repositoryMachineList.add( machine );
		repositoryMachineList.add( machine );
		final Part part = Mockito.mock( Part.class );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( repositoryMachineList );
		Mockito.when( machine.getCurrentJobPartId() ).thenReturn( UUID.fromString( "413447bc-d416-465d-835a-9ca93cabfc72" ) );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		Mockito.when( part.getBuildTime() ).thenReturn( 30 );
		Mockito.when( machine.getJobInstallmentDate() ).thenReturn( LocalDateTime.now() );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		final MachineList obtained = machineServiceV1.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getMachines().size() );
		Assertions.assertNotNull( obtained.getMachines().get( 0 ).getCurrentJobPartId() );
	}
}
