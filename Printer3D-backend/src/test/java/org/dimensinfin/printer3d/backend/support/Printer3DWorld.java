package org.dimensinfin.printer3d.backend.support;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.acceptance.support.CommonWorld;
import org.dimensinfin.printer3d.backend.part.persistence.Part;

public class Printer3DWorld extends CommonWorld {
	private Part part;
	private ResponseEntity<Part> newPartResponseEntity;

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<Part> getNewPartResponseEntity() {
		return this.newPartResponseEntity;
	}

	public Printer3DWorld setNewPartResponseEntity( final ResponseEntity<Part> newPartResponseEntity ) {
		this.newPartResponseEntity = newPartResponseEntity;
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
