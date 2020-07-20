package org.dimensinfin.printer3d.backend.support.core;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GSONOffsetDateTimeDeserializer implements JsonDeserializer<OffsetDateTime> {
	@Deprecated
	@Override
	public OffsetDateTime deserialize(
			JsonElement element,
			Type arg1,
			JsonDeserializationContext arg2 ) throws JsonParseException {
		String date = element.getAsString();
		return OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
}
