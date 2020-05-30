package org.dimensinfin.printer3d.backend.inventory.coil.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;

public class CoilControllerSupportTest {

	private CoilRepository coilRepository;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
	}

	@Test
	public void constructorContract() {
		final CoilControllerSupport coilControllerSupport = new CoilControllerSupport( this.coilRepository );
		Assertions.assertNotNull( coilControllerSupport );
	}

	@Test
	public void deleteAllRolls() {
		// Given
		final Long TEST_RECORD_COUNT = 6L;
		// When
		Mockito.when( this.coilRepository.count() ).thenReturn( TEST_RECORD_COUNT );
		// Test
		final CoilControllerSupport coilControllerSupport = new CoilControllerSupport( this.coilRepository );
		final ResponseEntity<CountResponse> obtained = coilControllerSupport.deleteAllRolls();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( TEST_RECORD_COUNT, obtained.getBody().getRecords() );
	}

	@Test
	public void deleteAllRollsException() {
		// Given
		final Long TEST_RECORD_COUNT = 6L;
		// When
		Mockito.when( this.coilRepository.count() ).thenThrow( RuntimeException.class );
		// Test
		final CoilControllerSupport coilControllerSupport = new CoilControllerSupport( this.coilRepository );
		// Exceptions
		Assertions.assertThrows( RepositoryException.class, () -> {
			coilControllerSupport.deleteAllRolls();
		} );
	}

	////	@Test
	//	public void testDeleteAllRolls() {
	//	}
}
