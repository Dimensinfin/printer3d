package org.dimensinfin.printer3d.backend.inventory.part.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

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

public class PartServiceV1Test {
	private PartEntity testPart;
	private PartEntity testPartNotActive;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.testPart = new PartEntity.Builder()
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
				.withActive( true )
				.build();
		this.testPartNotActive = new PartEntity.Builder()
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
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		Assertions.assertNotNull( serviceV1 );
	}

	@Test
	public void getPartsActivesOnly() {
		// Given
		final List<PartEntity> partList = new ArrayList<>();
		partList.add( this.testPart );
		partList.add( this.testPartNotActive );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( partList );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		final PartList obtained = serviceV1.getParts( true );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 1, obtained.getCount() );
		Assertions.assertEquals( 1, obtained.getParts().size() );
	}

	@Test
	public void newPart() {
		// Given
		final Part part = new Part.Builder()
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
		// When
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.partRepository.save( Mockito.any( PartEntity.class ) ) ).thenReturn( partEntity );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		final Part obtained = serviceV1.newPart( part );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( obtained, part );
	}

	@Test
	public void newPartAlreadyPresent() {
		// Given
		final Part part = new Part.Builder()
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
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		// Tests
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> serviceV1.newPart( part ) );
	}

	@Test
	public void newPartException() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final PartEntity partEntity = Mockito.mock( PartEntity.class );
		// When
		Mockito.when( part.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		// Tests
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> serviceV1.newPart( part ) );
	}

	@Test
	public void newPartExceptionDataIntegrityViolatio() {
		// Given
		final Part part = new Part.Builder()
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
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.partRepository.save( Mockito.any( PartEntity.class ) ) ).thenThrow( DataIntegrityViolationException.class );
		// Tests
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> serviceV1.newPart( part ) );
	}

	@Test
	public void newPartKeyFieldsAlreadyUsed() {
		// Given
		final Part part = new Part.Builder()
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
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		Mockito.when( this.partRepository.save( partEntity ) ).thenThrow( DataIntegrityViolationException.class );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
			serviceV1.newPart( part );
		} );
	}

	@Test
	public void partsListAll() {
		// Given
		final List<PartEntity> partList = new ArrayList<>();
		partList.add( this.testPart );
		partList.add( this.testPartNotActive );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( partList );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		final PartList obtained = serviceV1.getParts( false );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getCount() );
		Assertions.assertEquals( 2, obtained.getParts().size() );
	}

	@Test
	public void updateGroupPart() {
		// Given
		final String MODIFIED = "MODIFIED";
		final UpdateGroupPartRequest updateData = new UpdateGroupPartRequest.Builder()
				.withLabel( TEST_PART_LABEL + MODIFIED )
				.withDescription( TEST_PART_DESCRIPTION + MODIFIED )
				.withBuildTime( TEST_PART_BUILD_TIME + 10 )
				.withWeight( TEST_PART_WEIGHT + 10 )
				.withImagePath( TEST_PART_IMAGE_PATH + MODIFIED )
				.withModelPath( TEST_PART_MODEL_PATH + MODIFIED )
				.build();
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
		final List<PartEntity> partList = new ArrayList<>();
		partList.add( partEntity );
		partList.add( partEntity );
		// When
		Mockito.when( this.partRepository.findByLabel( Mockito.anyString() ) ).thenReturn( partList );
		// Tests
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		final CounterResponse obtained = serviceV1.updateGroupPart( updateData );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getRecords() );
	}

	@Test
	public void updatePart() {
		// Given
		final Part part = new Part.Builder()
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
		// When
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		Mockito.when( this.partRepository.save( Mockito.any( PartEntity.class ) ) ).thenReturn( partEntity );
		//		Mockito.when( part.getId() ).thenReturn( UUID.randomUUID() );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		final Part obtained = serviceV1.updatePart( part );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( obtained, part );
	}

	@Test
	public void updatePartException() {
		// Given
		final Part part = Mockito.mock( Part.class );
		// When
		Mockito.when( part.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			// Test
			final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
			serviceV1.updatePart( part );
		} );
	}
}
