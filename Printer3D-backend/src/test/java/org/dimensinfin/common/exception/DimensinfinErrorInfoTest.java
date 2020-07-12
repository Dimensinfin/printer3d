package org.dimensinfin.common.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class DimensinfinErrorInfoTest {

	@Test
	public void RUNTIME_INTERNAL_ERROR() {
		// Test
		final DimensinfinError error = DimensinfinErrorInfo.RUNTIME_INTERNAL_ERROR( "-TEST_MESSAGE-" );
		final String messageExpected = "Runtime uncatalogued exception: -TEST_MESSAGE-";
		// Assertions
		Assertions.assertEquals( "INTERNAL_SERVER_ERROR", error.getErrorName() );
		Assertions.assertEquals( "dimensinfin.uncatalogued.runtime", error.getErrorCode() );
		Assertions.assertEquals( messageExpected, error.getMessage() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus() );
	}
}
