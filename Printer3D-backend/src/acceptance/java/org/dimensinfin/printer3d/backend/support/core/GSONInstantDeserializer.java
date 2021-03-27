package org.dimensinfin.printer3d.backend.support.core;

import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GSONInstantDeserializer implements JsonDeserializer<Instant> {
	@Deprecated
	@Override
	public Instant deserialize(
			JsonElement element,
			Type arg1,
			JsonDeserializationContext arg2 ) throws JsonParseException {
		return Instant.parse( element.getAsString() );
	}
}
