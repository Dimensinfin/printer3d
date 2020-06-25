package org.dimensinfin.common.exception;

import java.time.OffsetDateTime;

public class ApiError {
	private DimensinfinRuntimeException dimensinfinRuntimeException;
	private OffsetDateTime timestamp = OffsetDateTime.now();

	// - C O N S T R U C T O R S
	public ApiError( final DimensinfinRuntimeException dimensinfinRuntimeException ) {
		this.dimensinfinRuntimeException = dimensinfinRuntimeException;
	}

	// - G E T T E R S   &   S E T T E R S
	public String getErrorCode() {
		return this.dimensinfinRuntimeException.getErrorInfo().errorCode;
	}

	public String getErrorInfo() {
		return this.dimensinfinRuntimeException.getErrorInfo().name();
	}

	public String getHttpStatus() {
		return this.dimensinfinRuntimeException.getHttpStatus().toString();
	}

	public Integer getHttpStatusCode() {
		return this.dimensinfinRuntimeException.getHttpStatus().value();
	}

	public String getHttpStatusName() {
		return this.dimensinfinRuntimeException.getHttpStatus().name();
	}

	public String getMessage() {
		return this.dimensinfinRuntimeException.getMessage();
	}

	public OffsetDateTime getTimestamp() {
		return this.timestamp;
	}
}
