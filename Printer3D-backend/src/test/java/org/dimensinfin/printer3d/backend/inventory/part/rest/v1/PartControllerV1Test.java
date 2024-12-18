package org.dimensinfin.printer3d.backend.inventory.part.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

public class PartControllerV1Test {

	private PartServiceV1 partServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.partServiceV1 = Mockito.mock( PartServiceV1.class );
	}

	@Test
	public void constructorContract() {
		final PartControllerV1 controllerV1 = new PartControllerV1( this.partServiceV1 );
		Assertions.assertNotNull( controllerV1 );
	}

	@Test
	public void newPart() {
		// Given
		final Part part = Mockito.mock( Part.class );
		// When
		Mockito.when( this.partServiceV1.newPart( Mockito.any( Part.class ) ) ).thenReturn( part );
		// Test
		final PartControllerV1 controllerV1 = new PartControllerV1( this.partServiceV1 );
		final ResponseEntity<Part> obtained = controllerV1.newPart( part );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertTrue( obtained.getBody().equals( part ) );
	}

	@Test
	public void partsList() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final List<Part> partList = new ArrayList<>();
		partList.add( part );
		partList.add( part );
		partList.add( part );
		partList.add( part );
		final PartList partListContainer = new PartList.Builder()
				.withPartList( partList )
				.build();
		// When
		Mockito.when( this.partServiceV1.getParts( true ) ).thenReturn( partListContainer );
		// Test
		final PartControllerV1 controllerV1 = new PartControllerV1( this.partServiceV1 );
		final ResponseEntity<PartList> obtained = controllerV1.getParts( Optional.of( true ) );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 4, obtained.getBody().getCount() );
		Assertions.assertEquals( 4, obtained.getBody().getParts().size() );
	}

	@Test
	public void updateGroupPart() {
		// Given
		final UpdateGroupPartRequest updateData = Mockito.mock( UpdateGroupPartRequest.class );
		final CounterResponse counter = new CounterResponse.Builder().withRecords( 1 ).build();
		// When
		Mockito.when( this.partServiceV1.updateGroupPart( Mockito.any( UpdateGroupPartRequest.class ) ) ).thenReturn( counter );
		// Test
		final PartControllerV1 controllerV1 = new PartControllerV1( this.partServiceV1 );
		final ResponseEntity<CounterResponse> obtained = controllerV1.updateGroupPart( updateData );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().getRecords() );
	}

	@Test
	public void updatePart() {
		// Given
		final Part part = Mockito.mock( Part.class );
		// When
		Mockito.when( this.partServiceV1.updatePart( Mockito.any( Part.class ) ) ).thenReturn( part );
		// Test
		final PartControllerV1 controllerV1 = new PartControllerV1( this.partServiceV1 );
		final ResponseEntity<Part> obtained = controllerV1.updatePart( part );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertTrue( obtained.getBody().equals( part ) );
	}
}
