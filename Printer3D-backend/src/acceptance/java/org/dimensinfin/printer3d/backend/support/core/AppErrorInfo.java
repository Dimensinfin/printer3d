package org.dimensinfin.printer3d.backend.support.core;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"httpStatus",
		"timestamp",
		"message",
		"httpStatusName",
		"errorInfo",
		"httpStatusCode",
		"errorCode"
})
public class AppErrorInfo {
	@JsonProperty("httpStatus")
	private String httpStatus;
	@JsonProperty("timestamp")
	private Object timestamp;
	@JsonProperty("message")
	private String message;
	@JsonProperty("httpStatusName")
	private String httpStatusName;
	@JsonProperty("errorInfo")
	private String errorInfo;
	@JsonProperty("httpStatusCode")
	private Integer httpStatusCode;
	@JsonProperty("errorCode")
	private String errorCode;
	@JsonIgnore
	private final Map<String, Object> additionalProperties = new HashMap<>();

	// - G E T T E R S   &   S E T T E R S
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonProperty("errorCode")
	public String getErrorCode() {
		return this.errorCode;
	}

	@JsonProperty("errorCode")
	public void setErrorCode( final String errorCode ) {
		this.errorCode = errorCode;
	}

	@JsonProperty("errorInfo")
	public String getErrorInfo() {
		return this.errorInfo;
	}

	@JsonProperty("errorInfo")
	public void setErrorInfo( final String errorInfo ) {
		this.errorInfo = errorInfo;
	}

	@JsonProperty("httpStatus")
	public String getHttpStatus() {
		return this.httpStatus;
	}

	@JsonProperty("httpStatus")
	public void setHttpStatus( final String httpStatus ) {
		this.httpStatus = httpStatus;
	}

	@JsonProperty("httpStatusCode")
	public Integer getHttpStatusCode() {
		return this.httpStatusCode;
	}

	@JsonProperty("httpStatusCode")
	public void setHttpStatusCode( final Integer httpStatusCode ) {
		this.httpStatusCode = httpStatusCode;
	}

	@JsonProperty("httpStatusName")
	public String getHttpStatusName() {
		return this.httpStatusName;
	}

	@JsonProperty("httpStatusName")
	public void setHttpStatusName( final String httpStatusName ) {
		this.httpStatusName = httpStatusName;
	}

	@JsonProperty("message")
	public String getMessage() {
		return this.message;
	}

	@JsonProperty("message")
	public void setMessage( final String message ) {
		this.message = message;
	}

	@JsonProperty("timestamp")
	public Object getTimestamp() {
		return this.timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp( final Object timestamp ) {
		this.timestamp = timestamp;
	}

	@JsonAnySetter
	public void setAdditionalProperty( final String name, final Object value ) {
		this.additionalProperties.put( name, value );
	}
}
