package org.dimensinfin.printer3d.backend.support.core;

import com.google.gson.Gson;

import org.dimensinfin.common.exception.RestExceptionResponse;
import org.dimensinfin.core.interfaces.Converter;

public class RestExceptionMessageConverter implements Converter<String, RestExceptionResponse> {
	@Override
	public RestExceptionResponse convert( final String input ) {
		return new Gson().fromJson( input, RestExceptionResponse.class );
	}
}
