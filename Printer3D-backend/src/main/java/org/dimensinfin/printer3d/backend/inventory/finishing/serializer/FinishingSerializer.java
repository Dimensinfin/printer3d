package org.dimensinfin.printer3d.backend.inventory.finishing.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Finishing;

@JsonComponent
public class FinishingSerializer extends JsonSerializer<Finishing> {
	@Override
	public void serialize( final Finishing value, final JsonGenerator jgen, final SerializerProvider serializers ) throws IOException {
		jgen.writeStartObject();

		jgen.writeStringField( "material", value.getMaterial() );
		jgen.writeObjectField( "colors", value.getColors() );

		jgen.writeEndObject();

	}
}
