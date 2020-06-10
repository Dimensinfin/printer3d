package org.dimensinfin.printer3d.backend.core.exception;

import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

public class RepositoryExceptionTest {
	@Test
	public void constructorContractErrorInfo() {
		// Given
		final RepositoryException exception = new RepositoryException( ErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE, new SQLException("-SQL-EXCEPTION-"));
		// Test
		final String expected = "There is an SQL error on the Inventory repository. -SQL-EXCEPTION-. Cause: null";
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( expected, message );
		Assertions.assertEquals( ErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE.name(), exception.getErrorInfo().name() );
	}

	@Test
	public void getMessage() {
		// Given
		final RepositoryException exception = new RepositoryException( ErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE, new SQLException("-SQL-EXCEPTION-"));
		// Test
		final String expected = "There is an SQL error on the Inventory repository. -SQL-EXCEPTION-. Cause: null";
		final String message = exception.getMessage();
		// Assertions
		Assertions.assertEquals( expected, message );
	}
}
