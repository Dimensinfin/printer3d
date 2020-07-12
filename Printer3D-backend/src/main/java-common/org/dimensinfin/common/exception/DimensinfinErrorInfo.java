package org.dimensinfin.common.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

public class DimensinfinErrorInfo {
	public static DimensinfinError RUNTIME_INTERNAL_ERROR( final String message ) {
		return new DimensinfinError.Builder()
				.withErrorName( "INTERNAL_SERVER_ERROR" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withErrorCode( "dimensinfin.uncatalogued.runtime" )
				.withMessage( MessageFormat.format( "Runtime uncatalogued exception: {0}", message ) )
				.build();
	}
	public static DimensinfinError INVALID_REQUEST_STRUCTURE( final RestExceptionMessage restException ) {
		// TODO - Process the eception to extract the validation cause.
		return new DimensinfinError.Builder()
				.withErrorName( "INVALID_REQUEST_STRUCTURE" )
				.withHttpStatus( HttpStatus.BAD_REQUEST )
				.withErrorCode( "dimensinfin.request.validation" )
				.withMessage( MessageFormat.format( "The request is not valid. {0}", restException.getMessage() ) )
				.build();
	}

	// - C O N S T R U C T O R S
	protected DimensinfinErrorInfo() { }
}
