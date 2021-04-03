package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.machine.domain.PlasticConsumerManager;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_COPIES;
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
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_WEIGHT;

public class MachineServiceV2Test {

	private MachineRepository machineRepository;
	private PartRepository partRepository;
	private PlasticConsumerManager plasticConsumerManager;

	@BeforeEach
	public void beforeEach() {
		this.machineRepository = Mockito.mock( MachineRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
		this.plasticConsumerManager = Mockito.mock( PlasticConsumerManager.class );
	}

	@Test
	public void constructorContract() {
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.plasticConsumerManager );
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
		machineEntityRunning.setJobInstallmentDate( Instant.parse( "2020-06-05T21:54:00.226181Z" ) );
		machineEntityRunning.setCurrentPartInstances( 3 );
		machineEntityRunning.setCurrentJobPartBuildTime( 45 );
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
		Mockito.when( machineEntityRunning.getJobInstallmentDate() ).thenReturn( Instant.now().minus( Duration.ofMinutes( 4 ) ) );
		Mockito.when( machineEntityRunning.getCurrentPartInstances() ).thenReturn( 3 );
		Mockito.when( machineEntityRunning.getCurrentJobPartBuildTime() ).thenReturn( TEST_PART_BUILD_TIME );
		// Test
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.plasticConsumerManager );
		final List<MachineV2> obtained = machineServiceV2.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.size() );
		MachineV2 target = obtained.get( 0 );
		Assertions.assertFalse( target.isRunning() );
		target = obtained.get( 1 );
		Assertions.assertTrue( target.isRunning() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME, target.getBuildRecord().getJobBuildTime() );
		Assertions.assertEquals( TEST_PART_ID.toString(), target.getBuildRecord().getPart().getId().toString() );
		final int newBuildTime = (TEST_PART_BUILD_TIME * 60 * 3) - 4 * 60;
		assertThat( target.getRemainingTime() ).isBetween( newBuildTime - 1, newBuildTime );
	}

	@Test
	public void getMachinesNullPointerException() {
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final List<MachineEntity> machineEntityList = new ArrayList<>();
		machineEntityList.add( machineEntity );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( machineEntityList );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenThrow( NullPointerException.class );
		// Exceptions
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.plasticConsumerManager );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			machineServiceV2.getMachines();
		} );
	}

	@Test
	public void getMachinesPartNotFound() {
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final List<MachineEntity> machineEntityList = new ArrayList<>();
		machineEntityList.add( machineEntity );
		// When
		Mockito.when( this.machineRepository.findAll() ).thenReturn( machineEntityList );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( 3 );
		Mockito.when( machineEntity.getCurrentJobPartBuildTime() ).thenReturn( TEST_PART_BUILD_TIME );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( UUID.fromString( "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" ) );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Exceptions
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.plasticConsumerManager );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			machineServiceV2.getMachines();
		} );
	}

	@Test
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
		machineEntityRunning.setJobInstallmentDate( Instant.parse( "2020-06-06T21:54:00.226181Z" ) );
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
		Mockito.when( machineEntityIdle.getCurrentPartInstances() ).thenReturn( 2 );
		Mockito.when( machineEntityRunning.getCurrentPartInstances() ).thenReturn( 2 );
		Mockito.when( machineEntityIdle.getCurrentJobPartBuildTime() ).thenReturn( 0 );
		Mockito.when( machineEntityRunning.getCurrentJobPartBuildTime() ).thenReturn( TEST_PART_BUILD_TIME );
		Mockito.when( machineEntityIdle.getJobInstallmentDate() ).thenReturn( null );
		Mockito.when( machineEntityRunning.getJobInstallmentDate() ).thenReturn( Instant.now() );

		Mockito.when( machineEntityRunning.getCurrentJobPartId() ).thenReturn( UUID.fromString( "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" ) );
		Mockito.when( machineEntityRunning.getJobInstallmentDate() )
				.thenReturn( Instant.now().minus( Duration.ofMinutes( TEST_PART_BUILD_TIME + 2 ) ) );
		Mockito.when( machineEntityRunning.getCurrentPartInstances() ).thenReturn( 3 );
		Mockito.when( machineEntityRunning.getCurrentJobPartBuildTime() ).thenReturn( TEST_PART_BUILD_TIME );
		// Test
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.plasticConsumerManager );
		final List<MachineV2> obtained = machineServiceV2.getMachines();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.size() );
		MachineV2 target = obtained.get( 0 );
		Assertions.assertFalse( target.isRunning() );
		Assertions.assertNull( target.getBuildRecord().getPart() );
		Assertions.assertEquals( 0, target.getRemainingTime() );
		target = obtained.get( 1 );
		Assertions.assertTrue( target.isRunning() );
	}

	@Test
	public void startBuild() {
		// Given
		final UUID machineId = UUID.fromString( "988bb790-2212-4c8a-bdbf-8a5a95199c91" );
		final JobRequest jobRequest = new JobRequest.Builder()
				.withId( TEST_JOB_ID )
				.withPartId( TEST_PART_ID )
				.withCopies( TEST_JOB_PART_COPIES )
				.build();
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withWeight( TEST_PART_WEIGHT )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<Coil> coils = new ArrayList<>();
		coils.add( new Coil.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( "FLEX" )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "ROJO" )
				.withWeight( 800 )
				.build() );
		coils.add( new Coil.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( "PLA" )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "ROJO" )
				.withWeight( 800 )
				.build() );
		final Coil targetCoil = new Coil.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( "PLA" )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "VERDE-T" )
				.withWeight( 100 )
				.build();
		coils.add( targetCoil );
		final Coil coil = new Coil.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( "PLA" )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "ROJO" )
				.withWeight( 800 )
				.build();
		// When
		Mockito.when( this.machineRepository.findById( machineId ) ).thenReturn( Optional.of( machineEntity ) );
		Mockito.when( this.partRepository.findById( TEST_PART_ID ) ).thenReturn( Optional.of( partEntity ) );
		//		Mockito.doNothing().when( this.plasticConsumerManager ).subtractPlasticFromCoil(
		//				Mockito.any( PartEntity.class ),
		//				Mockito.anyInt() );

		Mockito.when( this.machineRepository.save( Mockito.any( MachineEntity.class ) ) ).thenReturn( machineEntity );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( machineEntity.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( machineEntity.getLabel() ).thenReturn( "LABEL" );
		Mockito.when( machineEntity.getModel() ).thenReturn( "MODEL" );
		Mockito.when( machineEntity.getCharacteristics() ).thenReturn( "CHARACTERISTICS" );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( 2 );
		Mockito.when( machineEntity.getJobInstallmentDate() ).thenReturn( Instant.now() );
		// Test
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.plasticConsumerManager );
		final MachineV2 obtained = machineServiceV2.startBuild( machineId, jobRequest );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( true, obtained.isRunning() );
		//		Assertions.assertEquals( 100 - 10 * 3, targetCoil.getWeight() );
	}

	@Test
	public void startBuildMachineNotFound() {
		// Given
		final UUID machineId = UUID.fromString( "988bb790-2212-4c8a-bdbf-8a5a95199c91" );
		final JobRequest jobRequest = new JobRequest.Builder()
				.withId( TEST_JOB_ID )
				.withPartId( TEST_PART_ID )
				.withCopies( TEST_JOB_PART_COPIES )
				.build();
		// When
		Mockito.when( this.machineRepository.findById( machineId ) ).thenReturn( Optional.empty() );
		// Test
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.plasticConsumerManager );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			machineServiceV2.startBuild( machineId, jobRequest );
		} );
	}

	@Test
	public void startBuildNotMaterial() {
		// Given
		final UUID machineId = UUID.fromString( "988bb790-2212-4c8a-bdbf-8a5a95199c91" );
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		final JobRequest jobRequest = Mockito.mock( JobRequest.class );
		// When
		Mockito.when( this.machineRepository.findById( machineId ) ).thenReturn( Optional.of( machineEntity ) );
		Mockito.when( this.partRepository.findById( TEST_PART_ID ) ).thenReturn( Optional.of( partEntity ) );
		Mockito.doThrow( DimensinfinRuntimeException.class ).when( this.plasticConsumerManager ).subtractPlasticFromCoil(
				Mockito.any( PartEntity.class ),
				Mockito.anyInt() );
		// Exceptions
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.plasticConsumerManager );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			machineServiceV2.startBuild( machineId, jobRequest );
		} );
	}

	@Test
	public void startBuildPartNotFound() {
		// Given
		final UUID machineId = UUID.fromString( "988bb790-2212-4c8a-bdbf-8a5a95199c91" );
		final JobRequest jobRequest = new JobRequest.Builder()
				.withId( TEST_JOB_ID )
				.withPartId( TEST_PART_ID )
				.withCopies( TEST_JOB_PART_COPIES )
				.build();
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		// When
		Mockito.when( this.machineRepository.findById( machineId ) ).thenReturn( Optional.of( machineEntity ) );
		Mockito.when( this.partRepository.findById( TEST_PART_ID ) ).thenReturn( Optional.empty() );
		// Test
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository, this.plasticConsumerManager );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			machineServiceV2.startBuild( machineId, jobRequest );
		} );
	}
}
