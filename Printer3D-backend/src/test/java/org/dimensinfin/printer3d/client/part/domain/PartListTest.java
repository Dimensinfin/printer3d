package org.dimensinfin.printer3d.client.part.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.client.domain.PartList;

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
		final List<Part> partList = new ArrayList<>();
		partList.add( new Part() );
		partList.add( new Part() );
		final PartList partListContainer = new PartList.Builder()
				.withPartList( partList )
				.build();
		// Assertions
		Assertions.assertEquals( 2, partListContainer.getCount() );
		Assertions.assertEquals( 2, partListContainer.getCount() );
		Assertions.assertEquals( 2, partListContainer.getParts().size() );
	}
}
