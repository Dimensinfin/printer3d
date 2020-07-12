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

	// - C O N S T R U C T O R S
	protected DimensinfinErrorInfo() { }
}
