package org.dimensinfin.printer3d.backend.inventory.machine.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;

@JsonComponent
public class MachineListV2Serializer extends JsonSerializer<MachineListV2> {
	@Override
	public void serialize( final MachineListV2 value, final JsonGenerator jgen, final SerializerProvider serializers ) throws IOException {
		jgen.writeStartObject();

		jgen.writeNumberField( "count", value.getMachines().size() );
		jgen.writeObjectField( "machines", value.getMachines() );

		jgen.writeEndObject();
	}
}
