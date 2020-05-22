package org.dimensinfin.printer3d.backend.part.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryException;
import org.dimensinfin.printer3d.backend.part.persistence.InventoryRepository;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartListConstants.TEST_PARTLIST_COUNT;

public class PartServiceSupportTest {
	private InventoryRepository inventoryRepository;

	@BeforeEach
	public void beforeEach() {
		this.inventoryRepository = Mockito.mock( InventoryRepository.class );
	}

	@Test
	public void constructorContract() {
		final PartServiceSupport partServiceSupport = new PartServiceSupport( this.inventoryRepository );
		Assertions.assertNotNull( partServiceSupport );
	}

	@Test
	public void deleteAllParts() {
		// When
		Mockito.when( this.inventoryRepository.count() ).thenReturn( (long) TEST_PARTLIST_COUNT );
		Mockito.doNothing().when( this.inventoryRepository ).deleteAll();
		// Test
		final PartServiceSupport partServiceSupport = new PartServiceSupport( this.inventoryRepository );
		final CountResponse obtained = partServiceSupport.deleteAllParts();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_PARTLIST_COUNT, obtained.getRecords() );
	}

	@Test
	public void deleteAllPartsException() {
		// When
		Mockito.doThrow( RuntimeException.class ).when( this.inventoryRepository ).deleteAll();
		// Exceptions
		Assertions.assertThrows( RepositoryException.class, () -> {
			final PartServiceSupport partServiceSupport = new PartServiceSupport( this.inventoryRepository );
			partServiceSupport.deleteAllParts();
		} );
	}
}
