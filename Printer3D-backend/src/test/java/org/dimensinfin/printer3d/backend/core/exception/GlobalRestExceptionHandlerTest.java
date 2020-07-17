package org.dimensinfin.printer3d.backend.core.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.ApiError;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;

public class GlobalRestExceptionHandlerTest {
	private static final String EXCEPTION_ERROR_NAME = "RUNTIME_INTERNAL_ERROR";
	private static final String EXCEPTION_ERROR_CODE = "dimensinfin.uncatalogued.runtime";
	private static final String EXCEPTION_ERROR_MESSAGE = "Runtime uncatalogued exception: -RUNTIME-EXCEPTION-MESSAGE-";
	private static final String EXCEPTION_ERROR_CAUSE = "Intercepted RuntimeException at the ErrorHandler level because this was not expected.";

	@Test
	public void handleDimensinfinRuntimeException() {
		// Test
		final GlobalRestExceptionHandler exceptionHandler = new GlobalRestExceptionHandler();
		final ResponseEntity<ApiError> obtained = exceptionHandler.handleDimensinfinRuntimeException(
				new DimensinfinRuntimeException( "-DIMENSINFIN-RUNTIME-EXCEPTION-" ) );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, obtained.getStatusCode() );
		Assertions.assertNull( obtained.getBody().getCause() );
		Assertions.assertEquals( EXCEPTION_ERROR_CODE, obtained.getBody().getErrorCode() );
		Assertions.assertEquals( EXCEPTION_ERROR_NAME, obtained.getBody().getErrorName() );
		Assertions.assertEquals( "Runtime uncatalogued exception: -DIMENSINFIN-RUNTIME-EXCEPTION-", obtained.getBody().getMessage() );
	}

	@Test
	public void handleRuntimeException() {
		// Test
		final GlobalRestExceptionHandler exceptionHandler = new GlobalRestExceptionHandler();
		final ResponseEntity<ApiError> obtained = exceptionHandler.handleRuntimeException( new RuntimeException( "-RUNTIME-EXCEPTION-MESSAGE-" ) );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, obtained.getStatusCode() );
		Assertions.assertEquals( EXCEPTION_ERROR_CAUSE, obtained.getBody().getCause() );
		Assertions.assertEquals( EXCEPTION_ERROR_CODE, obtained.getBody().getErrorCode() );
		Assertions.assertEquals( EXCEPTION_ERROR_NAME, obtained.getBody().getErrorName() );
		Assertions.assertEquals( EXCEPTION_ERROR_MESSAGE, obtained.getBody().getMessage() );
	}
}
