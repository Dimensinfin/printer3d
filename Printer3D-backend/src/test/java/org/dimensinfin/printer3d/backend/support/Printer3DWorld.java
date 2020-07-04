package org.dimensinfin.printer3d.backend.support;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.acceptance.support.CommonWorld;
import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

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
	private ResponseEntity<RequestList> requestListResponseEntity;
	private UUID requestId;
	private ResponseEntity<Request> closeRequestResponseEntity;
	private JobRequest jobRequest;
	private ResponseEntity<ModelList> modelListResponseEntity;
	private DimensinfinRuntimeException applicationException;
	private List<RequestItem> requestContents;
	private RequestV2 requestV2;
	private ResponseEntity<List<RequestV2>> listRequestV2ResponseEntity;
	private String selectionLabel;
	private UpdateGroupPartRequest updateGroupPartRequest;
	private ResponseEntity<CountResponse> updateGroupPartResponseEntity;

	// - G E T T E R S   &   S E T T E R S
	public DimensinfinRuntimeException getApplicationException() {
		return this.applicationException;
	}

	public Printer3DWorld setApplicationException( final DimensinfinRuntimeException applicationException ) {
		this.applicationException = applicationException;
		return this;
	}

	public ResponseEntity<Request> getCloseRequestResponseEntity() {
		return this.closeRequestResponseEntity;
	}

	public Printer3DWorld setCloseRequestResponseEntity( final ResponseEntity<Request> closeRequestResponseEntity ) {
		this.closeRequestResponseEntity = closeRequestResponseEntity;
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

	public JobRequest getJobRequest() {
		return this.jobRequest;
	}

	public Printer3DWorld setJobRequest( final JobRequest jobRequest ) {
		this.jobRequest = jobRequest;
		return this;
	}

	public ResponseEntity<List<RequestV2>> getListRequestV2ResponseEntity() {
		return this.listRequestV2ResponseEntity;
	}

	public Printer3DWorld setListRequestV2ResponseEntity( final ResponseEntity<List<RequestV2>> listRequestV2ResponseEntity ) {
		this.listRequestV2ResponseEntity = listRequestV2ResponseEntity;
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

	public ResponseEntity<ModelList> getModelListResponseEntity() {
		return this.modelListResponseEntity;
	}

	public Printer3DWorld setModelListResponseEntity( final ResponseEntity<ModelList> modelListResponseEntity ) {
		this.modelListResponseEntity = modelListResponseEntity;
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

	public List<RequestItem> getRequestContents() {
		return this.requestContents;
	}

	public Printer3DWorld setRequestContents( final List<RequestItem> requestContents ) {
		this.requestContents = requestContents;
		return this;
	}

	public UUID getRequestId() {
		return this.requestId;
	}

	public Printer3DWorld setRequestId( final UUID requestId ) {
		this.requestId = requestId;
		return this;
	}

	public ResponseEntity<RequestList> getRequestListResponseEntity() {
		return this.requestListResponseEntity;
	}

	public Printer3DWorld setRequestListResponseEntity( final ResponseEntity<RequestList> requestListResponseEntity ) {
		this.requestListResponseEntity = requestListResponseEntity;
		return this;
	}

	public ResponseEntity<Request> getRequestResponseEntity() {
		return this.requestResponseEntity;
	}

	public Printer3DWorld setRequestResponseEntity( final ResponseEntity<Request> requestResponseEntity ) {
		this.requestResponseEntity = requestResponseEntity;
		return this;
	}

	public RequestV2 getRequestV2() {
		return this.requestV2;
	}

	public Printer3DWorld setRequestV2( final RequestV2 requestV2 ) {
		this.requestV2 = requestV2;
		return this;
	}

	public String getSelectionLabel() {
		return this.selectionLabel;
	}

	public Printer3DWorld setSelectionLabel( final String selectionLabel ) {
		this.selectionLabel = selectionLabel;
		return this;
	}

	public ResponseEntity<Machine> getStartBuildResponseEntity() {
		return this.startBuildResponseEntity;
	}

	public Printer3DWorld setStartBuildResponseEntity( final ResponseEntity<Machine> startBuildResponseEntity ) {
		this.startBuildResponseEntity = startBuildResponseEntity;
		return this;
	}

	public UpdateGroupPartRequest getUpdateGroupPartRequest() {
		return this.updateGroupPartRequest;
	}

	public Printer3DWorld setUpdateGroupPartRequest( final UpdateGroupPartRequest updateGroupPartRequest ) {
		this.updateGroupPartRequest = updateGroupPartRequest;
		return this;
	}

	public ResponseEntity<CountResponse> getUpdateGroupPartResponseEntity() {
		return this.updateGroupPartResponseEntity;
	}

	public Printer3DWorld setUpdateGroupPartResponseEntity( final ResponseEntity<CountResponse> updateGroupPartResponseEntity ) {
		this.updateGroupPartResponseEntity = updateGroupPartResponseEntity;
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
