package org.dimensinfin.printer3d.backend.support.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"timestamp",
		"status",
		"error",
		"errors",
		"message",
		"path"
})
public class RestExceptionMessage {

	@JsonProperty("timestamp")
	private String timestamp;
	@JsonProperty("status")
	private Integer status;
	@JsonProperty("error")
	private String error;
	@JsonProperty("errors")
	private List<Error> errors = null;
	@JsonProperty("message")
	private String message;
	@JsonProperty("path")
	private String path;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("timestamp")
	public String getTimestamp() {
		return timestamp;
	}

	@JsonProperty("timestamp")
	public void setTimestamp( String timestamp ) {
		this.timestamp = timestamp;
	}

	@JsonProperty("status")
	public Integer getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus( Integer status ) {
		this.status = status;
	}

	@JsonProperty("error")
	public String getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError( String error ) {
		this.error = error;
	}

	@JsonProperty("errors")
	public List<Error> getErrors() {
		return errors;
	}

	@JsonProperty("errors")
	public void setErrors( List<Error> errors ) {
		this.errors = errors;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage( String message ) {
		this.message = message;
	}

	@JsonProperty("path")
	public String getPath() {
		return path;
	}

	@JsonProperty("path")
	public void setPath( String path ) {
		this.path = path;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty( String name, Object value ) {
		this.additionalProperties.put( name, value );
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
			"codes",
			"arguments",
			"defaultMessage",
			"objectName",
			"field",
			"rejectedValue",
			"bindingFailure",
			"code"
	})
	public class Error {

		@JsonProperty("codes")
		private List<String> codes = null;
		@JsonProperty("arguments")
		private List<Argument> arguments = null;
		@JsonProperty("defaultMessage")
		private String defaultMessage;
		@JsonProperty("objectName")
		private String objectName;
		@JsonProperty("field")
		private String field;
		@JsonProperty("rejectedValue")
		private Object rejectedValue;
		@JsonProperty("bindingFailure")
		private Boolean bindingFailure;
		@JsonProperty("code")
		private String code;
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		@JsonProperty("codes")
		public List<String> getCodes() {
			return codes;
		}

		@JsonProperty("codes")
		public void setCodes( List<String> codes ) {
			this.codes = codes;
		}

		@JsonProperty("arguments")
		public List<Argument> getArguments() {
			return arguments;
		}

		@JsonProperty("arguments")
		public void setArguments( List<Argument> arguments ) {
			this.arguments = arguments;
		}

		@JsonProperty("defaultMessage")
		public String getDefaultMessage() {
			return defaultMessage;
		}

		@JsonProperty("defaultMessage")
		public void setDefaultMessage( String defaultMessage ) {
			this.defaultMessage = defaultMessage;
		}

		@JsonProperty("objectName")
		public String getObjectName() {
			return objectName;
		}

		@JsonProperty("objectName")
		public void setObjectName( String objectName ) {
			this.objectName = objectName;
		}

		@JsonProperty("field")
		public String getField() {
			return field;
		}

		@JsonProperty("field")
		public void setField( String field ) {
			this.field = field;
		}

		@JsonProperty("rejectedValue")
		public Object getRejectedValue() {
			return rejectedValue;
		}

		@JsonProperty("rejectedValue")
		public void setRejectedValue( Object rejectedValue ) {
			this.rejectedValue = rejectedValue;
		}

		@JsonProperty("bindingFailure")
		public Boolean getBindingFailure() {
			return bindingFailure;
		}

		@JsonProperty("bindingFailure")
		public void setBindingFailure( Boolean bindingFailure ) {
			this.bindingFailure = bindingFailure;
		}

		@JsonProperty("code")
		public String getCode() {
			return code;
		}

		@JsonProperty("code")
		public void setCode( String code ) {
			this.code = code;
		}

		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		@JsonAnySetter
		public void setAdditionalProperty( String name, Object value ) {
			this.additionalProperties.put( name, value );
		}

	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
			"codes",
			"arguments",
			"defaultMessage",
			"code"
	})
	public static class Argument {

		@JsonProperty("codes")
		private List<String> codes = null;
		@JsonProperty("arguments")
		private Object arguments;
		@JsonProperty("defaultMessage")
		private String defaultMessage;
		@JsonProperty("code")
		private String code;
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		@JsonProperty("codes")
		public List<String> getCodes() {
			return codes;
		}

		@JsonProperty("codes")
		public void setCodes( List<String> codes ) {
			this.codes = codes;
		}

		@JsonProperty("arguments")
		public Object getArguments() {
			return arguments;
		}

		@JsonProperty("arguments")
		public void setArguments( Object arguments ) {
			this.arguments = arguments;
		}

		@JsonProperty("defaultMessage")
		public String getDefaultMessage() {
			return defaultMessage;
		}

		@JsonProperty("defaultMessage")
		public void setDefaultMessage( String defaultMessage ) {
			this.defaultMessage = defaultMessage;
		}

		@JsonProperty("code")
		public String getCode() {
			return code;
		}

		@JsonProperty("code")
		public void setCode( String code ) {
			this.code = code;
		}

		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

		@JsonAnySetter
		public void setAdditionalProperty( String name, Object value ) {
			this.additionalProperties.put( name, value );
		}

	}
}
