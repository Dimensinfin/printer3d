package org.dimensinfin.printer3d.backend.support;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.acceptance.support.CommonWorld;
import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.client.part.domain.PartList;

public class Printer3DWorld extends CommonWorld {
	private Part part;
	private ResponseEntity<Part> newPartResponseEntity;
	private ResponseEntity<PartList> partListResponseEntity;

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

	public ResponseEntity<PartList> getPartListResponseEntity() {
		return this.partListResponseEntity;
	}

	public Printer3DWorld setPartListResponseEntity( final ResponseEntity<PartList> partListResponseEntity ) {
		this.partListResponseEntity = partListResponseEntity;
		return this;
	}
}
