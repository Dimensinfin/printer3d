package org.dimensinfin.printer3d.backend.support;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.acceptance.support.CommonWorld;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;
import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;
import org.dimensinfin.printer3d.client.domain.PartList;
import org.dimensinfin.printer3d.client.domain.RollList;

public class Printer3DWorld extends CommonWorld {
	private Part part;
	private ResponseEntity<Part> newPartResponseEntity;
	private ResponseEntity<PartList> partListResponseEntity;
	private Roll roll;
	private ResponseEntity<Roll> newRollResponseEntity;
	private ResponseEntity<RollList> rollListResponseEntity;
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

	public ResponseEntity<Roll> getNewRollResponseEntity() {
		return this.newRollResponseEntity;
	}

	public Printer3DWorld setNewRollResponseEntity( final ResponseEntity<Roll> newRollResponseEntity ) {
		this.newRollResponseEntity = newRollResponseEntity;
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

	public Roll getRoll() {
		return this.roll;
	}

	public Printer3DWorld setRoll( final Roll roll ) {
		this.roll = roll;
		return this;
	}

	public ResponseEntity<RollList> getRollListResponseEntity() {
		return this.rollListResponseEntity;
	}

	public Printer3DWorld setRollListResponseEntity( final ResponseEntity<RollList> rollListResponseEntity ) {
		this.rollListResponseEntity = rollListResponseEntity;
		return this;
	}
}
