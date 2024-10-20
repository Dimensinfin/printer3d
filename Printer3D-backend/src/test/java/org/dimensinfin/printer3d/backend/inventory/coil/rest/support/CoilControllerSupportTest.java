package org.dimensinfin.printer3d.backend.inventory.coil.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.coil.rest.v2.CoilServiceV2;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;

public class CoilControllerSupportTest {
	private CoilRepository coilRepository;
	private CoilServiceV2 coilServiceV2;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
		this.coilServiceV2 = Mockito.mock( CoilServiceV2.class );
	}

	@Test
	public void constructorContract() {
		final CoilControllerSupport coilControllerSupport = new CoilControllerSupport( this.coilRepository, this.coilServiceV2 );
		Assertions.assertNotNull( coilControllerSupport );
	}

	@Test
	public void deleteAllRolls() {
		// Given
		final Long TEST_RECORD_COUNT = 6L;
		// When
		Mockito.when( this.coilRepository.count() ).thenReturn( TEST_RECORD_COUNT );
		// Test
		final CoilControllerSupport coilControllerSupport = new CoilControllerSupport( this.coilRepository, this.coilServiceV2 );
		final ResponseEntity<CounterResponse> obtained = coilControllerSupport.deleteAllCoils();
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
		final CoilControllerSupport coilControllerSupport = new CoilControllerSupport( this.coilRepository, this.coilServiceV2 );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			coilControllerSupport.deleteAllCoils();
		} );
	}
}
