package org.dimensinfin.printer3d.backend.support.core;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.common.exception.RestExceptionMessage;
import org.dimensinfin.core.interfaces.Converter;

public class RestExceptionMessageToDimensinfinRuntimeExceptionConverter implements Converter<RestExceptionMessage, DimensinfinRuntimeException> {
	@Deprecated
	@Override
	public DimensinfinRuntimeException convert( final RestExceptionMessage input ) {
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
