package org.dimensinfin.core.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import org.dimensinfin.printer3d.client.core.dto.RestExceptionResponse;

public class DimensinfinRuntimeExceptionTest {
	private static final String EXCEPTION_ERROR_NAME = "RUNTIME_INTERNAL_ERROR";
	private static final String EXCEPTION_ERROR_CODE = "dimensinfin.uncatalogued.runtime";
	private static final String EXCEPTION_ERROR_MESSAGE = "Runtime uncatalogued exception: -MESSAGE-";
	private static final String EXCEPTION_ERROR_CAUSE = "-CAUSE-";

	@Test
	public void RUNTIME_INTERNAL_ERROR() {
		// Test
		final DimensinfinError error = DimensinfinRuntimeException.errorRUNTIMEINTERNALERROR( "-TEST_MESSAGE-" );
		final String messageExpected = "Runtime uncatalogued exception: -TEST_MESSAGE-";
		// Assertions
		Assertions.assertEquals( "RUNTIME_INTERNAL_ERROR", error.getErrorName() );
		Assertions.assertEquals( "dimensinfin.uncatalogued.runtime", error.getErrorCode() );
		Assertions.assertEquals( messageExpected, error.getMessage() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus() );
	}

	@Test
	public void constructorContractError() {
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException(
				DimensinfinRuntimeException.errorRUNTIMEINTERNALERROR( "-MESSAGE-" ) );
		Assertions.assertNotNull( exception );
	}

	@Test
	public void constructorContractRestExceptionResponse() {
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException( new RestExceptionResponse() );
		Assertions.assertNotNull( exception );
	}

	@Test
	public void gettersContractError() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException(
				DimensinfinRuntimeException.errorRUNTIMEINTERNALERROR( "-MESSAGE-" ) );
		// Assertions
		Assertions.assertNull( exception.getCause() );
		Assertions.assertEquals( EXCEPTION_ERROR_CODE, exception.getErrorCode() );
		Assertions.assertEquals( EXCEPTION_ERROR_NAME, exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getHttpStatus().toString() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getHttpStatusName() );
		Assertions.assertEquals( EXCEPTION_ERROR_MESSAGE, exception.getMessage() );
	}

	@Test
	public void gettersContractRestExceptionResponse() {
		// Given
		final RestExceptionResponse exceptionResponse = new RestExceptionResponse();
		exceptionResponse.setErrorName( EXCEPTION_ERROR_NAME );
		exceptionResponse.setErrorCode( EXCEPTION_ERROR_CODE );
		exceptionResponse.setHttpStatusCode( HttpStatus.INTERNAL_SERVER_ERROR.value() );
		exceptionResponse.setHttpStatusName( HttpStatus.INTERNAL_SERVER_ERROR.name() );
		exceptionResponse.setMessage( EXCEPTION_ERROR_MESSAGE );

		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException( exceptionResponse );
		// Assertions
		Assertions.assertNull( exception.getCause() );
		Assertions.assertEquals( EXCEPTION_ERROR_CODE, exception.getErrorCode() );
		Assertions.assertEquals( EXCEPTION_ERROR_NAME, exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getHttpStatus().toString() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getHttpStatusName() );
		Assertions.assertEquals( EXCEPTION_ERROR_MESSAGE, exception.getMessage() );
	}

	@Test
	public void gettersContractWithCause() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException(
				DimensinfinRuntimeException.errorRUNTIMEINTERNALERROR( "-MESSAGE-" ),
				EXCEPTION_ERROR_CAUSE );
		// Assertions
		Assertions.assertEquals( EXCEPTION_ERROR_CAUSE, exception.getCauseMessage() );
		Assertions.assertEquals( EXCEPTION_ERROR_CODE, exception.getErrorCode() );
		Assertions.assertEquals( EXCEPTION_ERROR_NAME, exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getHttpStatus().toString() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getHttpStatusName() );
		Assertions.assertEquals( EXCEPTION_ERROR_MESSAGE, exception.getMessage() );
	}
}
