package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.core.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_MODEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;

public class MachineServiceV2Test {

	private MachineRepository machineRepository;
	private PartRepository partRepository;
	private CoilRepository coilRepository;

	@BeforeEach
	public void beforeEach() {
		this.machineRepository = Mockito.mock( MachineRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
		this.coilRepository = Mockito.mock( CoilRepository.class );
	}

	@Test
	public void constructorContract() {
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.coilRepository );
		Assertions.assertNotNull( machineServiceV2 );
	}

	@Test
	public void getMachines() {
		// Given
		final PartEntity part = new PartEntity.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final MachineEntity machineEntityIdle = Mockito.mock( MachineEntity.class );
		final MachineEntity machineEntityRunning = Mockito.mock( MachineEntity.class );
		machineEntityRunning.setCurrentJobPartId( UUID.fromString( "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" ) );
		machineEntityRunning.setJobInstallmentDate( OffsetDateTime.parse( "2020-06-05T21:54:00.226181+02:00" ) );
		machineEntityRunning.setCurrentPartInstances( 3 );
		final List<MachineEntity> machineEntityList = new ArrayList<>();
		machineEntityList.add( machineEntityIdle );
		machineEntityList.add( machineEntityRunning );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( machineEntityList );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		Mockito.when( machineEntityIdle.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( machineEntityRunning.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( machineEntityIdle.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntityRunning.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntityIdle.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntityRunning.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntityRunning.getCurrentJobPartId() ).thenReturn( UUID.fromString( "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" ) );
		Mockito.when( machineEntityRunning.getJobInstallmentDate() ).thenReturn( OffsetDateTime.now().minus( Duration.ofMinutes( 4 ) ) );
		Mockito.when( machineEntityRunning.getCurrentPartInstances() ).thenReturn( 3 );
		// Test
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.coilRepository );
		final MachineListV2 obtained = machineServiceV2.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getMachines() );
		Assertions.assertEquals( 2, obtained.getMachines().size() );
		MachineV2 target = obtained.getMachines().get( 0 );
		Assertions.assertFalse( target.isRunning() );
		target = obtained.getMachines().get( 1 );
		Assertions.assertTrue( target.isRunning() );
		Assertions.assertEquals( TEST_PART_ID.toString(), target.getBuildRecord().getPart().getId().toString() );
		final int newBuildTime = (TEST_PART_BUILD_TIME * 60 * 3) - 4 * 60;
		Assertions.assertEquals( newBuildTime, target.getRemainingTime() );
	}

	@Test
	public void getMachinesPartNotFound() {
		// Given
		final MachineEntity machineEntityRunning = Mockito.mock( MachineEntity.class );
		final List<MachineEntity> machineEntityList = new ArrayList<>();
		machineEntityList.add( machineEntityRunning );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( machineEntityList );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( machineEntityRunning.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( machineEntityRunning.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntityRunning.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntityRunning.getCurrentJobPartId() ).thenReturn( UUID.fromString( "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" ) );
		Mockito.when( machineEntityRunning.getJobInstallmentDate() ).thenReturn( OffsetDateTime.now().minus( Duration.ofMinutes( 4 ) ) );
		Mockito.when( machineEntityRunning.getCurrentPartInstances() ).thenReturn( 3 );
		// Exceptions
		Assertions.assertThrows( InvalidRequestException.class, () -> {
			final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.coilRepository );
			machineServiceV2.getMachines();
		} );
	}

	//	@Test
	public void getMachinesRunComplete() {
		// Given
		final PartEntity part = new PartEntity.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final MachineEntity machineEntityIdle = Mockito.mock( MachineEntity.class );
		final MachineEntity machineEntityRunning = Mockito.mock( MachineEntity.class );
		machineEntityRunning.setCurrentJobPartId( UUID.fromString( "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" ) );
		machineEntityRunning.setJobInstallmentDate( OffsetDateTime.parse( "2020-06-06T21:54:00.226181+02:00" ) );
		machineEntityRunning.setCurrentPartInstances( 3 );
		final List<MachineEntity> machineEntityList = new ArrayList<>();
		machineEntityList.add( machineEntityIdle );
		machineEntityList.add( machineEntityRunning );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( machineEntityList );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		Mockito.when( machineEntityIdle.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( machineEntityRunning.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( machineEntityIdle.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntityRunning.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntityIdle.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntityRunning.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntityRunning.getCurrentJobPartId() ).thenReturn( UUID.fromString( "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" ) );
		Mockito.when( machineEntityRunning.getJobInstallmentDate() )
				.thenReturn( OffsetDateTime.now().minus( Duration.ofMinutes( TEST_PART_BUILD_TIME + 2 ) ) );
		Mockito.when( machineEntityRunning.getCurrentPartInstances() ).thenReturn( 3 );
		// Test
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.coilRepository );
		final MachineListV2 obtained = machineServiceV2.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getMachines() );
		Assertions.assertEquals( 2, obtained.getMachines().size() );
		MachineV2 target = obtained.getMachines().get( 0 );
		Assertions.assertFalse( target.isRunning() );
		target = obtained.getMachines().get( 1 );
		Assertions.assertFalse( target.isRunning() );
		Assertions.assertNull( target.getBuildRecord().getPart() );
		Assertions.assertEquals( 0, target.getRemainingTime() );
	}
}
