package org.dimensinfin.printer3d.backend.support;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.acceptance.support.CommonWorld;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.client.domain.CoilList;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;
import org.dimensinfin.printer3d.client.domain.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListv2;
import org.dimensinfin.printer3d.client.production.domain.Job;

public class Printer3DWorld extends CommonWorld {
	private Part part;
	private ResponseEntity<Part> newPartResponseEntity;
	private ResponseEntity<PartList> partListResponseEntity;
	private Coil coil;
	private ResponseEntity<Coil> newCoilResponseEntity;
	private ResponseEntity<CoilList> coilListResponseEntity;
	private ResponseEntity<FinishingsResponse> finishingsResponseEntity;
	private ResponseEntity<MachineList> machineListResponseEntity;
	private ResponseEntity<MachineListv2> machineListv2ResponseEntity;

	public ResponseEntity<MachineListv2> getMachineListv2ResponseEntity() {
		return this.machineListv2ResponseEntity;
	}

	public Printer3DWorld setMachineListv2ResponseEntity( final ResponseEntity<MachineListv2> machineListv2ResponseEntity ) {
		this.machineListv2ResponseEntity = machineListv2ResponseEntity;
		return this;
	}

	private UUID partId;
	private UUID machineId;
	private ResponseEntity<Machine> startBuildResponseEntity;
	private ResponseEntity<List<Job>> jobListResponseEntity;

	// - G E T T E R S   &   S E T T E R S
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

	public ResponseEntity<FinishingsResponse> getFinishingsResponseEntity() {
		return this.finishingsResponseEntity;
	}

	public Printer3DWorld setFinishingsResponseEntity( final ResponseEntity<FinishingsResponse> finishingsResponseEntity ) {
		this.finishingsResponseEntity = finishingsResponseEntity;
		return this;
	}

	public ResponseEntity<List<Job>> getJobListResponseEntity() {
		return this.jobListResponseEntity;
	}

	public Printer3DWorld setJobListResponseEntity( final ResponseEntity<List<Job>> jobListResponseEntity ) {
		this.jobListResponseEntity = jobListResponseEntity;
		return this;
	}

	public UUID getMachineId() {
		return this.machineId;
	}

	public Printer3DWorld setMachineId( final UUID machineId ) {
		this.machineId = machineId;
		return this;
	}

	public ResponseEntity<MachineList> getMachineListResponseEntity() {
		return this.machineListResponseEntity;
	}

	public Printer3DWorld setMachineListResponseEntity( final ResponseEntity<MachineList> machineListResponseEntity ) {
		this.machineListResponseEntity = machineListResponseEntity;
		return this;
	}

	public ResponseEntity<Coil> getNewCoilResponseEntity() {
		return this.newCoilResponseEntity;
	}

	public Printer3DWorld setNewCoilResponseEntity( final ResponseEntity<Coil> newCoilResponseEntity ) {
		this.newCoilResponseEntity = newCoilResponseEntity;
		return this;
	}

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

	public UUID getPartId() {
		return this.partId;
	}

	public Printer3DWorld setPartId( final UUID partId ) {
		this.partId = partId;
		return this;
	}

	public ResponseEntity<PartList> getPartListResponseEntity() {
		return this.partListResponseEntity;
	}

	public Printer3DWorld setPartListResponseEntity( final ResponseEntity<PartList> partListResponseEntity ) {
		this.partListResponseEntity = partListResponseEntity;
		return this;
	}

	public ResponseEntity<Machine> getStartBuildResponseEntity() {
		return this.startBuildResponseEntity;
	}

	public Printer3DWorld setStartBuildResponseEntity( final ResponseEntity<Machine> startBuildResponseEntity ) {
		this.startBuildResponseEntity = startBuildResponseEntity;
		return this;
	}
}
