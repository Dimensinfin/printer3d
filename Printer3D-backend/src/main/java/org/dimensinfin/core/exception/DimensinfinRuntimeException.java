package org.dimensinfin.core.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.dimensinfin.printer3d.client.core.dto.RestExceptionResponse;

public class DimensinfinRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -8973768889849000453L;

	public static DimensinfinError errorRUNTIMEINTERNALERROR( final String message ) {
		return new DimensinfinError.Builder()
				.withErrorName( "RUNTIME_INTERNAL_ERROR" )
				.withHttpStatus( HttpStatus.INTERNAL_SERVER_ERROR )
				.withErrorCode( "dimensinfin.uncatalogued.runtime" )
				.withMessage( MessageFormat.format( "Runtime uncatalogued exception: {0}", message ) )
				.build();
	}

	public static DimensinfinError errorINVALIDREQUESTSTRUCTURE( final MethodArgumentNotValidException restException ) {
		return new DimensinfinError.Builder()
				.withErrorName( "INVALID_REQUEST_STRUCTURE" )
				.withHttpStatus( HttpStatus.BAD_REQUEST )
				.withErrorCode( "dimensinfin.request.validation" )
				.withMessage( MessageFormat.format( "The request is not valid. {0}", restException.getMessage() ) )
				.build();
	}

	public static DimensinfinError errorFIELDNOTVALIDREQUEST( final MethodArgumentNotValidException restException,
	                                                          final String cause,
	                                                          final String subcause ) {
		return new DimensinfinError.Builder()
				.withErrorName( "INVALID_REQUEST_FIELD_CONTENTS" )
				.withHttpStatus( HttpStatus.BAD_REQUEST )
				.withErrorCode( "dimensinfin.request.validation" )
				.withMessage( MessageFormat.format( "The request is not valid. {0} {1}", cause, subcause ) )
				.build();
	}

	private final String errorName;
	private final String errorCode;
	private final String causeMessage;
	private final String message;
	private final HttpStatus httpStatus;

	// - C O N S T R U C T O R S
	public DimensinfinRuntimeException( final String errorMessage ) {
		this( errorRUNTIMEINTERNALERROR( errorMessage ) );
	}

	public DimensinfinRuntimeException( final DimensinfinError error ) {
		this.errorName = error.getErrorName();
		this.errorCode = error.getErrorCode();
		this.causeMessage = error.getCause();
		this.message = error.getMessage();
		this.httpStatus = error.getStatus();
	}

	/**
	 * This constructor implementation is used to deserialize backend received exceptions.
	 *
	 * @param exceptionResponse the json deserializer class that jackson will use to convert back the Dimensinfin exception.
	 */
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
