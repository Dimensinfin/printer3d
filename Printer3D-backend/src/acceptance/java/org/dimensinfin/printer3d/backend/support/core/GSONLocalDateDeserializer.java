package org.dimensinfin.printer3d.backend.support.core;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;

public class GSONLocalDateDeserializer implements JsonDeserializer<LocalDate> {
	private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public LocalDate deserialize(
			com.google.gson.JsonElement element,
			Type arg1,
			com.google.gson.JsonDeserializationContext arg2 ) throws JsonParseException {
		String date = element.getAsString();
		return LocalDate.parse(date, format);
	}
}
