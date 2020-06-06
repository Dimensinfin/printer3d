package org.dimensinfin.printer3d.backend.support.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dimensinfin.logging.LogWrapper;

public class LogWrapperLocal extends LogWrapper {
	private static final Logger logger = LoggerFactory.getLogger( LogWrapperLocal.class );

	public static void error( final Exception exception ) {
		final String headerMessage = header();
		if (null != exception)
			if (null != exception.getMessage())
				logger.error( ">E {}x {}", headerMessage, exception.getMessage() );
			else
				logger.error( ">E {}x {}", headerMessage, exception.toString() );
		final String trace = defaultExceptionLogAction( exception );
		logger.debug( trace );
	}

	private static String header() {
		return wrapper( generateCaller() );
	}

	private static String wrapper( final String data ) {
		return "[" + data + "]";
	}

	private static String generateCaller() {
		StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
		int androidDisplacement = 0;
		if (traceElements[0].getMethodName().equalsIgnoreCase( "getThreadStackTrace" ))
			androidDisplacement = 1;
		final StackTraceElement element = traceElements[4 + androidDisplacement];
		return element.getClassName().substring( element.getClassName().lastIndexOf( '.' ) + 1 ) + "." +
				element.getMethodName();
	}
}
