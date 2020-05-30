package org.dimensinfin.printer3d.backend.support;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.acceptance.support.CommonWorld;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;
import org.dimensinfin.printer3d.client.domain.PartList;
import org.dimensinfin.printer3d.client.domain.CoilList;

public class Printer3DWorld extends CommonWorld {
	private Part part;
	private ResponseEntity<Part> newPartResponseEntity;
	private ResponseEntity<PartList> partListResponseEntity;
	private Coil coil;
	private ResponseEntity<Coil> newCoilResponseEntity;
	private ResponseEntity<CoilList> coilListResponseEntity;
	private ResponseEntity<FinishingsResponse> finishingsResponseEntity;

	// - G E T T E R S   &   S E T T E R S
	public ResponseEntity<FinishingsResponse> getFinishingsResponseEntity() {
		return this.finishingsResponseEntity;
	}

	public Printer3DWorld setFinishingsResponseEntity( final ResponseEntity<FinishingsResponse> finishingsResponseEntity ) {
		this.finishingsResponseEntity = finishingsResponseEntity;
		return this;
	}

	public ResponseEntity<Part> getNewPartResponseEntity() {
		return this.newPartResponseEntity;
	}

	public Printer3DWorld setNewPartResponseEntity( final ResponseEntity<Part> newPartResponseEntity ) {
		this.newPartResponseEntity = newPartResponseEntity;
		return this;
	}

	public ResponseEntity<Coil> getNewCoilResponseEntity() {
		return this.newCoilResponseEntity;
	}

	public Printer3DWorld setNewCoilResponseEntity( final ResponseEntity<Coil> newCoilResponseEntity ) {
		this.newCoilResponseEntity = newCoilResponseEntity;
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

	public Coil getCoil() {
		return this.coil;
	}

	public Printer3DWorld setCoil( final Coil coil ) {
		this.coil = coil;
		return this;
	}

	public ResponseEntity<CoilList> getCoilListResponseEntity() {
		return this.coilListResponseEntity;
	}

	public Printer3DWorld setCoilListResponseEntity( final ResponseEntity<CoilList> coilListResponseEntity ) {
		this.coilListResponseEntity = coilListResponseEntity;
		return this;
	}
}
