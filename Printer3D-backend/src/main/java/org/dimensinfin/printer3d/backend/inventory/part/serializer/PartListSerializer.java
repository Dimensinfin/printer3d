package org.dimensinfin.printer3d.backend.inventory.part.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;

@JsonComponent
public class PartListSerializer extends JsonSerializer<PartList> {
	@Override
	public void serialize( final PartList value, final JsonGenerator jgen, final SerializerProvider serializers ) throws IOException {
		jgen.writeStartObject();

		jgen.writeStringField( "jsonClass", "PartList" );
		jgen.writeNumberField( "count", value.getCount() );
		jgen.writeObjectField( "parts", value.getParts() );

		jgen.writeEndObject();
	}
}
