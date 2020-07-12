package org.dimensinfin.common.exception;

import java.time.OffsetDateTime;
import java.util.Objects;

public class ApiError {
	private final DimensinfinRuntimeException dimensinfinRuntimeException;

	// - C O N S T R U C T O R S
	public ApiError( final DimensinfinRuntimeException dimensinfinRuntimeException ) {
		this.dimensinfinRuntimeException = Objects.requireNonNull( dimensinfinRuntimeException );
	}

	// - G E T T E R S   &   S E T T E R S
	public String getCause() {
		return this.dimensinfinRuntimeException.getCauseMessage();
	}

	public String getErrorCode() {
		return this.dimensinfinRuntimeException.getErrorCode();
	}

	public String getErrorName() {
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
		return OffsetDateTime.now();
	}
}
