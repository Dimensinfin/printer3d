package org.dimensinfin.printer3d.backend.core.exception;

import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

public class RepositoryExceptionTest {
	@Test
	public void constructorContractErrorException() {
		// Given
		final UUID recordId = UUID.fromString( "b728b7bc-7d6c-4a86-89bf-ae0fa4a29a24" );
		final RepositoryException exception = new RepositoryException(
				Printer3DErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE( recordId, new SQLException( "-SQL-EXCEPTION-" ) ) );
		// Test
		final String expected = "Request record with id [b728b7bc-7d6c-4a86-89bf-ae0fa4a29a24] not found at the repository. -SQL-EXCEPTION-";
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error", exception.getErrorCode() );
		Assertions.assertEquals( "INVENTORY_STORE_REPOSITORY_FAILURE", exception.getErrorName() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getStatusCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getStatusName() );
		Assertions.assertEquals( expected, message );
	}

//	@Test
//	public void getMessage() {
//		// Given
//		final RepositoryException exception = new RepositoryException( ErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE,
//				new SQLException( "-SQL-EXCEPTION-" ) );
//		// Test
//		final String expected = "There is an SQL error on the Inventory repository. -SQL-EXCEPTION-. Cause: null";
//		final String message = exception.getMessage();
//		// Assertions
//		Assertions.assertEquals( expected, message );
//	}
}
