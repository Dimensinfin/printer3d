package org.dimensinfin.printer3d.backend.support.core;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.common.exception.RestExceptionResponse;
import org.dimensinfin.core.interfaces.Converter;

public class RestExceptionMessageToDimensinfinRuntimeExceptionConverter implements Converter<RestExceptionResponse, DimensinfinRuntimeException> {
	@Deprecated
	@Override
	public DimensinfinRuntimeException convert( final RestExceptionResponse input ) {
		return new DimensinfinRuntimeException("Deprecated");
//				.setError(
//				new DimensinfinError.Builder()
//						.withErrorName( input.getErrorInfo() )
//						.withHttpStatus( HttpStatus.valueOf( input.getHttpStatusCode() ) )
//						.withErrorCode( input.getErrorCode() )
//						.withMessage( input.getMessage() )
//						.build()
//		);
	}
}
