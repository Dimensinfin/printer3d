package org.dimensinfin.printer3d.backend.production.request.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;

@JsonComponent
public class PartRequestSerializer extends JsonSerializer<PartRequest> {

	@Override
	public void serialize( final PartRequest value, final JsonGenerator jgen, final SerializerProvider serializers ) throws IOException {
		jgen.writeStartObject();

		if (null != value.getPartId()) jgen.writeStringField( "partId", value.getPartId().toString() );
		if (null != value.getQuantity()) jgen.writeNumberField( "quantity", value.getQuantity() );

		jgen.writeEndObject();

	}
}
