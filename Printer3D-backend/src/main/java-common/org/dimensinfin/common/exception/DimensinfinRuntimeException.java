package org.dimensinfin.common.exception;

import com.google.gson.annotations.SerializedName;
import org.springframework.http.HttpStatus;

public class DimensinfinRuntimeException extends RuntimeException {
	private DimensinfinError error;

	// - C O N S T R U C T O R S
	public DimensinfinRuntimeException( final String errorMessage ) {
		this( DimensinfinErrorInfo.RUNTIME_INTERNAL_ERROR( errorMessage ) );
	}
	public DimensinfinRuntimeException( final DimensinfinError error ) {
		this.error = error;
	}

	// - G E T T E R S   &   S E T T E R S
	@SerializedName("cause")
	public String getCauseMessage() {
		if (null != this.error) return this.error.getCause();
		else return null;
	}

	@SerializedName("errorCode")
	public String getErrorCode() {
		if (null != this.error) return this.error.getErrorCode();
		else return null;
	}

	@SerializedName("errorName")
	public String getErrorName() {
		if (null != this.error) return this.error.getErrorName();
		else return null;
	}

	@SerializedName("message")
	public String getMessage() {
		if (null != this.error) return this.error.getMessage();
		else return super.getMessage();
	}

	@SerializedName("status")
	public HttpStatus getStatus() {
		if (null != this.error) return this.error.getStatus();
		else return HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@SerializedName("statusCode")
	public int getStatusCode() {
		if (null != this.error) return this.error.getStatusCode();
		else return HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

	@SerializedName("statusName")
	public String getStatusName() {
		if (null != this.error) return this.error.getStatusName();
		else return HttpStatus.INTERNAL_SERVER_ERROR.name();
	}
}
