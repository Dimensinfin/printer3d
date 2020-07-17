package org.dimensinfin.common.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class DimensinfinRuntimeException extends RuntimeException {
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

	private final String errorName;
	private final String errorCode;
	private final String causeMessage;
	private final String message;
	private final HttpStatus httpStatus;

	// - C O N S T R U C T O R S
	public DimensinfinRuntimeException( final String errorMessage ) {
		this( RUNTIME_INTERNAL_ERROR( errorMessage ) );
	}

	public DimensinfinRuntimeException( final DimensinfinError error ) {
		this.errorName = error.getErrorName();
		this.errorCode = error.getErrorCode();
		this.causeMessage = error.getCause();
		this.message = error.getMessage();
		this.httpStatus = error.getStatus();
	}

	public DimensinfinRuntimeException( final RestExceptionResponse exceptionResponse ) {
		this.errorName = exceptionResponse.getErrorName();
		this.errorCode = exceptionResponse.getErrorCode();
		this.causeMessage = exceptionResponse.getCause();
		this.message = exceptionResponse.getMessage();
		this.httpStatus = HttpStatus.valueOf( exceptionResponse.getHttpStatusCode() );
	}

	public DimensinfinRuntimeException( final DimensinfinError error, final String cause ) {
		this.errorName = error.getErrorName();
		this.errorCode = error.getErrorCode();
		this.causeMessage = cause;
		this.message = error.getMessage();
		this.httpStatus = error.getStatus();
	}

	// - G E T T E R S   &   S E T T E R S
	public String getCauseMessage() {
		return this.causeMessage;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getErrorName() {
		return this.errorName;
	}

	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	public int getHttpStatusCode() {
		return this.httpStatus.value();
	}

	public String getHttpStatusName() {
		return this.httpStatus.name();
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
