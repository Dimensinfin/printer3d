package org.dimensinfin.printer3d.backend.support.core;
import com.google.gson.Gson;

import org.dimensinfin.core.interfaces.Converter;

public class AppErrorInfoConverter implements Converter<String, AppErrorInfo> {
	@Override
	public AppErrorInfo convert( final String input ) {
		return new Gson().fromJson( input, AppErrorInfo.class );
	}
}
