package org.dimensinfin.printer3d.backend.core.exception;

import java.sql.SQLException;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import org.dimensinfin.core.exception.DimensinfinError;
import org.dimensinfin.printer3d.backend.production.request.rest.RequestRestErrors;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

public class Printer3DErrorInfoTest {
	@Test
	public void REQUEST_NOT_FOUND() {
		// Test
		final DimensinfinError error = RequestRestErrors.errorREQUESTNOTFOUND( UUID.fromString( "aed625aa-8999-4227-b0ad-cbfbd3966771" ) );
		final String messageExpected = "Request record with id [aed625aa-8999-4227-b0ad-cbfbd3966771] not found at the repository.";
		// Assertions
		Assertions.assertEquals( "REQUEST_NOT_FOUND", error.getErrorName() );
		Assertions.assertEquals( APPLICATION_ERROR_CODE_PREFIX + ".defined.repository.logic", error.getErrorCode() );
		Assertions.assertEquals( messageExpected, error.getMessage() );
		Assertions.assertEquals( HttpStatus.NOT_FOUND, error.getStatus() );
	}

	@Test
	public void errorINVENTORYSTOREREPOSITORYFAILURE() {
		// Test
		final DimensinfinError error = Printer3DErrorInfo.errorINVENTORYSTOREREPOSITORYFAILURE( new SQLException( "-SQL-TEST-MESSAGE-" ) );
		final String messageExpected = "There is an SQL error on the Inventory repository. -SQL-TEST-MESSAGE-.";
		// Assertions
		Assertions.assertEquals( "INVENTORY_STORE_REPOSITORY_FAILURE", error.getErrorName() );
		Assertions.assertEquals( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error", error.getErrorCode() );
		Assertions.assertEquals( messageExpected, error.getMessage() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus() );
	}

	@Test
	public void errorINVENTORYSTOREREPOSITORYFAILUREwithcause() {
		// Test
		final DimensinfinError error = Printer3DErrorInfo.errorINVENTORYSTOREREPOSITORYFAILURE(
				new SQLException( new Throwable( "-CAUSE-MESSAGE" ) )
		);
		final String messageExpected = "There is an SQL error on the Inventory repository. java.lang.Throwable: -CAUSE-MESSAGE. SQL cause: java.lang.Throwable: -CAUSE-MESSAGE";
		// Assertions
		Assertions.assertEquals( "INVENTORY_STORE_REPOSITORY_FAILURE", error.getErrorName() );
		Assertions.assertEquals( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error", error.getErrorCode() );
		Assertions.assertEquals( messageExpected, error.getMessage() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus() );
	}

	@Test
	public void errorMODELNOTFOUND() {
		// Test
		final DimensinfinError error = Printer3DErrorInfo.errorMODELNOTFOUND( UUID.fromString( "f6659840-0135-4c44-a7df-0b880ecf88f9" ) );
		final String messageExpected = "Model with id [f6659840-0135-4c44-a7df-0b880ecf88f9] not found at the repository.";
		// Assertions
		Assertions.assertEquals( "MODEL_NOT_FOUND", error.getErrorName() );
		Assertions.assertEquals( APPLICATION_ERROR_CODE_PREFIX + ".notfound", error.getErrorCode() );
		Assertions.assertEquals( messageExpected, error.getMessage() );
		Assertions.assertEquals( HttpStatus.NOT_FOUND, error.getStatus() );
	}

	@Test
	public void errorREQUESTSTOREREPOSITORYFAILURE() {
		// Test
		final DimensinfinError error = Printer3DErrorInfo.errorREQUESTSTOREREPOSITORYFAILURE( new SQLException( "-SQL-TEST-MESSAGE-" ) );
		final String messageExpected = "There is an SQL error on the Requests repository. -SQL-TEST-MESSAGE-.";
		// Assertions
		Assertions.assertEquals( "REQUEST_STORE_REPOSITORY_FAILURE", error.getErrorName() );
		Assertions.assertEquals( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error", error.getErrorCode() );
		Assertions.assertEquals( messageExpected, error.getMessage() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus() );
	}

	@Test
	public void errorREQUESTSTOREREPOSITORYFAILUREwithcause() {
		// Test
		final DimensinfinError error = Printer3DErrorInfo.errorREQUESTSTOREREPOSITORYFAILURE(
				new SQLException( new Throwable( "-CAUSE-MESSAGE" ) )
		);
		final String messageExpected = "There is an SQL error on the Requests repository. java.lang.Throwable: -CAUSE-MESSAGE. SQL cause: java.lang.Throwable: -CAUSE-MESSAGE";
		// Assertions
		Assertions.assertEquals( "REQUEST_STORE_REPOSITORY_FAILURE", error.getErrorName() );
		Assertions.assertEquals( APPLICATION_ERROR_CODE_PREFIX + ".persistence.sql.error", error.getErrorCode() );
		Assertions.assertEquals( messageExpected, error.getMessage() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, error.getStatus() );
	}
}
