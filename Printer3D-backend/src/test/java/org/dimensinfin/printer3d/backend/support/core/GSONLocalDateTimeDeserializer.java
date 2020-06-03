package org.dimensinfin.printer3d.backend.support.core;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GSONLocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
	@Override
	public LocalDateTime deserialize(
			JsonElement element,
			Type arg1,
			JsonDeserializationContext arg2 ) throws JsonParseException {
		String date = element.getAsString();
		return LocalDateTime.parse(date);
	}
}
