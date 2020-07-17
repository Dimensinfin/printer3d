package org.dimensinfin.core.exception;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import org.springframework.http.HttpStatus;

public class DimensinfinError {
	private String errorName;
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	private String errorCode;
	private String message;
	private String cause;

	// - C O N S T R U C T O R S
	private DimensinfinError() {}

	// - G E T T E R S   &   S E T T E R S
	@SerializedName("cause")
	public String getCause() {
		return this.cause;
	}

	public DimensinfinError setCause( final String cause ) {
		this.cause = cause;
		return this;
	}

	@SerializedName("errorCode")
	public String getErrorCode() {
		return this.errorCode;
	}

	@SerializedName("errorName")
	public String getErrorName() {
		return this.errorName;
	}

	@SerializedName("message")
	public String getMessage() {
		return this.message;
	}

	@SerializedName("status")
	public HttpStatus getStatus() {
		return this.status;
	}

	public DimensinfinError setStatus( final HttpStatus status ) {
		this.status = status;
		return this;
	}

	@SerializedName("statusCode")
	public int getStatusCode() {
		return this.status.value();
	}

	@SerializedName("statusName")
	public String getStatusName() {
		return this.status.name();
	}

	public DimensinfinError updateMessage( final String newMessage ) {
		this.message = newMessage;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private final DimensinfinError onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new DimensinfinError();
		}

		public DimensinfinError build() {
			Objects.requireNonNull( this.onConstruction.errorName );
			Objects.requireNonNull( this.onConstruction.errorCode );
			Objects.requireNonNull( this.onConstruction.message );
			return this.onConstruction;
		}

		public DimensinfinError.Builder withCause( final String cause ) {
			if (null != cause) this.onConstruction.cause = cause;
			return this;
		}

		public DimensinfinError.Builder withErrorCode( final String errorCode ) {
			this.onConstruction.errorCode = Objects.requireNonNull( errorCode );
			return this;
		}

		public DimensinfinError.Builder withErrorName( final String errorName ) {
			this.onConstruction.errorName = Objects.requireNonNull( errorName );
			return this;
		}

		public DimensinfinError.Builder withHttpStatus( final HttpStatus httpStatus ) {
			if (null != httpStatus) this.onConstruction.status = Objects.requireNonNull( httpStatus );
			return this;
		}

		public DimensinfinError.Builder withMessage( final String message ) {
			this.onConstruction.message = Objects.requireNonNull( message );
			return this;
		}
	}
}
