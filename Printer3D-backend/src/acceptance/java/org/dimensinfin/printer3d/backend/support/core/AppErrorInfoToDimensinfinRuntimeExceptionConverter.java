package org.dimensinfin.printer3d.backend.support.core;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.core.interfaces.Converter;

public class AppErrorInfoToDimensinfinRuntimeExceptionConverter implements Converter<AppErrorInfo, DimensinfinRuntimeException> {
	@Deprecated
	@Override
	public DimensinfinRuntimeException convert( final AppErrorInfo input ) {
//		final ErrorInfo errorInfo = ErrorInfo.valueOf( input.getErrorInfo() );
		return new DimensinfinRuntimeException( "Deprecated" );
	}
}
