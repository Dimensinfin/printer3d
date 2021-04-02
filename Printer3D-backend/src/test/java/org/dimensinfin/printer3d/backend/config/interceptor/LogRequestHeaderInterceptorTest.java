package org.dimensinfin.printer3d.backend.config.interceptor;

import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LogRequestHeaderInterceptorTest {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Object handler;

	@BeforeEach
	public void beforeEach() {
		this.request = Mockito.mock( HttpServletRequest.class );
		this.response = Mockito.mock( HttpServletResponse.class );
		this.handler = Mockito.mock( Object.class );
	}

	@Test
	public void preHandle_Headers() {
		// Given
		final Hashtable<String, String> headers = new Hashtable<>();
		headers.put( "-HEADER-KEY", "-HEADER-VALUE-" );
		// When
		Mockito.when( this.request.getMethod() ).thenReturn( "GET" );
		Mockito.when( this.request.getHeaderNames() ).thenReturn( headers.keys() );
		Mockito.when( this.request.getHeader( Mockito.anyString() ) ).thenReturn( "-HEADER-VALUE-" );
		// Test
		final LogRequestHeaderInterceptor requestHeaderInterceptor = new LogRequestHeaderInterceptor();
		final boolean obtained = requestHeaderInterceptor.preHandle( this.request, this.response, this.handler );
		// Assertions
		Assertions.assertTrue( obtained );
	}

	@Test
	public void preHandle_OPTIONS() {
		// When
		Mockito.when( this.request.getMethod() ).thenReturn( "OPTIONS" );
		// Test
		final LogRequestHeaderInterceptor requestHeaderInterceptor = new LogRequestHeaderInterceptor();
		final boolean obtained = requestHeaderInterceptor.preHandle( this.request, this.response, this.handler );
		// Assertions
		Assertions.assertTrue( obtained );
	}
}