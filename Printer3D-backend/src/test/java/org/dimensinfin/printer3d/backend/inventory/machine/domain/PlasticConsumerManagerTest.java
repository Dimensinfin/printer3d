package org.dimensinfin.printer3d.backend.inventory.machine.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
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

public class PlasticConsumerManagerTest {

	private CoilRepository coilRepository;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
	}

	@Test
	public void constructorContract() {
		final PlasticConsumerManager plasticConsumerManager = new PlasticConsumerManager( this.coilRepository );
		Assertions.assertNotNull( plasticConsumerManager );
	}

	@Test
	public void subtractPlasticFromCoil() {
		// Given
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withWeight( TEST_PART_WEIGHT )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<CoilEntity> coilList = new ArrayList<>();
		coilList.add( new CoilEntity.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( TEST_PART_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_PART_COLOR )
				.withWeight( 800 )
				.build() );
		coilList.add( new CoilEntity.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( TEST_PART_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_PART_COLOR )
				.withWeight( 800 )
				.build() );
		final CoilEntity targetCoil = new CoilEntity.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( TEST_PART_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_PART_COLOR )
				.withWeight( 100 )
				.build();
		coilList.add( targetCoil );
		// When
		Mockito.when( this.coilRepository.findAll() ).thenReturn( coilList );
		// Test
		final PlasticConsumerManager plasticConsumerManager = new PlasticConsumerManager( this.coilRepository );
		plasticConsumerManager.subtractPlasticFromCoil( partEntity, 2 );
		// Assertions
		Assertions.assertEquals( 100 - (TEST_PART_WEIGHT * 2), targetCoil.getWeight() );
	}

	@Test
	public void subtractPlasticFromCoilNotFound() {
		// Given
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withWeight( TEST_PART_WEIGHT )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<CoilEntity> coilList = new ArrayList<>();
		coilList.add( new CoilEntity.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( TEST_PART_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "NOT-FOUND" )
				.withWeight( 800 )
				.build() );
		coilList.add( new CoilEntity.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( TEST_PART_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "NOT-FOUND" )
				.withWeight( 800 )
				.build() );
		// When
		Mockito.when( this.coilRepository.findAll() ).thenReturn( coilList );
		// Test
		final PlasticConsumerManager plasticConsumerManager = new PlasticConsumerManager( this.coilRepository );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			plasticConsumerManager.subtractPlasticFromCoil( partEntity, 2 );
		} );
	}
}
