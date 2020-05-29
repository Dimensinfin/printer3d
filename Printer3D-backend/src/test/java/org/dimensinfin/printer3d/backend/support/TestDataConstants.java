package org.dimensinfin.printer3d.backend.support;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;

public class TestDataConstants {
	public static class PartListConstants {
		public static final Integer TEST_PARTLIST_COUNT = 8;
		public static final List<Part> TEST_PARTLIST_PARTLIST = new ArrayList<>();
	}

	public static class PartConstants {
		public static final UUID TEST_PART_ID = UUID.randomUUID();
		public static final String TEST_PART_LABEL = "-TEST_PART_LABEL-";
		public static final String TEST_PART_DESCRIPTION = "-TEST_PART_DESCRIPTION-";
		public static final String TEST_PART_MATERIAL = "PLA";
		public static final String TEST_PART_COLOR_CODE = "VERDE-T";
		public static final Integer TEST_PART_BUILD_TIME = 60;
		public static final Float TEST_PART_COST = 0.76F;
		public static final Float TEST_PART_PRICE = 2.00F;
		public static final Integer TEST_PART_STOCK_LEVEL = 4;
		public static final Integer TEST_PART_STOCK_AVAILABLE = 4;
		public static final String TEST_PART_IMAGE_PATH = "https://ibb.co/3dGbsRh";
		public static final String TEST_PART_MODEL_PATH = "pieza3.STL";
	}

	public static class RollConstants {
		public static final UUID TEST_ROLL_ID = UUID.randomUUID();
		public static final String TEST_ROLL_MATERIAL = "PLA";
		public static final String TEST_ROLL_COLOR = "-COLOR-";
		public static final Integer TEST_ROLL_WEIGHT = 750;
	}
}
