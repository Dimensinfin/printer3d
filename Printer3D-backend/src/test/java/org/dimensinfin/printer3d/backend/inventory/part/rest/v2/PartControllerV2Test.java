package org.dimensinfin.printer3d.backend.inventory.part.rest.v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public class PartControllerV2Test {

	private PartServiceV2 partServiceV2;

	@BeforeEach
	public void beforeEach() {
		this.partServiceV2 = Mockito.mock( PartServiceV2.class );
	}

	@Test
	public void constructorContract() {
		final PartControllerV2 partControllerV2 = new PartControllerV2( this.partServiceV2 );
		Assertions.assertNotNull( partControllerV2 );
	}

	@Test
	public void getParts() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final List<Part> partList = new ArrayList<>();
		partList.add( part );
		partList.add( part );
		partList.add( part );
		partList.add( part );
		// When
		Mockito.when( this.partServiceV2.getPartsV2() ).thenReturn( partList );
		// Test
		final PartControllerV2 partControllerV2 = new PartControllerV2( this.partServiceV2 );
		final ResponseEntity<List<Part>> obtained = partControllerV2.getParts();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 4, obtained.getBody().size() );
	}
}