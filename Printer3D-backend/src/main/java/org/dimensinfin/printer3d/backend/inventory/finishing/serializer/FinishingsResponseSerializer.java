package org.dimensinfin.printer3d.backend.inventory.finishing.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.printer3d.client.domain.FinishingsResponse;

@JsonComponent
public class FinishingsResponseSerializer extends JsonSerializer<FinishingsResponse> {
	@Override
	public void serialize( final FinishingsResponse value, final JsonGenerator jgen, final SerializerProvider serializers ) throws IOException {
		jgen.writeStartObject();

		jgen.writeObjectField( "materials", value.getMaterials() );

		jgen.writeEndObject();

	}
}
