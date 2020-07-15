package org.dimensinfin.common.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class DimensinfinErrorInfo {
	public static DimensinfinError RUNTIME_INTERNAL_ERROR( final String message ) {
		return new DimensinfinError.Builder()
				.withErrorName( "RUNTIME_INTERNAL_ERROR" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withErrorCode( "dimensinfin.uncatalogued.runtime" )
				.withMessage( MessageFormat.format( "Runtime uncatalogued exception: {0}", message ) )
				.build();
	}

	public static DimensinfinError INVALID_REQUEST_STRUCTURE( final MethodArgumentNotValidException restException ) {
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
