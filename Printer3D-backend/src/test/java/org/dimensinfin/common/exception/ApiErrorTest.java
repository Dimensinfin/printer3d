package org.dimensinfin.common.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

public class ApiErrorTest {
	@Test
	public void constructorContract() {
		final DimensinfinRuntimeException exception = Mockito.mock( DimensinfinRuntimeException.class );
		final ApiError apiError = new ApiError( exception );
		Assertions.assertNotNull( apiError );
	}

	@Test
	public void gettersContract() {
		// Given
		final DimensinfinRuntimeException exception =
				new DimensinfinRuntimeException( DimensinfinErrorInfo.RUNTIME_INTERNAL_ERROR( "-TEST-MESSAGE-" ) );
		final ApiError apiError = new ApiError( exception );
		// Assertions
		Assertions.assertNull( apiError.getCause() );
		Assertions.assertEquals( "dimensinfin.uncatalogued.runtime", apiError.getErrorCode() );
		Assertions.assertEquals( "INTERNAL_SERVER_ERROR", apiError.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.toString(), apiError.getHttpStatus() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), apiError.getHttpStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), apiError.getHttpStatusName() );
		Assertions.assertEquals( "Runtime uncatalogued exception: -TEST-MESSAGE-", apiError.getMessage() );
		Assertions.assertNotNull( apiError.getTimestamp() );
	}
}
