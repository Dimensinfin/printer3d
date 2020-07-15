package org.dimensinfin.printer3d.backend.core.exception;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import org.dimensinfin.common.exception.DimensinfinError;
import org.dimensinfin.printer3d.backend.production.request.rest.v2.RequestServiceV2;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

public class Printer3DErrorInfoTest {
	@Test
	public void REQUEST_NOT_FOUND() {
		// Test
		final DimensinfinError error = RequestServiceV2.REQUEST_NOT_FOUND( UUID.fromString( "aed625aa-8999-4227-b0ad-cbfbd3966771" ) );
		final String messageExpected = "Request record with id [aed625aa-8999-4227-b0ad-cbfbd3966771] not found at the repository.";
		// Assertions
		Assertions.assertEquals( "REQUEST_NOT_FOUND", error.getErrorName() );
		Assertions.assertEquals( APPLICATION_ERROR_CODE_PREFIX + ".defined.repository.logic", error.getErrorCode() );
		Assertions.assertEquals( messageExpected, error.getMessage() );
		Assertions.assertEquals( HttpStatus.NOT_FOUND, error.getStatus() );
	}
}
