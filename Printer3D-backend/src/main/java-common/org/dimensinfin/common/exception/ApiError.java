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
		return this.dimensinfinRuntimeException.getErrorCode();
	}

	public String getErrorInfo() {
		return this.dimensinfinRuntimeException.getErrorName();
	}

	public String getHttpStatus() {
		return this.dimensinfinRuntimeException.getStatus().toString();
	}

	public Integer getHttpStatusCode() {
		return this.dimensinfinRuntimeException.getStatus().value();
	}

	public String getHttpStatusName() {
		return this.dimensinfinRuntimeException.getStatus().name();
	}

	public String getMessage() {
		return this.dimensinfinRuntimeException.getMessage();
	}

	public OffsetDateTime getTimestamp() {
		return this.timestamp;
	}
}
