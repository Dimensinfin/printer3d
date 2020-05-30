package org.dimensinfin.printer3d.backend.inventory.coil.rest.v1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;

public class CoilControllerV1Test {

	private CoilServiceV1 coilviceV1;

	@BeforeEach
	public void beforeEach() {
		this.coilviceV1 = Mockito.mock( CoilServiceV1.class);
	}

	@Test
	public void constructorContract() {
		final RollControllerV1 rollControllerV1=new RollControllerV1(this.coilviceV1 );
		Assertions.assertNotNull(rollControllerV1);
	}

	@Test
	public void newRoll() {
		// Given
		final Coil coil = Mockito.mock( Coil.class);
		// When
		Mockito.when( this.coilviceV1.newCoil(Mockito.any( Coil.class)) ).thenReturn( coil );
		// Test
		final RollControllerV1 rollControllerV1=new RollControllerV1(this.coilviceV1 );
		final ResponseEntity<Coil> obtained = rollControllerV1.newRoll( coil );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertTrue( obtained.getBody().equals( coil ) );
	}
}
