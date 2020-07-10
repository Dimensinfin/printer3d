package org.dimensinfin.printer3d.backend.support.core;

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
public class RestExceptionMessage {

	@JsonProperty("timestamp")
	private String timestamp;
	@JsonProperty("httpStatusName")
	private String httpStatusName;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("httpStatus")
	private String httpStatus;
	@JsonProperty("errorInfo")
	private String errorInfo;
	@JsonProperty("httpStatusCode")
	private int httpStatusCode;
	@JsonProperty("message")
	private String message;

	@JsonProperty("timestamp")
	public String getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public RestExceptionMessage withTimestamp(String timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	@JsonProperty("httpStatusName")
	public String getHttpStatusName() {
		return httpStatusName;
	}

	@JsonProperty("httpStatusName")
	public void setHttpStatusName(String httpStatusName) {
		this.httpStatusName = httpStatusName;
	}

	public RestExceptionMessage withHttpStatusName(String httpStatusName) {
		this.httpStatusName = httpStatusName;
		return this;
	}

	@JsonProperty("errorCode")
	public String getErrorCode() {
		return errorCode;
	}

	@JsonProperty("errorCode")
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public RestExceptionMessage withErrorCode(String errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	@JsonProperty("httpStatus")
	public String getHttpStatus() {
		return httpStatus;
	}

	@JsonProperty("httpStatus")
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public RestExceptionMessage withHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
		return this;
	}

	@JsonProperty("errorInfo")
	public String getErrorInfo() {
		return errorInfo;
	}

	@JsonProperty("errorInfo")
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public RestExceptionMessage withErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
		return this;
	}

	@JsonProperty("httpStatusCode")
	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	@JsonProperty("httpStatusCode")
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public RestExceptionMessage withHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
		return this;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}

	public RestExceptionMessage withMessage(String message) {
		this.message = message;
		return this;
	}
}
