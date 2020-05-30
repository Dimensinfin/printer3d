package org.dimensinfin.printer3d.backend.inventory.finishing.rest.v1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.domain.Finishing;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;

public class FinishingsControllerV1Test {

	private FinishingsServiceV1 finishingsServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.finishingsServiceV1 = Mockito.mock( FinishingsServiceV1.class );
	}

	@Test
	public void constructorContract() {
		final FinishingsControllerV1 finishingsControllerV1 = new FinishingsControllerV1( this.finishingsServiceV1 );
		Assertions.assertNotNull( finishingsControllerV1 );
	}

	@Test
	public void getFinishings() {
		// Given
		final List<String> colors = new ArrayList<>();
		colors.add( "BLANCO" );
		colors.add( "ROJO" );
		final FinishingsResponse finishingResponse = new FinishingsResponse.Builder().build();
		finishingResponse.addFinishing( new Finishing.Builder()
				.withMaterial( "PLA" )
				.withColors( colors )
				.build() );
		// When
		Mockito.when( this.finishingsServiceV1.getFinishings() ).thenReturn( finishingResponse );
		// Test
		final FinishingsControllerV1 finishingsControllerV1 = new FinishingsControllerV1( this.finishingsServiceV1 );
		final ResponseEntity<FinishingsResponse> obtained = finishingsControllerV1.getFinishings();
		//Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().getMaterials().size() );
	}
}
