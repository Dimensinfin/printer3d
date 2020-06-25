package org.dimensinfin.common.exception;

import java.text.MessageFormat;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;

import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

public class DimensinfinRuntimeException extends RuntimeException {
	// - INITIALIZATION PROCESSORS
	private static String processException( final RuntimeException runtime ) {
		if (runtime instanceof NullPointerException) { // Process NullPointer exceptions
			final StackTraceElement traceElement = runtime.getStackTrace()[0];
			return MessageFormat.format( "NullPointerException detected when: {0}", traceElement );
		} else return ErrorInfo.RUNTIME_INTERNAL_ERROR.getErrorMessage( runtime.getMessage() );
	}

	protected ErrorInfo errorInfo = ErrorInfo.RUNTIME_INTERNAL_ERROR;
	protected String cause ;

	// - C O N S T R U C T O R S
	public DimensinfinRuntimeException() {
		super( ErrorInfo.RUNTIME_INTERNAL_ERROR.getErrorMessage( " " ).trim() );
	}

	public DimensinfinRuntimeException( final @NotNull String errorMessage ) {
		super( ErrorInfo.RUNTIME_INTERNAL_ERROR.getErrorMessage( errorMessage ) );
	}

	public DimensinfinRuntimeException( final @NotNull ErrorInfo errorInfo ) {
		super( errorInfo.getErrorMessage() );
		this.errorInfo = errorInfo;
	}

	public DimensinfinRuntimeException( final RuntimeException runtime ) {
		super( processException( runtime ) );
	}

	public DimensinfinRuntimeException( @NotNull final ErrorInfo errorInfo, final Object... messageParameters ) {
		super( errorInfo.getErrorMessage( messageParameters ) );
		this.errorInfo = errorInfo;
	}

	// - G E T T E R S   &   S E T T E R S
	public ErrorInfo getErrorInfo() {
		return this.errorInfo;
	}

	public DimensinfinRuntimeException setErrorInfo( final ErrorInfo newErrorInfo ) {
		this.errorInfo = newErrorInfo;
		return this;
	}

	public HttpStatus getHttpStatus() {
		return this.errorInfo.status;
	}

	protected void setCause( final Throwable rootCause ) {
	}
}
