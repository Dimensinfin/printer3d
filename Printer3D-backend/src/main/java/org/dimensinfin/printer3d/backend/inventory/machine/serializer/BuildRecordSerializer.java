package org.dimensinfin.printer3d.backend.inventory.machine.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildState;

@JsonComponent
public class BuildRecordSerializer extends JsonSerializer<BuildRecord> {
	@Override
	public void serialize( final BuildRecord value, final JsonGenerator jgen, final SerializerProvider serializers ) throws IOException {
		jgen.writeStartObject();

		if (null != value.getState())
			jgen.writeStringField( "state", value.getState().toString() );
		else
			jgen.writeStringField( "state", BuildState.IDLE.toString() );
		jgen.writeNumberField( "partCopies", value.getPartCopies() );
		jgen.writeObjectField( "part", value.getPart() );
		if (null != value.getJobInstallmentDate())
			jgen.writeStringField( "jobInstallmentDate", value.getJobInstallmentDate().toString() );
		else
			jgen.writeStringField( "jobInstallmentDate", null );
		jgen.writeNumberField( "remainingTime", value.getRemainingTime() );

		jgen.writeEndObject();
	}
}
