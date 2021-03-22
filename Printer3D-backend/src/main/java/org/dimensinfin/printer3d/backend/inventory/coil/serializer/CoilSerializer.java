package org.dimensinfin.printer3d.backend.inventory.coil.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.14.0
 */
@JsonComponent
public class CoilSerializer extends JsonSerializer<Coil> {

	@Override
	public void serialize( final Coil value, final JsonGenerator gen, final SerializerProvider serializers ) throws IOException {
		gen.writeStartObject();

		gen.writeStringField( "id", value.getId().toString() );
		gen.writeStringField( "material", value.getMaterial() );
		gen.writeStringField( "color", value.getColor() );
		gen.writeStringField( "colorSet", value.getColorSet() );
		gen.writeBooleanField( "active", value.getActive() );

		gen.writeEndObject();

	}
}