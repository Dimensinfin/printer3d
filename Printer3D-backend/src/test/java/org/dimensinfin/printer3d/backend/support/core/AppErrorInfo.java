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
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	// - G E T T E R S   &   S E T T E R S
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
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
	public String getErrorInfo() {
		return errorInfo;
	}

	@JsonProperty("errorInfo")
	public void setErrorInfo( String errorInfo ) {
		this.errorInfo = errorInfo;
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
	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	@JsonProperty("httpStatusCode")
	public void setHttpStatusCode( Integer httpStatusCode ) {
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
	public Object getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp( Object timestamp ) {
		this.timestamp = timestamp;
	}

	@JsonAnySetter
	public void setAdditionalProperty( String name, Object value ) {
		this.additionalProperties.put( name, value );
	}
}
