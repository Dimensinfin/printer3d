package org.dimensinfin.printer3d.backend.inventory.part.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartListConstants.TEST_PARTLIST_COUNT;

public class PartServiceSupportTest {
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final PartServiceSupport partServiceSupport = new PartServiceSupport( this.partRepository );
		Assertions.assertNotNull( partServiceSupport );
	}

	@Test
	public void deleteAllParts() {
		// When
		Mockito.when( this.partRepository.count() ).thenReturn( (long) TEST_PARTLIST_COUNT );
		Mockito.doNothing().when( this.partRepository ).deleteAll();
		// Test
		final PartServiceSupport partServiceSupport = new PartServiceSupport( this.partRepository );
		final CounterResponse obtained = partServiceSupport.deleteAllParts();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_PARTLIST_COUNT, obtained.getRecords() );
	}

	@Test
	public void deleteAllPartsException() {
		// When
		Mockito.doThrow( RuntimeException.class ).when( this.partRepository ).deleteAll();
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final PartServiceSupport partServiceSupport = new PartServiceSupport( this.partRepository );
			partServiceSupport.deleteAllParts();
		} );
	}
}
