package org.dimensinfin.printer3d.infrastructure.adapters.inbound.rest.inventoryv2;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.application.ports.inbound.InventoryV2UseCases;
import org.dimensinfin.printer3d.application.usecases.GetPartsUseCase;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public class PartControllerV2Test {

	private InventoryV2UseCases inventoryV2UseCases;

	@BeforeEach
	public void beforeEach() {
		this.inventoryV2UseCases = Mockito.mock( InventoryV2UseCases.class );
	}

	@Test
	public void constructorContract() {
		final PartControllerV2 partControllerV2 = new PartControllerV2( this.inventoryV2UseCases );
		Assertions.assertNotNull( partControllerV2 );
	}

	@Test
	public void getParts() {
		// Given
		final GetPartsUseCase useCase=Mockito.mock( GetPartsUseCase.class);
		final Part part = Mockito.mock( Part.class );
		final List<Part> partList = new ArrayList<>();
		partList.add( part );
		partList.add( part );
		partList.add( part );
		partList.add( part );
		// When
		Mockito.when( this.inventoryV2UseCases.getPartsUseCaseV2() ).thenReturn( useCase );
		Mockito.when( useCase.execute() ).thenReturn( partList );
		// Test
		final PartControllerV2 partControllerV2 = new PartControllerV2( this.inventoryV2UseCases );
		final ResponseEntity<List<Part>> obtained = partControllerV2.getPartsV2();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 4, obtained.getBody().size() );
	}
}