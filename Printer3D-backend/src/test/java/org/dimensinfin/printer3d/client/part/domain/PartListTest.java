package org.dimensinfin.printer3d.client.part.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.client.domain.PartList;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartListConstants.TEST_PARTLIST_COUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartListConstants.TEST_PARTLIST_PARTLIST;

public class PartListTest {
	@Test
	public void buildContract() {
		final PartList partList = new PartList.Builder()
				.withCount( TEST_PARTLIST_COUNT )
				.withPartList( TEST_PARTLIST_PARTLIST )
				.build();
		Assertions.assertNotNull( partList );
		new PartList.Builder()
				.withCount( null )
				.withPartList( TEST_PARTLIST_PARTLIST )
				.build();
		new PartList.Builder()
				.withCount( TEST_PARTLIST_COUNT )
				.withPartList( null )
				.build();
		new PartList.Builder()
				.withPartList( TEST_PARTLIST_PARTLIST )
				.build();
		new PartList.Builder()
				.withCount( TEST_PARTLIST_COUNT )
				.build();
		new PartList.Builder()
				.build();
	}

	@Test
	public void getterContract() {
		// Given
		final List<Part> partList = new ArrayList<>();
		partList.add( new Part() );
		partList.add( new Part() );
		final PartList partListEntity = new PartList.Builder()
				.withCount( partList.size())
				.withPartList( partList )
				.build();
		// Assertions
		Assertions.assertEquals( 2, partListEntity.getCount() );
		Assertions.assertEquals( partList.size(), partListEntity.getCount() );
		Assertions.assertEquals( partList.size(), partListEntity.getParts().size() );
	}
}
