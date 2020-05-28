package org.dimensinfin.printer3d.backend.inventory.roll.rest.v1;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.RollRepository;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RollConstants.TEST_ROLL_ID;

public class RollServiceV1Test {

	private RollRepository rollRepository;

	@BeforeEach
	public void beforeEach() {
		this.rollRepository = Mockito.mock( RollRepository.class );
	}

	@Test
	public void constructorContract() {
		final RollServiceV1 rollServiceV1 = new RollServiceV1( this.rollRepository );
		Assertions.assertNotNull( rollServiceV1 );
	}

	@Test
	public void newRoll() {
		// Given
		final Roll roll = Mockito.mock( Roll.class );
		// When
		Mockito.when( roll.getId() ).thenReturn( TEST_ROLL_ID );
		Mockito.when( this.rollRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.rollRepository.save( Mockito.any( Roll.class ) ) ).thenReturn( roll );
		// Test
		final RollServiceV1 rollServiceV1 = new RollServiceV1( this.rollRepository );
		final Roll obtained = rollServiceV1.newRoll( roll );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_ROLL_ID, obtained.getId() );
	}

	@Test
	public void newRollException() {
		// Given
		final Roll roll = Mockito.mock( Roll.class );
		// When
		Mockito.when( roll.getId() ).thenReturn( TEST_ROLL_ID );
		Mockito.when( this.rollRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( roll ) );
		Mockito.when( this.rollRepository.save( Mockito.any( Roll.class ) ) ).thenReturn( roll );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final RollServiceV1 rollServiceV1 = new RollServiceV1( this.rollRepository );
			rollServiceV1.newRoll( roll );
		} );
	}
}
