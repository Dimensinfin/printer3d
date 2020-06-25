package org.dimensinfin.common.exception;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class DimensinfinRuntimeExceptionSerializer extends JsonSerializer<DimensinfinRuntimeException> {
	@Override
	public void serialize( final DimensinfinRuntimeException value, final JsonGenerator jgen, final SerializerProvider provider ) throws IOException {
		jgen.writeStartObject();

		jgen.writeStringField( "httpStatus", value.getHttpStatus().toString() );
		jgen.writeNumberField( "httpStatusCode", value.getHttpStatus().value() );
		jgen.writeStringField( "httpStatusName", value.getHttpStatus().name() );
		jgen.writeStringField( "errorInfo", value.getErrorInfo().name() );
		jgen.writeStringField( "errorCode", value.getErrorInfo().errorCode );
		jgen.writeStringField( "message", value.getMessage() );
		jgen.writeStringField( "timestamp", OffsetDateTime.now().toString() );

		jgen.writeEndObject();
	}
}
