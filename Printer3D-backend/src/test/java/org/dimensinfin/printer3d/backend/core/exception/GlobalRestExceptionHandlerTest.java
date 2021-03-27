package org.dimensinfin.printer3d.backend.core.exception;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import org.dimensinfin.core.exception.ApiError;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;
import static org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo.PERSISTENCE_ERROR;

public class GlobalRestExceptionHandlerTest {
	private static final String EXCEPTION_ERROR_NAME = "RUNTIME_INTERNAL_ERROR";
	private static final String EXCEPTION_ERROR_CODE = "dimensinfin.uncatalogued.runtime";
	private static final String EXCEPTION_ERROR_MESSAGE = "Runtime uncatalogued exception: -RUNTIME-EXCEPTION-MESSAGE-";
	private static final String EXCEPTION_ERROR_CAUSE = "Intercepted RuntimeException at the ErrorHandler level because this was not expected.";

	@Test
	public void handleDataIntegrityViolationException() {
		// Given
		final SQLException internalSQLException = new SQLException( "-CAUSE-MESSAGE-" );
		final SQLException processorException = new SQLException( "-PROCESSOR-EXCEPTION-" );
		processorException.initCause( internalSQLException );
		final DataIntegrityViolationException exception = new DataIntegrityViolationException( "-MESSAGE-EXCEPTION-LEVEL-" );
		exception.initCause( processorException );
		// Test
		final GlobalRestExceptionHandler exceptionHandler = new GlobalRestExceptionHandler();
		final ResponseEntity<ApiError> obtained = exceptionHandler.handleDataIntegrityViolationException( exception );
		// Assertions
		final String expectedMessage = "There is an SQL error on the Inventory repository. -MESSAGE-EXCEPTION-LEVEL-; nested exception is java.sql" +
				".SQLException: -PROCESSOR-EXCEPTION-. SQL cause: -CAUSE-MESSAGE-";
		final String expectedCause = "-CAUSE-MESSAGE-";
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, obtained.getStatusCode() );
		Assertions.assertEquals( APPLICATION_ERROR_CODE_PREFIX + PERSISTENCE_ERROR, obtained.getBody().getErrorCode() );
		Assertions.assertEquals( "INVENTORY_STORE_REPOSITORY_FAILURE", obtained.getBody().getErrorName() );
		Assertions.assertEquals( expectedMessage, obtained.getBody().getMessage() );
		Assertions.assertEquals( expectedCause, obtained.getBody().getCause() );
	}

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
	public void handleMethodArgumentNotValid() {
		// Given
		final MethodArgumentNotValidException exception = Mockito.mock( MethodArgumentNotValidException.class );
		final HttpHeaders headers = Mockito.mock( HttpHeaders.class );
		final HttpStatus status = HttpStatus.BAD_REQUEST;
		final WebRequest request = Mockito.mock( WebRequest.class );
		// When
		Mockito.when( exception.getMessage() ).thenReturn( "Test Message" );
		// Test
		final GlobalRestExceptionHandler exceptionHandler = new GlobalRestExceptionHandler();
		final ResponseEntity<Object> obtained = exceptionHandler.handleMethodArgumentNotValid(
				exception, headers, status, request
		);
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( HttpStatus.BAD_REQUEST, obtained.getStatusCode() );
		Assertions.assertEquals( "The request is not valid. Test Message", ((ApiError) obtained.getBody()).getMessage() );
	}
}
