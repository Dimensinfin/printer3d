package org.dimensinfin.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"timestamp",
		"httpStatusName",
		"errorCode",
		"httpStatus",
		"errorInfo",
		"httpStatusCode",
		"message"
})
public class RestExceptionResponse {

	@JsonProperty("timestamp")
	private String timestamp;
	@JsonProperty("httpStatusName")
	private String httpStatusName;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("httpStatus")
	private String httpStatus;
	@JsonProperty("errorName")
	private String errorName;
	@JsonProperty("httpStatusCode")
	private int httpStatusCode;
	@JsonProperty("message")
	private String message;
	@JsonProperty("cause")
	private String cause;

	// - G E T T E R S   &   S E T T E R S
	@JsonProperty("cause")
	public String getCause() {
		return this.cause;
	}

	@JsonProperty("message")
	public void setCause( final String cause ) {
		this.cause = cause;
	}

	@JsonProperty("errorCode")
	public String getErrorCode() {
		return errorCode;
	}

	@JsonProperty("errorCode")
	public void setErrorCode( String errorCode ) {
		this.errorCode = errorCode;
	}

	@JsonProperty("errorInfo")
	public String getErrorName() {
		return errorName;
	}

	@JsonProperty("errorInfo")
	public void setErrorName( String errorName ) {
		this.errorName = errorName;
	}

	@JsonProperty("httpStatus")
	public String getHttpStatus() {
		return httpStatus;
	}

	@JsonProperty("httpStatus")
	public void setHttpStatus( String httpStatus ) {
		this.httpStatus = httpStatus;
	}

	@JsonProperty("httpStatusCode")
	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	@JsonProperty("httpStatusCode")
	public void setHttpStatusCode( int httpStatusCode ) {
		this.httpStatusCode = httpStatusCode;
	}

	@JsonProperty("httpStatusName")
	public String getHttpStatusName() {
		return httpStatusName;
	}

	@JsonProperty("httpStatusName")
	public void setHttpStatusName( String httpStatusName ) {
		this.httpStatusName = httpStatusName;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage( String message ) {
		this.message = message;
	}

	@JsonProperty("timestamp")
	public String getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp( String timestamp ) {
		this.timestamp = timestamp;
	}

	public RestExceptionResponse withCause( final String cause ) {
		this.cause = cause;
		return this;
	}

	public RestExceptionResponse withErrorCode( String errorCode ) {
		this.errorCode = errorCode;
		return this;
	}

	public RestExceptionResponse withErrorInfo( String errorInfo ) {
		this.errorName = errorInfo;
		return this;
	}

	public RestExceptionResponse withHttpStatus( String httpStatus ) {
		this.httpStatus = httpStatus;
		return this;
	}

	public RestExceptionResponse withHttpStatusCode( int httpStatusCode ) {
		this.httpStatusCode = httpStatusCode;
		return this;
	}

	public RestExceptionResponse withHttpStatusName( String httpStatusName ) {
		this.httpStatusName = httpStatusName;
		return this;
	}

	public RestExceptionResponse withMessage( String message ) {
		this.message = message;
		return this;
	}

	public RestExceptionResponse withTimestamp( String timestamp ) {
		this.timestamp = timestamp;
		return this;
	}
}
