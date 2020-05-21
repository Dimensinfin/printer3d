package org.dimensinfin.acceptance.support;

import org.springframework.http.HttpStatus;

public class CommonWorld {
	private HttpStatus httpStatus;
	private String jwtAuthorizationToken;

	// - G E T T E R S   &   S E T T E R S
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	public CommonWorld setHttpStatus( final HttpStatus httpStatus ) {
		this.httpStatus = httpStatus;
		return this;
	}

	public String getJwtAuthorizationToken() {
		return this.jwtAuthorizationToken;
	}

	public CommonWorld setJwtAuthorizationToken( final String jwtAuthorizationToken ) {
		this.jwtAuthorizationToken = jwtAuthorizationToken;
		return this;
	}
}
