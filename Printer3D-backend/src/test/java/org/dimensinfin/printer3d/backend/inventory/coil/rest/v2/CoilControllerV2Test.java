package org.dimensinfin.printer3d.backend.inventory.coil.rest.v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;

public class CoilControllerV2Test {

	private CoilServiceV2 coilServiceV2;

	@BeforeEach
	public void beforeEach() {
		this.coilServiceV2 = Mockito.mock( CoilServiceV2.class );
	}

	@Test
	public void constructorContract() {
		final CoilControllerV2 coilControllerV2 = new CoilControllerV2( this.coilServiceV2 );
		Assertions.assertNotNull( coilControllerV2 );
	}

	@Test
	public void getCoils() {
		// Given
		final List<Coil> coilList = new ArrayList<>();
		final Coil coil1 = Mockito.mock( Coil.class );
		coilList.add( coil1 );
		final Coil coil2 = Mockito.mock( Coil.class );
		coilList.add( coil2 );
		// When
		Mockito.when( this.coilServiceV2.getCoils() ).thenReturn( coilList );
		// Test
		final CoilControllerV2 coilControllerV2 = new CoilControllerV2( this.coilServiceV2 );
		final ResponseEntity<List<Coil>> obtained = coilControllerV2.getCoils();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 2, obtained.getBody().size() );
	}
}