package org.dimensinfin.printer3d.backend.part.rest.v1;

import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.client.part.domain.PartList;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartListConstants.TEST_PARTLIST_COUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartListConstants.TEST_PARTLIST_PARTLIST;

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
		final Part part = new Part();
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
		final HttpServletResponse response = Mockito.mock( HttpServletResponse.class );
		final PartList partList = new PartList.Builder()
				.withCount( TEST_PARTLIST_COUNT )
				.withPartList( TEST_PARTLIST_PARTLIST )
				.build();
		// When
		Mockito.when( this.partServiceV1.partsList( true ) ).thenReturn( partList );
		// Test
		final PartControllerV1 controllerV1 = new PartControllerV1( this.partServiceV1 );
		final ResponseEntity<PartList> obtained = controllerV1.partsList( response, Optional.of( true ) );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( TEST_PARTLIST_COUNT, obtained.getBody().getCount() );
		Assertions.assertEquals( TEST_PARTLIST_PARTLIST.size(), obtained.getBody().getParts().size() );
	}
}
