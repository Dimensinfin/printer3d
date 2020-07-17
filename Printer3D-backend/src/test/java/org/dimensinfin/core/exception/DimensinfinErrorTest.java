package org.dimensinfin.core.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class DimensinfinErrorTest {
	@Test
	public void buildContract() {
		final DimensinfinError errorComplete = new DimensinfinError.Builder()
				.withErrorName( "-ERROR-NAME-" )
				.withErrorCode( "-ERROR-CODE-" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withMessage( "-MESSAGE-" )
				.withCause(  "-CAUSE-" )
				.build();
		Assertions.assertNotNull( errorComplete );
		final DimensinfinError errorNoCause = new DimensinfinError.Builder()
				.withErrorName( "-ERROR-NAME-" )
				.withErrorCode( "-ERROR-CODE-" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withMessage( "-MESSAGE-" )
				.build();
		Assertions.assertNotNull( errorNoCause );
		final DimensinfinError errorNullCause = new DimensinfinError.Builder()
				.withErrorName( "-ERROR-NAME-" )
				.withErrorCode( "-ERROR-CODE-" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withMessage( "-MESSAGE-" )
				.withCause( null )
				.build();
		Assertions.assertNotNull( errorNullCause );
	}

	@Test
	public void getterContract() {
		// Given
		final DimensinfinError error = new DimensinfinError.Builder()
				.withErrorName( "-ERROR-NAME-" )
				.withErrorCode( "-ERROR-CODE-" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withMessage( "-MESSAGE-" )
				.withCause(  "-CAUSE-"  )
				.build();
		// Assertions
		Assertions.assertEquals( "-ERROR-CODE-", error.getErrorCode() );
		Assertions.assertEquals( "-ERROR-NAME-", error.getErrorName() );
		Assertions.assertEquals( "-MESSAGE-", error.getMessage() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), error.getStatusName() );
		Assertions.assertEquals( "-CAUSE-", error.getCause() );
	}

	@Test
	public void setterContract() {
		// Given
		final DimensinfinError error = new DimensinfinError.Builder()
				.withErrorName( "-ERROR-NAME-" )
				.withErrorCode( "-ERROR-CODE-" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withMessage( "-MESSAGE-" )
				.withCause(  "-CAUSE-"  )
				.build();
		// Assertions
		Assertions.assertEquals( "-CAUSE-", error.getCause() );
		error.setCause( "-OTHER-CAUSE-" );
		Assertions.assertEquals( "-OTHER-CAUSE-", error.getCause() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), error.getStatusName() );
		error.setStatus( HttpStatus.ACCEPTED );
		Assertions.assertEquals( HttpStatus.ACCEPTED, error.getStatus() );
		Assertions.assertEquals( HttpStatus.ACCEPTED.value(), error.getStatusCode() );
		Assertions.assertEquals( HttpStatus.ACCEPTED.name(), error.getStatusName() );
		Assertions.assertEquals( "-MESSAGE-", error.getMessage() );
		error.updateMessage( "-NEW-MESSAGE-" );
		Assertions.assertEquals( "-NEW-MESSAGE-", error.getMessage() );
	}
}
