package org.dimensinfin.printer3d.backend.support.core;

import org.springframework.http.HttpStatus;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.common.exception.DimensinfinError;
import org.dimensinfin.core.interfaces.Converter;

public class RestExceptionMessageToDimensinfinRuntimeExceptionConverter implements Converter<RestExceptionMessage, DimensinfinRuntimeException> {
	@Override
	public DimensinfinRuntimeException convert( final RestExceptionMessage input ) {
		return new DimensinfinRuntimeException().setError(
				new DimensinfinError.Builder()
						.withErrorName( input.getErrorInfo() )
						.withHttpStatus( HttpStatus.valueOf( input.getHttpStatusCode() ) )
						.withErrorCode( input.getErrorCode() )
						.withMessage( input.getMessage() )
						.build()
		);
	}
}
