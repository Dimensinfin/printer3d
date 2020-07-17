package org.dimensinfin.printer3d.backend.inventory.coil.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;

public class CoilServiceV1Test {

	private CoilRepository coilRepository;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
	}

	@Test
	public void constructorContract() {
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		Assertions.assertNotNull( coilServiceV1 );
	}

	@Test
	public void getRolls() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		final List<Coil> coils = new ArrayList<>();
		coils.add( coil );
		coils.add( coil );
		coils.add( coil );
		// When
		Mockito.when( this.coilRepository.findAll() ).thenReturn( coils );
		// Test
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		final CoilList obtained = coilServiceV1.getCoils();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 3, obtained.getCount() );
		Assertions.assertEquals( 3, obtained.getCoils().size() );
	}

	@Test
	public void newRoll() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		// When
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.coilRepository.save( Mockito.any( Coil.class ) ) ).thenReturn( coil );
		// Test
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		final Coil obtained = coilServiceV1.newCoil( coil );
		// Assertions
		Assertions.assertNotNull( obtained );
	}

	@Test
	public void newRollFound() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		// When
		Mockito.when( coil.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( coil ) );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
			coilServiceV1.newCoil( coil );
		} );
	}
}
