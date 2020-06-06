package org.dimensinfin.printer3d.backend.support;

import java.time.LocalDateTime;
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

	public static class CoilConstants {
		public static final UUID TEST_ROLL_ID = UUID.randomUUID();
		public static final String TEST_ROLL_MATERIAL = "PLA";
		public static final String TEST_ROLL_COLOR = "-COLOR-";
		public static final Integer TEST_ROLL_WEIGHT = 750;
	}

	public static class FinishingConstants {
		public static final String TEST_FINISHING_MATERIAL = "-TEST_FINISHING_MATERIAL-";
	}
	public static class SetupRequest {
		public static final String TEST_SETUPREQUEST_MACHINE_LABEL = "-TEST_MACHINE_LABEL-";
		public static final UUID TEST_SETUPREQUEST_PART_ID = UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" );
		public static final String TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE = "2020-06-03T13:31:20.929896";
		public static final Integer TEST_SETUPREQUEST_PART_INSTANCES_COUNT = 2;
	}
	public static class MachineConstants{
		public static final UUID TEST_MACHINE_ID = UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" );
		public static final String TEST_MACHINE_LABEL = "-TEST_MACHINE_LABEL-";
		public static final String TEST_MACHINE_MODEL = "-TEST_MACHINE_MODEL-";
		public static final String TEST_MACHINE_CHARACTERISTICS = "-TEST_MACHINE_CHARACTERISTICS-";
		public static final UUID TEST_MACHINE_CURRENTJOBPARTID = UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" );
		public static final Integer TEST_MACHINE_CURRENTPARTINSTANCES = 8;
		public static final LocalDateTime TEST_MACHINE_JOBINSTALLMENTDATE = LocalDateTime.now();
	}
	public static class BuildRecordConstants{
		public static final LocalDateTime TEST_BUILDRECORD_JOBINSTALLMENTDATE = LocalDateTime.now();
		public static final Integer TEST_BUILDRECORD_PARTCOPIES = 8;
	}
}
