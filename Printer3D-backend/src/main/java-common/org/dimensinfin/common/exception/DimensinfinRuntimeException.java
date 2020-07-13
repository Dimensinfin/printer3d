package org.dimensinfin.common.exception;

import com.google.gson.annotations.SerializedName;
import org.springframework.http.HttpStatus;

public class DimensinfinRuntimeException extends RuntimeException {
	private DimensinfinError error;
	private RestExceptionResponse exceptionResponse;

	// - C O N S T R U C T O R S
	public DimensinfinRuntimeException( final String errorMessage ) {
		this( DimensinfinErrorInfo.RUNTIME_INTERNAL_ERROR( errorMessage ) );
	}

	public DimensinfinRuntimeException( final DimensinfinError error ) {
		this.error = error;
	}

	public DimensinfinRuntimeException( final RestExceptionResponse exceptionResponse ) {
		this.exceptionResponse = exceptionResponse;
		this.error = null;
	}

	public DimensinfinRuntimeException( final DimensinfinError error, final String cause ) {
		this.error = error;
		if (null != cause) this.error.setCause( cause );
	}

	// - G E T T E R S   &   S E T T E R S
	@SerializedName("cause")
	public String getCauseMessage() {
		if (null != this.error) return this.error.getCause();
		if (null != this.exceptionResponse) return this.exceptionResponse.getCause();
		else return null;
	}

	@SerializedName("errorCode")
	public String getErrorCode() {
		if (null != this.error) return this.error.getErrorCode();
		if (null != this.exceptionResponse) return this.exceptionResponse.getErrorCode();
		else return null;
	}

	@SerializedName("errorName")
	public String getErrorName() {
		if (null != this.error) return this.error.getErrorName();
		if (null != this.exceptionResponse) return this.exceptionResponse.getErrorName();
		else return null;
	}

	@SerializedName("message")
	public String getMessage() {
		if (null != this.error) return this.error.getMessage();
		if (null != this.exceptionResponse) return this.exceptionResponse.getMessage();
		else return super.getMessage();
	}

	@SerializedName("status")
	public HttpStatus getStatus() {
		if (null != this.error) return this.error.getStatus();
		if (null != this.exceptionResponse) return HttpStatus.valueOf( this.exceptionResponse.getHttpStatusCode() );
		else return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@SerializedName("statusCode")
	public int getStatusCode() {
		if (null != this.error) return this.error.getStatusCode();
		if (null != this.exceptionResponse) return this.exceptionResponse.getHttpStatusCode();
		else return HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

	@SerializedName("statusName")
	public String getStatusName() {
		if (null != this.error) return this.error.getStatusName();
		if (null != this.exceptionResponse) return this.exceptionResponse.getHttpStatus();
		else return HttpStatus.INTERNAL_SERVER_ERROR.name();
	}
}
