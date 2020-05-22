package org.dimensinfin.printer3d.backend.support;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dimensinfin.printer3d.backend.part.persistence.Part;

public class TestDataConstants {
	public static class PartListConstants {
		public static final Integer TEST_PARTLIST_COUNT = 8;
		public static final List<Part> TEST_PARTLIST_PARTLIST = new ArrayList<>();
	}

	public static class PartConstants {
		public static final UUID TEST_PART_ID = UUID.randomUUID();
		public static final String TEST_PART_LABEL = "-TEST_PART_LABEL-";
		public static final Float TEST_PART_COST = 0.76F;
		public static final Float TEST_PART_PRICE = 2.00F;
	}
}
