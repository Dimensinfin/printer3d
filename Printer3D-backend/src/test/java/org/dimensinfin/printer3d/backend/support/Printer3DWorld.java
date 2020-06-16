package org.dimensinfin.printer3d.backend.support;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.acceptance.support.CommonWorld;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;

public class Printer3DWorld extends CommonWorld {
	private Part part;
	private ResponseEntity<Part> newPartResponseEntity;
	private ResponseEntity<Part> updatePartResponseEntity;
	private ResponseEntity<PartList> partListResponseEntity;
	private Coil coil;
	private ResponseEntity<Coil> newCoilResponseEntity;
	private ResponseEntity<CoilList> coilListResponseEntity;
	private ResponseEntity<FinishingsResponse> finishingsResponseEntity;
	private ResponseEntity<MachineList> machineListResponseEntity;
	private ResponseEntity<MachineListV2> machineListv2ResponseEntity;
	private UUID partId;
	private UUID machineId;
	private ResponseEntity<Machine> startBuildResponseEntity;
	private ResponseEntity<List<Job>> jobListResponseEntity;
	private NewModelRequest newModelRequest;
	private ResponseEntity<Model> modelResponseEntity;
	private List<PartRequest> partRequestList;
	private UUID modelId;
	private Request newRequest;
	private ResponseEntity<Request> requestResponseEntity;

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

	public ResponseEntity<MachineListV2> getMachineListv2ResponseEntity() {
		return this.machineListv2ResponseEntity;
	}

	public Printer3DWorld setMachineListv2ResponseEntity( final ResponseEntity<MachineListV2> machineListv2ResponseEntity ) {
		this.machineListv2ResponseEntity = machineListv2ResponseEntity;
		return this;
	}

	public UUID getModelId() {
		return this.modelId;
	}

	public Printer3DWorld setModelId( final UUID modelId ) {
		this.modelId = modelId;
		return this;
	}

	public ResponseEntity<Model> getModelResponseEntity() {
		return this.modelResponseEntity;
	}

	public Printer3DWorld setModelResponseEntity( final ResponseEntity<Model> modelResponseEntity ) {
		this.modelResponseEntity = modelResponseEntity;
		return this;
	}

	public ResponseEntity<Coil> getNewCoilResponseEntity() {
		return this.newCoilResponseEntity;
	}

	public Printer3DWorld setNewCoilResponseEntity( final ResponseEntity<Coil> newCoilResponseEntity ) {
		this.newCoilResponseEntity = newCoilResponseEntity;
		return this;
	}

	public NewModelRequest getNewModelRequest() {
		return this.newModelRequest;
	}

	public Printer3DWorld setNewModelRequest( final NewModelRequest newModelRequest ) {
		this.newModelRequest = newModelRequest;
		return this;
	}

	public ResponseEntity<Part> getNewPartResponseEntity() {
		return this.newPartResponseEntity;
	}

	public Printer3DWorld setNewPartResponseEntity( final ResponseEntity<Part> newPartResponseEntity ) {
		this.newPartResponseEntity = newPartResponseEntity;
		return this;
	}

	public Request getNewRequest() {
		return this.newRequest;
	}

	public Printer3DWorld setNewRequest( final Request newRequest ) {
		this.newRequest = newRequest;
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

	public List<PartRequest> getPartRequestList() {
		return this.partRequestList;
	}

	public Printer3DWorld setPartRequestList( final List<PartRequest> partRequestList ) {
		this.partRequestList = partRequestList;
		return this;
	}

	public ResponseEntity<Request> getRequestResponseEntity() {
		return this.requestResponseEntity;
	}

	public Printer3DWorld setRequestResponseEntity( final ResponseEntity<Request> requestResponseEntity ) {
		this.requestResponseEntity = requestResponseEntity;
		return this;
	}

	public ResponseEntity<Machine> getStartBuildResponseEntity() {
		return this.startBuildResponseEntity;
	}

	public Printer3DWorld setStartBuildResponseEntity( final ResponseEntity<Machine> startBuildResponseEntity ) {
		this.startBuildResponseEntity = startBuildResponseEntity;
		return this;
	}

	public ResponseEntity<Part> getUpdatePartResponseEntity() {
		return this.updatePartResponseEntity;
	}

	public Printer3DWorld setUpdatePartResponseEntity( final ResponseEntity<Part> updatePartResponseEntity ) {
		this.updatePartResponseEntity = updatePartResponseEntity;
		return this;
	}
}
