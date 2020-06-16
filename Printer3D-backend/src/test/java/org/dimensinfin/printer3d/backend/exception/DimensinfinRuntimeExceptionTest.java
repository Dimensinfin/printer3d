package org.dimensinfin.printer3d.backend.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class DimensinfinRuntimeExceptionTest {
	private static final String TEST_ERROR_MESSAGE = "-TEST_ERROR_MESSAGE-";

	@Test
	public void constructorContractEmpty() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException();
		Assertions.assertNotNull( exception );
		// Test
		final String expected = "";
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( expected, message );
		Assertions.assertEquals( ErrorInfo.RUNTIME_INTERNAL_ERROR.name(), exception.getErrorInfo().name() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus() );
	}

	@Test
	public void constructorContractErrorInfo() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException( ErrorInfo.INVALID_REQUEST_STRUCTURE );
		Assertions.assertNotNull( exception );
		// Test
		final String expected = ErrorInfo.INVALID_REQUEST_STRUCTURE.getErrorMessage();
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( expected, message );
		Assertions.assertEquals( ErrorInfo.INVALID_REQUEST_STRUCTURE.name(), exception.getErrorInfo().name() );
		Assertions.assertEquals( HttpStatus.BAD_REQUEST, exception.getHttpStatus() );
	}

	@Test
	public void constructorContractMessage() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException( TEST_ERROR_MESSAGE );
		Assertions.assertNotNull( exception );
		// Test
		final String expected = TEST_ERROR_MESSAGE;
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( expected, message );
		Assertions.assertEquals( ErrorInfo.RUNTIME_INTERNAL_ERROR.name(), exception.getErrorInfo().name() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus() );
	}

	@Test
	public void constructorContractNPE() {
		// Given
		final DimensinfinRuntimeException exception = new DimensinfinRuntimeException( new NullPointerException() );
		// Test
		final String expected = "NullPointerException detected when: org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeExceptionTest" +
				".constructorContractNPE(DimensinfinRuntimeExceptionTest.java:55)";
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( expected, message );
		Assertions.assertEquals( ErrorInfo.RUNTIME_INTERNAL_ERROR.name(), exception.getErrorInfo().name() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus() );
	}
}
