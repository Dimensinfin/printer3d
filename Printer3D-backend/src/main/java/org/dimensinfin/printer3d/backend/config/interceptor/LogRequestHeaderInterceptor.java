package org.dimensinfin.printer3d.backend.config.interceptor;

import java.text.MessageFormat;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.web.servlet.HandlerInterceptor;

import org.dimensinfin.logging.LogWrapper;

public class LogRequestHeaderInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle( @NotNull final HttpServletRequest request,
	                          @NotNull final HttpServletResponse response,
	                          @NotNull final Object handler ) {
		// Filter the requests with the OPTIONS calls because they should not be verified
		final String meth = request.getMethod();
		if (!meth.equalsIgnoreCase( "OPTIONS" )) {
			final Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				final String header = headerNames.nextElement();
				final String headerValue = request.getHeader( header );
				LogWrapper.info( MessageFormat.format( "AP [VERIFICATION   ] > {0}={1}", header, headerValue ) );
			}
		}
		return true;
	}
}
