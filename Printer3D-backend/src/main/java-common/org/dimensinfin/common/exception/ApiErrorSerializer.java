package org.dimensinfin.common.exception;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class ApiErrorSerializer extends JsonSerializer<ApiError> {
	@Override
	public void serialize( final ApiError value, final JsonGenerator jgen, final SerializerProvider provider ) throws IOException {
		jgen.writeStartObject();

		jgen.writeNumberField( "httpStatus", value.getHttpStatus().value() );
		jgen.writeStringField( "httpStatusName", value.getHttpStatus().name() );
		jgen.writeStringField( "errorInfo", value.getErrorInfo().name() );
		jgen.writeStringField( "errorCode", value.getErrorInfo().errorCode );
		jgen.writeStringField( "message", value.getMessage() );
		//		jgen.writeObjectField( "trace", this.generateTrace( value ) );
		jgen.writeStringField( "timestamp", OffsetDateTime.now().toString() );

		jgen.writeEndObject();
	}

	//	private List<String> generateTrace( final InnoDentalRuntimeException exception ) {
	//		List<String> traceList = new ArrayList<>();
	//		final StackTraceElement[] stackTrace = exception.getStackTrace();
	//		for (int i = 0; i < stackTrace.length; i++)
	//			traceList.add( stackTrace[i].toString() );
	//		return traceList;
	//	}
}
