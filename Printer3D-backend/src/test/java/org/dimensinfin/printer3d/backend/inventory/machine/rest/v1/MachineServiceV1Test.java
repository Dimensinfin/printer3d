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
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.StartBuildRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CHARACTERISTICS;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CURRENTJOBPARTID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CURRENTPARTINSTANCES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_JOBINSTALLMENTDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_MODEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_ID;

public class MachineServiceV1Test {

	private MachineRepository machineRepository;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.machineRepository = Mockito.mock( MachineRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void cancelBuild() {
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		// When
		Mockito.when( this.machineRepository.findById( TEST_MACHINE_ID ) ).thenReturn( Optional.of( machineEntity ) );
		Mockito.when( this.machineRepository.save( Mockito.any( MachineEntity.class ) ) ).thenReturn( machineEntity );
		Mockito.when( machineEntity.getId() ).thenReturn( TEST_MACHINE_ID );
		Mockito.when( machineEntity.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntity.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntity.getCharacteristics() ).thenReturn( TEST_MACHINE_CHARACTERISTICS );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( null );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( 1 );
		Mockito.when( machineEntity.getJobInstallmentDate() ).thenReturn( null );
		Mockito.when( machineEntity.clearJob() ).thenReturn( machineEntity );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		final Machine obtained = machineServiceV1.cancelBuild( TEST_MACHINE_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNull( obtained.getCurrentJobPart() );
		Assertions.assertNull( obtained.getJobInstallmentDate() );
		Assertions.assertEquals( 1, obtained.getCurrentPartInstances() );
	}

	@Test
	public void startBuild() {
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final StartBuildRequest startBuildRequest= new StartBuildRequest.Builder()
				.withMachineId( TEST_MACHINE_ID )
				.withPartId( TEST_PART_ID )
				.build();
		final Part part = Mockito.mock(Part.class);
		// When
		Mockito.when( this.machineRepository.findById( Mockito.any(UUID.class) ) ).thenReturn( Optional.of( machineEntity ) );
		Mockito.when( this.machineRepository.save( Mockito.any( MachineEntity.class ) ) ).thenReturn( machineEntity );
		Mockito.when( this.partRepository.findById ( Mockito.any(UUID.class) )).thenReturn( Optional.of(part) );
		Mockito.when( machineEntity.getId() ).thenReturn( TEST_MACHINE_ID );
		Mockito.when( machineEntity.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntity.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntity.getCharacteristics() ).thenReturn( TEST_MACHINE_CHARACTERISTICS );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( TEST_MACHINE_CURRENTJOBPARTID );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( 1 );
		Mockito.when( machineEntity.getJobInstallmentDate() ).thenReturn( TEST_MACHINE_JOBINSTALLMENTDATE );
		Mockito.when( machineEntity.clearJob() ).thenReturn( machineEntity );
		Mockito.when( part.getId() ).thenReturn( TEST_PART_ID );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		final Machine obtained = machineServiceV1.startBuild( startBuildRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_PART_ID.toString(), obtained.getCurrentJobPart().getId().toString() );
		Assertions.assertEquals( TEST_MACHINE_JOBINSTALLMENTDATE, obtained.getJobInstallmentDate() );
		Assertions.assertEquals( 1, obtained.getCurrentPartInstances() );
	}

	@Test
	public void startBuildMachineNotFound() {
		// Given
		final StartBuildRequest startBuildRequest= new StartBuildRequest.Builder()
				.withMachineId( TEST_MACHINE_ID )
				.withPartId( TEST_PART_ID )
				.build();
		// When
		Mockito.when( this.machineRepository.findById( Mockito.any(UUID.class) ) ).thenReturn( Optional.empty() );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
			machineServiceV1.startBuild( startBuildRequest );
		} );
	}
	@Test
	public void startBuildPartNotFound() {
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final StartBuildRequest startBuildRequest= new StartBuildRequest.Builder()
				.withMachineId( TEST_MACHINE_ID )
				.withPartId( TEST_PART_ID )
				.build();
		// When
		Mockito.when( this.machineRepository.findById( Mockito.any(UUID.class) ) ).thenReturn( Optional.of(machineEntity) );
		Mockito.when( this.partRepository.findById ( Mockito.any(UUID.class) )).thenReturn( Optional.empty() );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
			machineServiceV1.startBuild( startBuildRequest );
		} );
	}

	@Test
	public void cancelBuildNotFound() {
		// When
		Mockito.when( this.machineRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
			machineServiceV1.cancelBuild( TEST_MACHINE_ID );
		} );
	}

	@Test
	public void constructorContract() {
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		Assertions.assertNotNull( machineServiceV1 );
	}

	@Test
	public void getMachinesCompleted() {
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final List<MachineEntity> repositoryMachineList = new ArrayList<>();
		repositoryMachineList.add( machineEntity );
		repositoryMachineList.add( machineEntity );
		final Part part = Mockito.mock( Part.class );
		final UUID TEST_PART_ID = UUID.randomUUID();
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( repositoryMachineList );
		Mockito.when( machineEntity.getId() ).thenReturn( TEST_MACHINE_ID );
		Mockito.when( machineEntity.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntity.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntity.getCharacteristics() ).thenReturn( TEST_MACHINE_CHARACTERISTICS );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( TEST_MACHINE_CURRENTJOBPARTID );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( TEST_MACHINE_CURRENTPARTINSTANCES );
		Mockito.when( machineEntity.getJobInstallmentDate() ).thenReturn( LocalDateTime.now().minus( Duration.ofMinutes( 45 ) ) );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		Mockito.when( part.getBuildTime() ).thenReturn( 30 );
		Mockito.when( part.getId() ).thenReturn( TEST_PART_ID );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		final MachineList obtained = machineServiceV1.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getMachines().size() );
		Assertions.assertNotNull( obtained.getMachines().get( 0 ).getCurrentJobPart() );
		Assertions.assertEquals( TEST_PART_ID, obtained.getMachines().get( 0 ).getCurrentJobPart().getId() );
		Mockito.verify( machineEntity, Mockito.times( 2 ) ).clearJob();
		Mockito.verify( this.machineRepository, Mockito.times( 2 ) ).save( Mockito.any( MachineEntity.class ) );
		Mockito.verify( part, Mockito.times( 2 ) ).incrementStock( Mockito.anyInt() );
		Mockito.verify( this.partRepository, Mockito.times( 2 ) ).save( Mockito.any( Part.class ) );
	}

	@Test
	public void getMachinesException() {
		// Given
		final MachineEntity machine = Mockito.mock( MachineEntity.class );
		final List<MachineEntity> repositoryMachineList = new ArrayList<>();
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
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final List<MachineEntity> repositoryMachineList = new ArrayList<>();
		repositoryMachineList.add( machineEntity );
		repositoryMachineList.add( machineEntity );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( repositoryMachineList );
		Mockito.when( machineEntity.getId() ).thenReturn( TEST_MACHINE_ID );
		Mockito.when( machineEntity.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntity.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntity.getCharacteristics() ).thenReturn( TEST_MACHINE_CHARACTERISTICS );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( null );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( TEST_MACHINE_CURRENTPARTINSTANCES );
		Mockito.when( machineEntity.getJobInstallmentDate() ).thenReturn( TEST_MACHINE_JOBINSTALLMENTDATE );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		final MachineList obtained = machineServiceV1.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getMachines().size() );
		Assertions.assertNull( obtained.getMachines().get( 0 ).getCurrentJobPart() );
	}

	@Test
	public void getMachinesRunning() {
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final List<MachineEntity> repositoryMachineList = new ArrayList<>();
		repositoryMachineList.add( machineEntity );
		repositoryMachineList.add( machineEntity );
		final Part part = Mockito.mock( Part.class );
		final UUID TEST_PART_ID = UUID.randomUUID();
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( repositoryMachineList );
		Mockito.when( machineEntity.getId() ).thenReturn( TEST_MACHINE_ID );
		Mockito.when( machineEntity.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntity.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntity.getCharacteristics() ).thenReturn( TEST_MACHINE_CHARACTERISTICS );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( TEST_MACHINE_CURRENTJOBPARTID );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( TEST_MACHINE_CURRENTPARTINSTANCES );
		Mockito.when( machineEntity.getJobInstallmentDate() ).thenReturn( LocalDateTime.now() );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		Mockito.when( part.getBuildTime() ).thenReturn( 30 );
		Mockito.when( part.getId() ).thenReturn( TEST_PART_ID );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository );
		final MachineList obtained = machineServiceV1.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getMachines().size() );
		Assertions.assertNotNull( obtained.getMachines().get( 0 ).getCurrentJobPart() );
		Assertions.assertEquals( TEST_PART_ID, obtained.getMachines().get( 0 ).getCurrentJobPart().getId() );
	}
}
