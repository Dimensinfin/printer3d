package org.dimensinfin.core.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.dimensinfin.printer3d.client.core.dto.RestExceptionResponse;

public class DimensinfinRuntimeExceptionTest {
	private static final String RUNTIME_EXCEPTION_ERROR_NAME = "RUNTIME_INTERNAL_ERROR";
	private static final String RUNTIME_EXCEPTION_ERROR_CODE = "dimensinfin.uncatalogued.runtime";
	private static final String RUNTIME_EXCEPTION_ERROR_MESSAGE = "Runtime uncatalogued exception: -MESSAGE-";
	private static final String EXCEPTION_ERROR_CAUSE = "-CAUSE-";
	private static final String INVALIDREQUEST_EXCEPTION_ERROR_NAME = "INVALID_REQUEST_STRUCTURE";
	private static final String INVALIDREQUEST_EXCEPTION_ERROR_CODE = "dimensinfin.request.validation";
	private static final String VALIDATION_MESSAGE = "-VALIDATION-METHOD-EXCEPTION-MESSAGE-";
	private static final String INVALIDREQUEST_EXCEPTION_ERROR_MESSAGE = "The request is not valid. " + VALIDATION_MESSAGE;

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
	public void constructorContractErrorInvalidRequest() {
		// Given
		final MethodArgumentNotValidException methodException = Mockito.mock( MethodArgumentNotValidException.class );
		// When
		Mockito.when( methodException.getMessage() ).thenReturn( VALIDATION_MESSAGE );
		// Test
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException(
				DimensinfinRuntimeException.errorINVALIDREQUESTSTRUCTURE( methodException ) );
		// Assertions
		Assertions.assertNotNull( exception );
		Assertions.assertEquals( INVALIDREQUEST_EXCEPTION_ERROR_CODE, exception.getErrorCode() );
		Assertions.assertEquals( INVALIDREQUEST_EXCEPTION_ERROR_NAME, exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.BAD_REQUEST.toString(), exception.getHttpStatus().toString() );
		Assertions.assertEquals( HttpStatus.BAD_REQUEST.value(), exception.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.BAD_REQUEST.name(), exception.getHttpStatusName() );
		Assertions.assertEquals( INVALIDREQUEST_EXCEPTION_ERROR_MESSAGE, exception.getMessage() );
	}

	@Test
	public void constructorContractErrorMessage() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException( "-MESSAGE-" );
		// Assertions
		Assertions.assertNotNull( exception );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_CODE, exception.getErrorCode() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_NAME, exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getHttpStatus().toString() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getHttpStatusName() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_MESSAGE, exception.getMessage() );
	}

	@Test
	public void constructorContractErrorRuntime() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException(
				DimensinfinRuntimeException.errorRUNTIMEINTERNALERROR( "-MESSAGE-" ) );
		// Assertions
		Assertions.assertNotNull( exception );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_CODE, exception.getErrorCode() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_NAME, exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getHttpStatus().toString() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getHttpStatusName() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_MESSAGE, exception.getMessage() );
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
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_CODE, exception.getErrorCode() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_NAME, exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getHttpStatus().toString() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getHttpStatusName() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_MESSAGE, exception.getMessage() );
	}

	@Test
	public void gettersContractRestExceptionResponse() {
		// Given
		final RestExceptionResponse exceptionResponse = new RestExceptionResponse();
		exceptionResponse.setErrorName( RUNTIME_EXCEPTION_ERROR_NAME );
		exceptionResponse.setErrorCode( RUNTIME_EXCEPTION_ERROR_CODE );
		exceptionResponse.setHttpStatusCode( HttpStatus.INTERNAL_SERVER_ERROR.value() );
		exceptionResponse.setHttpStatusName( HttpStatus.INTERNAL_SERVER_ERROR.name() );
		exceptionResponse.setMessage( RUNTIME_EXCEPTION_ERROR_MESSAGE );

		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException( exceptionResponse );
		// Assertions
		Assertions.assertNull( exception.getCause() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_CODE, exception.getErrorCode() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_NAME, exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getHttpStatus().toString() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getHttpStatusName() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_MESSAGE, exception.getMessage() );
	}

	@Test
	public void gettersContractWithCause() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException(
				DimensinfinRuntimeException.errorRUNTIMEINTERNALERROR( "-MESSAGE-" ),
				EXCEPTION_ERROR_CAUSE );
		// Assertions
		Assertions.assertEquals( EXCEPTION_ERROR_CAUSE, exception.getCauseMessage() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_CODE, exception.getErrorCode() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_NAME, exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getHttpStatus().toString() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getHttpStatusName() );
		Assertions.assertEquals( RUNTIME_EXCEPTION_ERROR_MESSAGE, exception.getMessage() );
	}
}
