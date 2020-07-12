package org.dimensinfin.printer3d.backend.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import org.dimensinfin.common.exception.DimensinfinErrorInfo;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;

public class DimensinfinRuntimeExceptionTest {
	private static final String TEST_ERROR_MESSAGE = "-TEST_ERROR_MESSAGE-";

	@Test
	public void constructorContractError() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException(
				DimensinfinErrorInfo.RUNTIME_INTERNAL_ERROR( TEST_ERROR_MESSAGE ) );
		// Test
		final String expected = "Runtime uncatalogued exception: " + TEST_ERROR_MESSAGE;
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( "dimensinfin.uncatalogued.runtime", exception.getErrorCode() );
		Assertions.assertEquals( "RUNTIME_INTERNAL_ERROR", exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getStatusName() );
		Assertions.assertEquals( expected, message );
	}

	@Test
	public void constructorContractMessage() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException( TEST_ERROR_MESSAGE );
		Assertions.assertNotNull( exception );
		// Test
		final String expected = "Runtime uncatalogued exception: " + TEST_ERROR_MESSAGE;
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( "dimensinfin.uncatalogued.runtime", exception.getErrorCode() );
		Assertions.assertEquals( "RUNTIME_INTERNAL_ERROR", exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getStatusName() );
		Assertions.assertEquals( expected, message );
	}
}
