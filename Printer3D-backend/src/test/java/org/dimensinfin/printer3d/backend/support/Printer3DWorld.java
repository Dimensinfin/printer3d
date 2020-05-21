package org.dimensinfin.printer3d.backend.support;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.Part;

public class Printer3DWorld {
	private HttpStatus httpStatus;

	public String getJwtAuthorizationToken() {
		return this.jwtAuthorizationToken;
	}

	public Printer3DWorld setJwtAuthorizationToken( final String jwtAuthorizationToken ) {
		this.jwtAuthorizationToken = jwtAuthorizationToken;
		return this;
	}

	private String jwtAuthorizationToken;
	private Part part;

	public ResponseEntity<Part> getNewPartResponseEntity() {
		return this.newPartResponseEntity;
	}

	public Printer3DWorld setNewPartResponseEntity( final ResponseEntity<Part> newPartResponseEntity ) {
		this.newPartResponseEntity = newPartResponseEntity;
		return this;
	}

	private ResponseEntity<Part> newPartResponseEntity;

	// - G E T T E R S   &   S E T T E R S
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	public Printer3DWorld setHttpStatus( final HttpStatus httpStatus ) {
		this.httpStatus = httpStatus;
		return this;
	}

	public Part getPart() {
		return this.part;
	}

	public Printer3DWorld setPart( final Part part ) {
		this.part = part;
		return this;
	}
}
