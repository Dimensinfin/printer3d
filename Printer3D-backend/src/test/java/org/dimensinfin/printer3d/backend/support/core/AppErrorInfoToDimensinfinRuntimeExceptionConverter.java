package org.dimensinfin.printer3d.backend.support.core;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.core.interfaces.Converter;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

public class AppErrorInfoToDimensinfinRuntimeExceptionConverter implements Converter<AppErrorInfo, DimensinfinRuntimeException> {
	@Override
	public DimensinfinRuntimeException convert( final AppErrorInfo input ) {
		final ErrorInfo errorInfo = ErrorInfo.valueOf( input.getErrorInfo() );
		return new DimensinfinRuntimeException( errorInfo, input.getMessage() );
	}
}
