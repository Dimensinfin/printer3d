package org.dimensinfin.printer3d.backend.support;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

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
		public static final String TEST_PART_COLOR = "VERDE-T";
		public static final Integer TEST_PART_WEIGHT = 4;
		public static final Integer TEST_PART_BUILD_TIME = 60;
		public static final Float TEST_PART_COST = 0.76F;
		public static final Float TEST_PART_PRICE = 2.00F;
		public static final Integer TEST_PART_STOCK_LEVEL = 4;
		public static final Integer TEST_PART_STOCK_AVAILABLE = 4;
		public static final String TEST_PART_IMAGE_PATH = "https://ibb.co/3dGbsRh";
		public static final String TEST_PART_MODEL_PATH = "pieza3.STL";
	}

	public static class CoilConstants {
		public static final UUID TEST_COIL_ID = UUID.fromString( "31009b73-fd2d-432d-9616-51cc2a1882be" );
		public static final String TEST_COIL_MATERIAL = "PLA";
		public static final String TEST_COIL_TRADE_MARK = "EOLAS";
		public static final String TEST_COIL_COLOR = "-COLOR-";
		public static final Integer TEST_COIL_WEIGHT = 750;
		public static final Boolean TEST_COIL_ACTIVE = false;
	}

	public static class FinishingConstants {
		public static final String TEST_FINISHING_MATERIAL = "-TEST_FINISHING_MATERIAL-";
	}

	public static class SetupRequest {
		public static final String TEST_SETUPREQUEST_MACHINE_LABEL = "-TEST_MACHINE_LABEL-";
		public static final UUID TEST_SETUPREQUEST_PART_ID = UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" );
		public static final String TEST_SETUPREQUEST_JOB_INSTALLMENT_DATE = "2020-06-06T21:54:00.226181Z";
		public static final Integer TEST_SETUPREQUEST_PART_INSTANCES_COUNT = 2;
	}

	public static class MachineConstants {
		public static final UUID TEST_MACHINE_ID = UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" );
		public static final String TEST_MACHINE_LABEL = "-TEST_MACHINE_LABEL-";
		public static final String TEST_MACHINE_MODEL = "-TEST_MACHINE_MODEL-";
		public static final String TEST_MACHINE_CHARACTERISTICS = "-TEST_MACHINE_CHARACTERISTICS-";
		public static final UUID TEST_MACHINE_CURRENTJOBPARTID = UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" );
		public static final Integer TEST_MACHINE_CURRENTPARTINSTANCES = 8;
		public static final Integer TEST_MACHINE_CURRENTPARTBUILDTIME = 45;
		public static final Instant TEST_MACHINE_JOBINSTALLMENTDATE = Instant.now();
	}

	public static class BuildRecordConstants {
		public static final Instant TEST_BUILDRECORD_JOBINSTALLMENTDATE = Instant.now();
		public static final Integer TEST_BUILDRECORD_PARTCOPIES = 8;
		public static final Integer TEST_BUILDRECORD_BUILD_TIME = 30;
	}

	public static class ModelConstants {
		public static final UUID TEST_MODEL_ID = UUID.fromString( "a2d2e8bc-ff65-4b61-9c60-57a3ad5e4230" );
		public static final String TEST_MODEL_LABEL = "-TEST_MODEL_LABEL-";
		public static final Float TEST_MODEL_PRICE = 6.0F;
		public static final Integer TEST_MODEL_STOCK_LEVEL = 2;
		public static final Integer TEST_MODEL_STOCK_AVAILABLE = 1;
		public static final String TEST_MODEL_IMAGE_PATH = "-TEST_MODEL_IMAGE_PATH-";
		public static final Boolean TEST_MODEL_ACTIVE = true;
	}

	public static class ModelRequestConstants {
		public static final UUID TEST_MODELREQUEST_ID = UUID.fromString( "e58c678a-24b3-464f-b61a-7eeba6d25399" );
		public static final String TEST_MODELREQUEST_LABEL = "-TEST_MODELREQUEST_LABEL-";
		public static final Float TEST_MODELREQUEST_PRICE = 12.0F;
		public static final Integer TEST_MODELREQUEST_STOCK_LEVEL = 4;
		public static final String TEST_MODELREQUEST_IMAGE_PATH = "-TEST_MODELREQUEST_IMAGE_PATH-";
		public static final Boolean TEST_MODELREQUEST_ACTIVE = false;
	}

	public static class RequestConstants {
		public static final UUID TEST_REQUEST_ID = UUID.fromString( "98be9442-edbb-47fe-bc20-60b9e6f4a315" );
		public static final String TEST_REQUEST_LABEL = "-TEST_REQUEST_LABEL-";
		public static final Instant TEST_REQUEST_DATE = Instant.parse( "2020-06-16T16:38:30.562806Z" );
		public static final String TEST_REQUEST_DATE_STRING = "2020-06-16T16:38:30.562806Z";
		public static final RequestState TEST_REQUEST_STATE = RequestState.OPEN;
		public static final float TEST_REQUEST_AMOUNT = 45.67F;
		public static final Instant TEST_REQUEST_CLOSED_DATE = Instant.parse( "2020-06-06T21:54:00.226181Z" );
	}

	public static class JobConstants {
		public static final UUID TEST_JOB_ID = UUID.fromString( "98be9442-edbb-47fe-bc20-60b9e6f4a315" );
		public static final UUID TEST_JOB_PART_ID = UUID.fromString( "bdf5eced-83fb-46d7-bdf9-dbfdd7e8b90f" );
		public static final Integer TEST_JOB_BUILD_TIME = 45;
		public static final Float TEST_JOB_COST = 0.80F;
		public static final Float TEST_JOB_PRICE = 3.00F;
		public static final Integer TEST_JOB_PART_COPIES = 3;
		public static final Instant TEST_JOB_JOBINSTALLMENTDATE = Instant.now();
		public static final Instant TEST_JOB_JOBCOMPLETIONDATE = Instant.now().plus( Duration.ofMinutes( 90 ) );
	}

	public static class RestExceptionResponseConstants {
		public static final String TEST_REST_EXCEPTION_RESPONSE_TIMESTAMP = "2020-06-16T16:38:30.562806Z";
		public static final HttpStatus TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS = HttpStatus.INTERNAL_SERVER_ERROR;
		public static final String TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_NAME = "INTERNAL_SERVER_ERROR";
		public static final Integer TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_CODE = 500;
		public static final String TEST_REST_EXCEPTION_RESPONSE_ERROR_CODE = "dimensinfin.uncatalogued.runtime";
		public static final String TEST_REST_EXCEPTION_RESPONSE_ERROR_NAME = "RUNTIME_INTERNAL_ERROR";
		public static final String TEST_REST_EXCEPTION_RESPONSE_MESSAGE = "Runtime uncatalogued exception: -EXCEPTION-";
		public static final String TEST_REST_EXCEPTION_RESPONSE_CAUSE = "-REST-EXCEPTION-RESPONSE-CAUSE-";
	}
}
