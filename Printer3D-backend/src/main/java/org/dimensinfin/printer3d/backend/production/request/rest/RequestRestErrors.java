package org.dimensinfin.printer3d.backend.production.request.rest;

import java.text.MessageFormat;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import org.dimensinfin.core.exception.DimensinfinError;
import org.dimensinfin.printer3d.backend.Printer3DApplication;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.17.0
 */
public class RequestRestErrors {
	public static DimensinfinError errorREQUESTMISSINGFIELD( final String fieldName ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_MISSING_FIELD" )
				.withErrorCode( Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX + ".request.violation" )
				.withHttpStatus( HttpStatus.BAD_REQUEST )
				.withMessage( MessageFormat.format( "The Customer Request constructor requires field {0} to be defined.", fieldName ) )
				.build();
	}

	public static DimensinfinError errorREQUESTNOTFOUND( final UUID requestId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".defined.repository.logic" )
				.withHttpStatus( HttpStatus.NOT_FOUND )
				.withMessage( MessageFormat.format( "Request record with id [{0}] not found at the repository.", requestId ) )
				.build();
	}

	public static DimensinfinError errorREQUESTINCORRECTSTATE( final UUID requestId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_INCORRECT_STATE" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".logic.exception" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format(
						"Request record with id [{0}] is not on the correct state to perform the requested operation.", requestId ) )
				.build();
	}

	public static DimensinfinError errorREQUESTALREADYEXISTS( final UUID requestId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_ALREADY_EXISTS" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".already.exists" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format(
						"Request with id [{0}] already exists at the repository. This should not be possible and means a development defect.",
						requestId ) )
				.build();
	}

	// - C O N S T R U C T O R S
	private RequestRestErrors() {}
}