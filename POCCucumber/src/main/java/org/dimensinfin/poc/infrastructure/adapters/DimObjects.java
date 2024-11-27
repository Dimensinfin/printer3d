package org.dimensinfin.poc.infrastructure.adapters;

import java.text.MessageFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.14.0
 */
@Slf4j
@Deprecated
public class DimObjects {
	public static <T> T requireNonNull( final T target ) {
		final String message = "Null Pointer validation detected unexpected null value.";
		if ( target == null ) {
			final String callerName = generateCaller();
			final NullPointerException npe = new NullPointerException( MessageFormat.format( "[{0}]>{1}", callerName, message ) );
			log.error( npe.toString() );
			throw npe;
		} else {
			return target;
		}
	}

	public static <T> T requireNonNull( final T target, final String message ) {
		if ( target == null ) {
			final String callerName = generateCaller();
			final NullPointerException npe = new NullPointerException( MessageFormat.format( "[{0}]>{1}", callerName, message ) );
			log.error( npe.toString() );
			throw npe;
		} else {
			return target;
		}
	}

	private static String generateCaller() {
		final StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
		int androidDisplacement = 0;
		if ( traceElements[0].getMethodName().equalsIgnoreCase( "getThreadStackTrace" ) )
			androidDisplacement = 1;
		final StackTraceElement element = traceElements[4 + androidDisplacement];
		return element.getClassName().substring( element.getClassName().lastIndexOf( '.' ) + 1 ) + "." +
				element.getMethodName();
	}

	// - C O N S T R U C T O R S
	private DimObjects() {}
}