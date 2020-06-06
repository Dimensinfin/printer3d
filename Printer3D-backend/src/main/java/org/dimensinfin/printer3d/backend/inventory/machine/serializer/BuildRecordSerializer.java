package org.dimensinfin.printer3d.backend.inventory.machine.serializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;

@JsonComponent
public class BuildRecordSerializer extends JsonSerializer<BuildRecord> {
	@Override
	public void serialize( final BuildRecord value, final JsonGenerator jgen, final SerializerProvider serializers ) throws IOException {
		jgen.writeStartObject();

		jgen.writeStringField( "state", value.getState().toString() );
		jgen.writeNumberField( "partCopies", value.getPartCopies() );
		jgen.writeObjectField( "part", value.getPart() );
		if (null != value.getJobInstallmentDate())
			jgen.writeStringField( "jobInstallmentDate", value.getJobInstallmentDate().format( DateTimeFormatter.ISO_OFFSET_DATE_TIME ) );
		else
			jgen.writeStringField( "jobInstallmentDate", null );
		jgen.writeNumberField( "remainingTime", value.getRemainingTime() );

		jgen.writeEndObject();
	}
}
