package org.dimensinfin.printer3d.backend.inventory.roll.rest.v1;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.CoilRepository;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RollConstants.TEST_ROLL_ID;

public class CoilServiceV1Test {

	private CoilRepository coilRepository;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
	}

	@Test
	public void constructorContract() {
		final RollServiceV1 rollServiceV1 = new RollServiceV1( this.coilRepository );
		Assertions.assertNotNull( rollServiceV1 );
	}

	@Test
	public void newRoll() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		// When
		Mockito.when( coil.getId() ).thenReturn( TEST_ROLL_ID );
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.coilRepository.save( Mockito.any( Coil.class ) ) ).thenReturn( coil );
		// Test
		final RollServiceV1 rollServiceV1 = new RollServiceV1( this.coilRepository );
		final Coil obtained = rollServiceV1.newRoll( coil );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_ROLL_ID, obtained.getId() );
	}

	@Test
	public void newRollException() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		// When
		Mockito.when( coil.getId() ).thenReturn( TEST_ROLL_ID );
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( coil ) );
		Mockito.when( this.coilRepository.save( Mockito.any( Coil.class ) ) ).thenReturn( coil );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final RollServiceV1 rollServiceV1 = new RollServiceV1( this.coilRepository );
			rollServiceV1.newRoll( coil );
		} );
	}
}
