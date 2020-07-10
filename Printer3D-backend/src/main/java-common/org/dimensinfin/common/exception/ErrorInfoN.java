package org.dimensinfin.common.exception;

import java.util.Objects;

import org.springframework.http.HttpStatus;

public class ErrorInfoN {
	private String errorName;
	private HttpStatus status;
	private String errorCode ;
	private String message;

	// - C O N S T R U C T O R S
	private ErrorInfoN() {}

	// - G E T T E R S   &   S E T T E R S

	public String getErrorName() {
		return this.errorName;
	}

	public HttpStatus getStatus() {
		return this.status;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getMessage() {
		return this.message;
	}

	// - B U I L D E R
	public static class Builder {
		private final ErrorInfoN onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new ErrorInfoN();
		}

		public ErrorInfoN build() {
			return this.onConstruction;
		}

		public ErrorInfoN.Builder withErrorName( final String errorName ) {
			this.onConstruction.errorName = Objects.requireNonNull( errorName );
			return this;
		}

		public ErrorInfoN.Builder withErrorCode( final String errorCode ) {
			this.onConstruction.errorCode = Objects.requireNonNull( errorCode );
			return this;
		}

		public ErrorInfoN.Builder withHttpStatus( final HttpStatus httpStatus ) {
			this.onConstruction.status = Objects.requireNonNull( httpStatus );
			return this;
		}

		public ErrorInfoN.Builder withMessage( final String message ) {
			this.onConstruction.message = Objects.requireNonNull( message );
			return this;
		}
	}
}
