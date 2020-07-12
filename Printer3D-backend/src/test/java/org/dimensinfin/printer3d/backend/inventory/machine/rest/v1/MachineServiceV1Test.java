package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_ID;
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

public class MachineServiceV1Test {

	private MachineRepository machineRepository;
	private PartRepository partRepository;
	private JobRepository jobRepository;

	@BeforeEach
	public void beforeEach() {
		this.machineRepository = Mockito.mock( MachineRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
		this.jobRepository = Mockito.mock( JobRepository.class );
	}

	@Test
	public void cancelBuild() {
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		// When
		Mockito.when( this.machineRepository.findById( TEST_MACHINE_ID ) ).thenReturn( Optional.of( machineEntity ) );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		Mockito.when( this.machineRepository.save( Mockito.any( MachineEntity.class ) ) ).thenReturn( machineEntity );
		Mockito.when( machineEntity.getId() ).thenReturn( TEST_MACHINE_ID );
		Mockito.when( machineEntity.getLabel() ).thenReturn( "TEST_MACHINE_LABEL" );
		Mockito.when( machineEntity.getModel() ).thenReturn( "TEST_MACHINE_MODEL" );
		Mockito.when( machineEntity.getCharacteristics() ).thenReturn( "TEST_MACHINE_CHARACTERISTICS" );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( null );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( 1 );
		Mockito.when( machineEntity.getJobInstallmentDate() ).thenReturn( null );
		Mockito.when( machineEntity.clearJob() ).thenReturn( machineEntity );
		// Test
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository, this.jobRepository );
		final Machine obtained = machineServiceV1.cancelBuild( TEST_MACHINE_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNull( obtained.getCurrentJobPart() );
		Assertions.assertNull( obtained.getJobInstallmentDate() );
		Assertions.assertEquals( 1, obtained.getCurrentPartInstances() );
	}

	@Test
	public void cancelBuildNotFound() {
		// When
		Mockito.when( this.machineRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository, this.jobRepository );
			machineServiceV1.cancelBuild( TEST_MACHINE_ID );
		} );
	}

//	@Test
	public void completeBuild() {
		// Given
		final UUID partId = UUID.randomUUID();
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final PartEntity partEntity = new PartEntity.Builder()
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
		// When
		Mockito.when( this.machineRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( machineEntity ) );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( partId );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( 2 );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		Mockito.when( this.machineRepository.save( Mockito.any( MachineEntity.class ) ) ).thenReturn( machineEntity );
		Mockito.when( machineEntity.getId() ).thenReturn( TEST_MACHINE_ID );
		Mockito.when( machineEntity.getLabel() ).thenReturn( "TEST_MACHINE_LABEL" );
		Mockito.when( machineEntity.getModel() ).thenReturn( "TEST_MACHINE_MODEL" );
		Mockito.when( machineEntity.getCharacteristics() ).thenReturn( "TEST_MACHINE_CHARACTERISTICS" );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( null );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( 1 );
		Mockito.when( machineEntity.getJobInstallmentDate() ).thenReturn( null );
		Mockito.when( machineEntity.clearJob() ).thenReturn( machineEntity );
		// Test
		final UUID id = machineEntity.getCurrentJobPartId();

		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository, this.jobRepository );
		final Machine obtained = machineServiceV1.completeBuild( partId );
		// Assertions
		Assertions.assertNotNull( obtained );
	}

	@Test
	public void constructorContract() {
		final MachineServiceV1 machineServiceV1 = new MachineServiceV1( this.machineRepository, this.partRepository, this.jobRepository );
		Assertions.assertNotNull( machineServiceV1 );
	}
}
