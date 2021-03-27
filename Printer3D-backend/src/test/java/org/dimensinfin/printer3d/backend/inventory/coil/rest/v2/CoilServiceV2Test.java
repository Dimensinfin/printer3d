package org.dimensinfin.printer3d.backend.inventory.coil.rest.v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;

public class CoilServiceV2Test {
	private CoilRepository coilRepository;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
	}

	@Test
	public void constructorContract() {
		final CoilServiceV2 coilServiceV2 = new CoilServiceV2( this.coilRepository );
		Assertions.assertNotNull( coilServiceV2 );
	}

	@Test
	public void getCoils() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		final List<Coil> coils = new ArrayList<>();
		coils.add( coil );
		coils.add( coil );
		coils.add( coil );
		// When
		Mockito.when( this.coilRepository.findAll() ).thenReturn( coils );
		// Test
		final CoilServiceV2 coilServiceV2 = new CoilServiceV2( this.coilRepository );
		final List<Coil> obtained = coilServiceV2.getCoils();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 3, obtained.size() );
	}
}