package org.dimensinfin.printer3d.backend.config;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.config.interceptor.LogRequestHeaderInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Value("${logging.request.headers}")
	private final String logRequestHeadersFlag = "false";

	@Override
	public void addInterceptors( final InterceptorRegistry registry ) {
		LogWrapper.info( MessageFormat.format( "Configuration flag: {0} on {1}.", "logRequestHeadersFlag", this.logRequestHeadersFlag ) );
		if (this.logRequestHeadersFlag.equalsIgnoreCase( "true" ))
			registry.addInterceptor( new LogRequestHeaderInterceptor() ).addPathPatterns( "/api/**" );
	}
}
