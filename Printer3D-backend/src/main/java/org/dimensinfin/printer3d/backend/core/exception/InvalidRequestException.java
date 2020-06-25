package org.dimensinfin.printer3d.backend.core.exception;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

public class InvalidRequestException extends DimensinfinRuntimeException {
	public InvalidRequestException() {
		super( ErrorInfo.INVALID_REQUEST_STRUCTURE, " " );
	}

	public InvalidRequestException( final String message ) {
		super( ErrorInfo.INVALID_REQUEST_STRUCTURE, message );
	}

	public InvalidRequestException( final RuntimeException runtime ) {
		super( runtime );
		this.errorInfo = ErrorInfo.INVALID_REQUEST_STRUCTURE;
	}
}
