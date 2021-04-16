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
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartToPartEntityConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartUpdater;
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
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PROJECT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_WEIGHT;

public class PartServiceV1Test {
	private static final String UPDATE = "-UPDATE";
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
		final PartEntity partEntity = new PartToPartEntityConverter().convert( part );
		// When
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.partRepository.save( Mockito.any( PartEntity.class ) ) ).thenReturn( partEntity );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		final Part obtained = serviceV1.newPart( part );
		// Assertions
		Assertions.assertNotNull( obtained );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_PART_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_PART_LABEL, obtained.getLabel() );
		Assertions.assertEquals( "<DEFAULT>", obtained.getProject() );
		Assertions.assertEquals( TEST_PART_DESCRIPTION, obtained.getDescription() );
		Assertions.assertEquals( TEST_PART_MATERIAL, obtained.getMaterial() );
		Assertions.assertEquals( TEST_PART_COLOR, obtained.getColor() );
		Assertions.assertEquals( TEST_PART_WEIGHT, obtained.getWeight() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME, obtained.getBuildTime() );
		Assertions.assertEquals( TEST_PART_COST, obtained.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE, obtained.getPrice() );
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL, obtained.getStockLevel() );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE, obtained.getStockAvailable() );
		Assertions.assertEquals( TEST_PART_IMAGE_PATH, obtained.getImagePath() );
		Assertions.assertEquals( TEST_PART_MODEL_PATH, obtained.getModelPath() );
		Assertions.assertFalse( obtained.isActive() );
		Assertions.assertFalse( obtained.isUnavailable() );
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
	public void newPartDefaults() {
		// Given
		final Part part = new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.build();
		final PartEntity partEntity = new PartToPartEntityConverter().convert( part );
		// When
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.partRepository.save( Mockito.any( PartEntity.class ) ) ).thenReturn( partEntity );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		final Part obtained = serviceV1.newPart( part );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_PART_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_PART_LABEL, obtained.getLabel() );
		Assertions.assertEquals( "<DEFAULT>", obtained.getProject() );
		Assertions.assertNull( obtained.getDescription() );
		Assertions.assertEquals( TEST_PART_MATERIAL, obtained.getMaterial() );
		Assertions.assertEquals( TEST_PART_COLOR, obtained.getColor() );
		Assertions.assertEquals( 1, obtained.getWeight() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME, obtained.getBuildTime() );
		Assertions.assertEquals( TEST_PART_COST, obtained.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE, obtained.getPrice() );
		Assertions.assertEquals( 0, obtained.getStockLevel() );
		Assertions.assertEquals( 0, obtained.getStockAvailable() );
		Assertions.assertNull( obtained.getImagePath() );
		Assertions.assertNull( obtained.getModelPath() );
		Assertions.assertTrue( obtained.isActive() );
		Assertions.assertFalse( obtained.isUnavailable() );
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
		final Part updatePart = new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION + UPDATE )
				.withProject( TEST_PART_PROJECT + UPDATE )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withStockLevel( TEST_PART_STOCK_LEVEL + 10 )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE - 5 )
				.withCost( TEST_PART_COST + 3 )
				.withPrice( TEST_PART_PRICE - 1 )
				.withActive( true )
				.build();
		final PartEntity updatedEntity = new PartUpdater( partEntity ).update( updatePart );
		// When
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( partEntity ) );
		Mockito.when( this.partRepository.save( Mockito.any( PartEntity.class ) ) ).thenReturn( updatedEntity );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.partRepository );
		final Part obtained = serviceV1.updatePart( updatePart );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_PART_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_PART_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_PART_PROJECT + UPDATE, obtained.getProject() );
		Assertions.assertEquals( TEST_PART_DESCRIPTION + UPDATE, obtained.getDescription() );
		Assertions.assertEquals( TEST_PART_MATERIAL, obtained.getMaterial() );
		Assertions.assertEquals( TEST_PART_COLOR, obtained.getColor() );
		Assertions.assertEquals( TEST_PART_WEIGHT, obtained.getWeight() );
		Assertions.assertEquals( TEST_PART_BUILD_TIME, obtained.getBuildTime() );
		Assertions.assertEquals( TEST_PART_COST + 3, obtained.getCost() );
		Assertions.assertEquals( TEST_PART_PRICE - 1, obtained.getPrice() );
		Assertions.assertEquals( TEST_PART_STOCK_LEVEL + 10, obtained.getStockLevel() );
		Assertions.assertEquals( TEST_PART_STOCK_AVAILABLE - 5, obtained.getStockAvailable() );
		Assertions.assertEquals( TEST_PART_IMAGE_PATH, obtained.getImagePath() );
		Assertions.assertEquals( TEST_PART_MODEL_PATH, obtained.getModelPath() );
		Assertions.assertTrue( obtained.isActive() );
		Assertions.assertFalse( obtained.isUnavailable() );
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
