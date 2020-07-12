package org.dimensinfin.printer3d.backend.support.core;

import com.google.gson.Gson;

import org.dimensinfin.common.exception.RestExceptionMessage;
import org.dimensinfin.core.interfaces.Converter;

public class RestExceptionMessageConverter implements Converter<String, RestExceptionMessage> {
	@Override
	public RestExceptionMessage convert( final String input ) {
		return new Gson().fromJson( input, RestExceptionMessage.class );
	}
}
