package org.dimensinfin.printer3d.backend.production.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;

public class StockManagerTest {

	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void clean() {
		// Given
		final UUID partId = UUID.fromString( "dad58f5e-af01-45da-819f-97ca3f354fa1" );
		// Test
		final StockManager stockManager = new StockManager( this.partRepository );
		stockManager.clean();
		final float obtained = stockManager.getPrice( partId );
		// Assertions
		Assertions.assertEquals( 0.0, obtained, 0.01 );
	}

	@Test
	public void constructorContract() {
		final StockManager stockManager = new StockManager( this.partRepository );
		Assertions.assertNotNull( stockManager );
	}

	@Test
	public void getPrice() {
		// Given
		final UUID partId = UUID.fromString( "dad58f5e-af01-45da-819f-97ca3f354fa1" );
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		final List<PartEntity> testPartList = new ArrayList<>();
		testPartList.add( partEntity );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( testPartList );
		Mockito.when( partEntity.getId() ).thenReturn( partId );
		Mockito.when( partEntity.getPrice() ).thenReturn( TEST_PART_PRICE );
		// Test
		final StockManager stockManager = new StockManager( this.partRepository );
		stockManager.startStock();
		final float obtained = stockManager.getPrice( partId );
		// Assertions
		Assertions.assertEquals( TEST_PART_PRICE, obtained, 0.01 );
	}

	@Test
	public void getStock() {
		// Given
		final UUID partId = UUID.fromString( "dad58f5e-af01-45da-819f-97ca3f354fa1" );
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		final List<PartEntity> testPartList = new ArrayList<>();
		testPartList.add( partEntity );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( testPartList );
		Mockito.when( partEntity.getId() ).thenReturn( partId );
		Mockito.when( partEntity.getStockAvailable() ).thenReturn( TEST_PART_STOCK_AVAILABLE );
		// Test
		final StockManager stockManager = new StockManager( this.partRepository );
		stockManager.startStock();
		final int obtained = stockManager.getStock( partId );
		// Assertions
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE, obtained );
	}

	@Test
	public void getStockException() {
		// Given
		final UUID partId = UUID.fromString( "dad58f5e-af01-45da-819f-97ca3f354fa1" );
		// Test
		final StockManager stockManager = new StockManager( this.partRepository );
		stockManager.startStock();
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> stockManager.getStock( partId ) );
	}

	@Test
	public void getStockIterator() {
		Assertions.assertNotNull( new StockManager( this.partRepository ).getStockIterator() );
	}

	@Test
	public void getStockLevel() {
		// Given
		final UUID partId = UUID.fromString( "dad58f5e-af01-45da-819f-97ca3f354fa1" );
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		final List<PartEntity> testPartList = new ArrayList<>();
		testPartList.add( partEntity );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( testPartList );
		Mockito.when( partEntity.getId() ).thenReturn( partId );
		Mockito.when( partEntity.getStockLevel() ).thenReturn( TEST_PART_STOCK_LEVEL );
		// Test
		final StockManager stockManager = new StockManager( this.partRepository );
		stockManager.startStock();
		final int obtained = stockManager.getStockLevel( partId );
		// Assertions
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL, obtained );
	}

	@Test
	public void getStockLevelException() {
		// Given
		final UUID partId = UUID.fromString( "dad58f5e-af01-45da-819f-97ca3f354fa1" );
		// Test
		final StockManager stockManager = new StockManager( this.partRepository );
		stockManager.startStock();
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> stockManager.getStockLevel( partId ) );
	}

	@Test
	public void minus() {
		// Given
		final UUID partId = UUID.fromString( "dad58f5e-af01-45da-819f-97ca3f354fa1" );
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		final List<PartEntity> testPartList = new ArrayList<>();
		testPartList.add( partEntity );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( testPartList );
		Mockito.when( partEntity.getId() ).thenReturn( partId );
		Mockito.when( partEntity.getStockAvailable() ).thenReturn( TEST_PART_STOCK_AVAILABLE );
		// Test
		final StockManager stockManager = new StockManager( this.partRepository );
		stockManager.startStock();
		int obtained = stockManager.getStock( partId );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE, obtained );
		obtained = stockManager.minus( partId, 2 );
		// Assertions
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE - 2, obtained );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE - 2, stockManager.getStock( partId ) );
	}

	@Test
	public void minusException() {
		// Given
		final UUID partId = UUID.fromString( "dad58f5e-af01-45da-819f-97ca3f354fa1" );
		// Test
		final StockManager stockManager = new StockManager( this.partRepository );
		stockManager.startStock();
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> stockManager.minus( partId, 1 ) );
	}

	@Test
	public void startStock() {
		// Given
		final UUID partId = UUID.fromString( "dad58f5e-af01-45da-819f-97ca3f354fa1" );
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		final List<PartEntity> testPartList = new ArrayList<>();
		testPartList.add( partEntity );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( testPartList );
		Mockito.when( partEntity.getId() ).thenReturn( partId );
		Mockito.when( partEntity.getStockAvailable() ).thenReturn( TEST_PART_STOCK_AVAILABLE );
		// Test
		final StockManager stockManager = new StockManager( this.partRepository );
		stockManager.startStock();
		final int obtained = stockManager.getStock( partId );
		// Assertions
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE, obtained );
	}
}
