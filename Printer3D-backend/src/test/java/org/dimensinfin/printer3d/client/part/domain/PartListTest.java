package org.dimensinfin.printer3d.client.part.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartListConstants.TEST_PARTLIST_PARTLIST;

public class PartListTest {
	@Test
	public void buildContract() {
		final PartList partList = new PartList.Builder()
				.withPartList( TEST_PARTLIST_PARTLIST )
				.build();
		Assertions.assertNotNull( partList );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new PartList.Builder()
					.withPartList( null )
					.build();
		} );
	}

	@Test
	public void getterContract() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final List<Part> partList = new ArrayList<>();
		partList.add( part );
		partList.add( part );
		final PartList partListContainer = new PartList.Builder()
				.withPartList( partList )
				.build();
		// Assertions
		Assertions.assertEquals( 2, partListContainer.getCount() );
		Assertions.assertEquals( 2, partListContainer.getCount() );
		Assertions.assertEquals( 2, partListContainer.getParts().size() );
	}
}
