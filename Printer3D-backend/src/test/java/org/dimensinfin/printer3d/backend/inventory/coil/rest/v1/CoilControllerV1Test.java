package org.dimensinfin.printer3d.backend.inventory.coil.rest.v1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

public class CoilControllerV1Test {

	private CoilServiceV1 coilServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.coilServiceV1 = Mockito.mock( CoilServiceV1.class );
	}

	@Test
	public void constructorContract() {
		final CoilControllerV1 coilControllerV1 = new CoilControllerV1( this.coilServiceV1 );
		Assertions.assertNotNull( coilControllerV1 );
	}

	@Test
	public void newCoil() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		// When
		Mockito.when( this.coilServiceV1.newCoil( Mockito.any( Coil.class ) ) ).thenReturn( coil );
		// Test
		final CoilControllerV1 coilControllerV1 = new CoilControllerV1( this.coilServiceV1 );
		final ResponseEntity<Coil> obtained = coilControllerV1.newCoil( coil );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertTrue( obtained.getBody().equals( coil ) );
	}
}
