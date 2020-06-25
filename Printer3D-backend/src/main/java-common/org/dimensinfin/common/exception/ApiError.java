package org.dimensinfin.common.exception;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import org.dimensinfin.printer3d.backend.exception.ErrorInfo;

public class ApiError {
	private HttpStatus httpStatus;
	private DimensinfinRuntimeException dimensinfinRuntimeException;
	//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private OffsetDateTime timestamp;
	private String message;
	private String debugMessage;
	private List<ApiSubError> subErrors;

	// - C O N S T R U C T O R S
	public ApiError( final DimensinfinRuntimeException dimensinfinRuntimeException ) {
		this.dimensinfinRuntimeException = dimensinfinRuntimeException;
		this.httpStatus = dimensinfinRuntimeException.getHttpStatus();
	}

	private ApiError() {
		timestamp = OffsetDateTime.now();
	}

	public ApiError( HttpStatus httpStatus ) {
		this();
		this.httpStatus = httpStatus;
	}

	public ApiError( HttpStatus httpStatus, Throwable ex ) {
		this();
		this.httpStatus = httpStatus;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ApiError( HttpStatus httpStatus, String message, Throwable ex ) {
		this();
		this.httpStatus = httpStatus;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	// - G E T T E R S   &   S E T T E R S
	public ErrorInfo getErrorInfo() {
		return this.dimensinfinRuntimeException.getErrorInfo();
	}

	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	public String getMessage() {
		return this.dimensinfinRuntimeException.getMessage();
	}
}
