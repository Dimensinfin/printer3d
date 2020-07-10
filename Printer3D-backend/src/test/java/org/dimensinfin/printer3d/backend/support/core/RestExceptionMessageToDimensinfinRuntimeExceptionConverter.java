package org.dimensinfin.printer3d.backend.support.core;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.core.exception.ErrorInfoN;

public class RestExceptionMessageToDimensinfinRuntimeExceptionConverter implements Converter<RestExceptionMessage, DimensinfinRuntimeException> {
	@Override
	public DimensinfinRuntimeException convert( final RestExceptionMessage input ) {
		return new DimensinfinRuntimeException().setError(
				new ErrorInfoN.Builder()
						.withErrorName( input.getErrorInfo() )
						.withMessage( input.getMessage() )
						.build()
		);
	}
}
