package org.dimensinfin.printer3d.backend.inventory.machine.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Machinev2;

@JsonComponent
public class MachineV2Serializer extends JsonSerializer<Machinev2> {
	@Override
	public void serialize( final Machinev2 value, final JsonGenerator jgen, final SerializerProvider serializers ) throws IOException {
		jgen.writeStartObject();

		if (null != value.getId()) jgen.writeStringField( "id", value.getId().toString() );
		if (null != value.getLabel()) jgen.writeStringField( "label", value.getLabel() );
		if (null != value.getModel()) jgen.writeStringField( "model", value.getModel() );
		if (null != value.getCharacteristics()) jgen.writeStringField( "characteristics", value.getCharacteristics() );
		if (null != value.getBuildRecord()) jgen.writeObjectField( "buildRecord", value.getBuildRecord() );

		jgen.writeEndObject();
	}
}
