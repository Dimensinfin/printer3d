package org.dimensinfin.printer3d.backend.support.core;

import com.google.gson.Gson;

import org.dimensinfin.printer3d.client.core.dto.RestExceptionResponse;
import org.dimensinfin.core.interfaces.Converter;

public class RestExceptionMessageConverter implements Converter<String, RestExceptionResponse> {
	@Override
	public RestExceptionResponse convert( final String input ) {
		return new Gson().fromJson( input, RestExceptionResponse.class );
	}
}
