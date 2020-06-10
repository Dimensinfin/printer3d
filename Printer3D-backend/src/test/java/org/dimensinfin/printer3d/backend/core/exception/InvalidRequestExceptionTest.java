package org.dimensinfin.printer3d.backend.core.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

public class InvalidRequestExceptionTest {
	private static final String TEST_ERROR_MESSAGE = "-TEST_ERROR_MESSAGE-";

	@Test
	public void constructorContractEmpty() {
		// Given
		final InvalidRequestException exception = new InvalidRequestException();
		Assertions.assertNotNull( exception );
		// Test
		final String expected = "The request is not valid.  ";
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( expected, message );
		Assertions.assertEquals( ErrorInfo.INVALID_REQUEST_STRUCTURE.name(), exception.getErrorInfo().name() );
	}

	@Test
	public void constructorContractException() {
		// Given
		final InvalidRequestException exception = new InvalidRequestException( new RuntimeException( TEST_ERROR_MESSAGE ) );
		Assertions.assertNotNull( exception );
		// Test
		final String expected = TEST_ERROR_MESSAGE;
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( expected, message );
		Assertions.assertEquals( ErrorInfo.INVALID_REQUEST_STRUCTURE.name(), exception.getErrorInfo().name() );
	}

	@Test
	public void constructorContractMessage() {
		// Given
		final InvalidRequestException exception = new InvalidRequestException( TEST_ERROR_MESSAGE );
		Assertions.assertNotNull( exception );
		// Test
		final String expected = "The request is not valid. "+TEST_ERROR_MESSAGE;
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( expected, message );
		Assertions.assertEquals( ErrorInfo.INVALID_REQUEST_STRUCTURE.name(), exception.getErrorInfo().name() );
	}
}
