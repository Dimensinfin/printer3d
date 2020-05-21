package org.dimensinfin.acceptance.support.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.acceptance.support.exception.InvalidRequestException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

public class InvalidRequestExceptionTest {
	private static final String TEST_ERROR_MESSAGE = "The request is not valid.";
	private static final String TEST_COMPLEMENTARY_MESSAGE = "-TEST_COMPLEMENTARY_MESSAGE-";
	private static final String TEST_RUNTIME_MESSAGE = "-TEST_RUNTIME_MESSAGE-";

	@Test
	public void constructorContract() {
		final InvalidRequestException exceptionEmpty = new InvalidRequestException();
		Assertions.assertNotNull( exceptionEmpty );
		final InvalidRequestException exceptionMessage = new InvalidRequestException( TEST_ERROR_MESSAGE );
		Assertions.assertNotNull( exceptionMessage );
		final InvalidRequestException exceptionRuntime = new InvalidRequestException( new RuntimeException( TEST_RUNTIME_MESSAGE ) );
		Assertions.assertNotNull( exceptionRuntime );
	}

	@Test
	public void getMessage4Empty() {
		// Given
		final InvalidRequestException exceptionEmpty = new InvalidRequestException();
		// Assertions
		Assertions.assertEquals( ErrorInfo.INVALID_REQUEST_STRUCTURE, exceptionEmpty.getErrorInfo() );
		Assertions.assertEquals( TEST_ERROR_MESSAGE, exceptionEmpty.getMessage().trim() );
	}

	@Test
	public void getMessage4Message() {
		// Given
		final InvalidRequestException exceptionMessage = new InvalidRequestException( TEST_COMPLEMENTARY_MESSAGE );
		// Assertions
		Assertions.assertEquals( ErrorInfo.INVALID_REQUEST_STRUCTURE, exceptionMessage.getErrorInfo() );
		Assertions.assertEquals( TEST_ERROR_MESSAGE + " " + TEST_COMPLEMENTARY_MESSAGE, exceptionMessage.getMessage() );
	}

	@Test
	public void getMessage4Runtime() {
		// Given
		final InvalidRequestException exceptionRuntime = new InvalidRequestException( new RuntimeException( TEST_RUNTIME_MESSAGE ) );
		// Assertions
		Assertions.assertEquals( ErrorInfo.INVALID_REQUEST_STRUCTURE, exceptionRuntime.getErrorInfo() );
		Assertions.assertEquals( TEST_RUNTIME_MESSAGE, exceptionRuntime.getMessage() );
	}
}
