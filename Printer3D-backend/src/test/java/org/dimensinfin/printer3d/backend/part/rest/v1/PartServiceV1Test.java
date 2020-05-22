package org.dimensinfin.printer3d.backend.part.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.part.persistence.InventoryRepository;
import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.client.part.domain.PartList;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;

public class PartServiceV1Test {

	private InventoryRepository inventoryRepository;

	@BeforeEach
	public void beforeEach() {
		this.inventoryRepository = Mockito.mock( InventoryRepository.class );
	}

	@Test
	public void constructorContract() {
		final PartServiceV1 serviceV1 = new PartServiceV1( this.inventoryRepository );
		Assertions.assertNotNull( serviceV1 );
	}

	@Test
	public void newPart() {
		// Given
		final Part part = Mockito.mock( Part.class );
		// When
		Mockito.when( this.inventoryRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		Mockito.when( this.inventoryRepository.save( Mockito.any( Part.class ) ) ).thenReturn( part );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.inventoryRepository );
		final Part obtained = serviceV1.newPart( part );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertTrue( part.equals( obtained ) );
	}

	@Test
	public void newPartException() {
		// Given
		final Part part = Mockito.mock( Part.class );
		// When
		Mockito.when( part.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( this.inventoryRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			// Test
			final PartServiceV1 serviceV1 = new PartServiceV1( this.inventoryRepository );
			serviceV1.newPart( part );
		} );
	}

	@Test
	public void partsListActive() {
		// Given
		final List<Part> partList = new ArrayList<>();
		partList.add( new Part() );
		partList.add( new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withActive( false )
				.build() );
		// When
		Mockito.when( this.inventoryRepository.findAll() ).thenReturn( partList );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.inventoryRepository );
		final PartList obtained = serviceV1.partsList( true );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getCount() );
		Assertions.assertEquals( 2, obtained.getParts().size() );
	}

	@Test
	public void partsListNotActive() {
		// Given
		final List<Part> partList = new ArrayList<>();
		partList.add( new Part() );
		partList.add( new Part.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withActive( false )
				.build() );
		// When
		Mockito.when( this.inventoryRepository.findAll() ).thenReturn( partList );
		// Test
		final PartServiceV1 serviceV1 = new PartServiceV1( this.inventoryRepository );
		final PartList obtained = serviceV1.partsList( false );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 1, obtained.getCount() );
		Assertions.assertEquals( 1, obtained.getParts().size() );
	}
}
