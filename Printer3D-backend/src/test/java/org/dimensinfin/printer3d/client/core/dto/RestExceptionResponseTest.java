package org.dimensinfin.printer3d.client.core.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RestExceptionResponseConstants.TEST_REST_EXCEPTION_RESPONSE_CAUSE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RestExceptionResponseConstants.TEST_REST_EXCEPTION_RESPONSE_ERROR_CODE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RestExceptionResponseConstants.TEST_REST_EXCEPTION_RESPONSE_ERROR_NAME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RestExceptionResponseConstants.TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RestExceptionResponseConstants.TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_CODE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RestExceptionResponseConstants.TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_NAME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RestExceptionResponseConstants.TEST_REST_EXCEPTION_RESPONSE_MESSAGE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RestExceptionResponseConstants.TEST_REST_EXCEPTION_RESPONSE_TIMESTAMP;

public class RestExceptionResponseTest {
	@Test
	public void getterContract() {
		// Given
		final RestExceptionResponse restExceptionResponse = new RestExceptionResponse()
				.withErrorCode( TEST_REST_EXCEPTION_RESPONSE_ERROR_CODE )
				.withErrorName( TEST_REST_EXCEPTION_RESPONSE_ERROR_NAME )
				.withHttpStatus( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS.toString() )
				.withHttpStatusCode( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_CODE )
				.withHttpStatusName( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_NAME )
				.withMessage( TEST_REST_EXCEPTION_RESPONSE_MESSAGE )
				.withCause( TEST_REST_EXCEPTION_RESPONSE_CAUSE )
				.withTimestamp( TEST_REST_EXCEPTION_RESPONSE_TIMESTAMP );
		// Assertions
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_ERROR_CODE, restExceptionResponse.getErrorCode() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_ERROR_NAME, restExceptionResponse.getErrorName() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS.toString(), restExceptionResponse.getHttpStatus().toString() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_CODE, restExceptionResponse.getHttpStatusCode() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_NAME, restExceptionResponse.getHttpStatusName() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_MESSAGE, restExceptionResponse.getMessage() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_CAUSE, restExceptionResponse.getCause() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_TIMESTAMP, restExceptionResponse.getTimestamp().toString() );
	}

	@Test
	public void setterContract() {
		// Given
		final RestExceptionResponse restExceptionResponse = new RestExceptionResponse();
		restExceptionResponse.setErrorCode( TEST_REST_EXCEPTION_RESPONSE_ERROR_CODE );
		restExceptionResponse.setErrorName( TEST_REST_EXCEPTION_RESPONSE_ERROR_NAME );
		restExceptionResponse.setHttpStatus( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS.toString() );
		restExceptionResponse.setHttpStatusCode( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_CODE );
		restExceptionResponse.setHttpStatusName( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_NAME );
		restExceptionResponse.setMessage( TEST_REST_EXCEPTION_RESPONSE_MESSAGE );
		restExceptionResponse.setCause( TEST_REST_EXCEPTION_RESPONSE_CAUSE );
		restExceptionResponse.setTimestamp( TEST_REST_EXCEPTION_RESPONSE_TIMESTAMP );
		// Assertions
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_ERROR_CODE, restExceptionResponse.getErrorCode() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_ERROR_NAME, restExceptionResponse.getErrorName() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS.toString(), restExceptionResponse.getHttpStatus().toString() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_CODE, restExceptionResponse.getHttpStatusCode() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_HTTPSTATUS_NAME, restExceptionResponse.getHttpStatusName() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_MESSAGE, restExceptionResponse.getMessage() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_CAUSE, restExceptionResponse.getCause() );
		Assertions.assertEquals( TEST_REST_EXCEPTION_RESPONSE_TIMESTAMP, restExceptionResponse.getTimestamp().toString() );
	}
}
