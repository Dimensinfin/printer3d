package org.dimensinfin.printer3d.backend.support;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.acceptance.support.CommonWorld;
import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;
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
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

public class Printer3DWorld extends CommonWorld {
	private DimensinfinRuntimeException applicationException;
	private Part part;
	private ResponseEntity<Part> partResponseEntity;
	private ResponseEntity<PartList> partListResponseEntity;
	private Coil coil;
	private ResponseEntity<Coil> coilResponseEntity;
	private ResponseEntity<CoilList> coilListResponseEntity;
	private ResponseEntity<FinishingsResponse> finishingsResponseEntity;
	private ResponseEntity<MachineList> machineListResponseEntity;
	private ResponseEntity<MachineListV2> machineListv2ResponseEntity;
	private UUID partId;
	private UUID machineId;
	private ResponseEntity<Machine> machineResponseEntity;
	private ResponseEntity<List<Job>> listJobResponseEntity;
	private NewModelRequest newModelRequest;
	private ResponseEntity<Model> modelResponseEntity;
	private ResponseEntity<ModelList> modelListResponseEntity;
	private UUID modelId;
	private UUID requestId;
	private ResponseEntity<RequestV2> requestV2ResponseEntity;
	private JobRequest jobRequest;
	private String selectionLabel;
	private UpdateGroupPartRequest updateGroupPartRequest;
	private ResponseEntity<CounterResponse> counterResponseResponseEntity;
	private UpdateCoilRequest updateCoilRequest;
	private List<RequestItem> requestContents;
	private RequestV2 requestV2;
	private ResponseEntity<List<RequestV2>> listRequestV2ResponseEntity;
	private ResponseEntity<List<WeekAmount>> listWeekAmountResponseEntity;
	private Integer weekCount;

	// - G E T T E R S   &   S E T T E R S
	public DimensinfinRuntimeException getApplicationException() {
		return this.applicationException;
	}

	public Printer3DWorld setApplicationException( final DimensinfinRuntimeException applicationException ) {
		this.applicationException = applicationException;
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

	public ResponseEntity<Coil> getCoilResponseEntity() {
		return this.coilResponseEntity;
	}

	public Printer3DWorld setCoilResponseEntity( final ResponseEntity<Coil> coilResponseEntity ) {
		this.coilResponseEntity = coilResponseEntity;
		return this;
	}

	public ResponseEntity<CounterResponse> getCounterResponseResponseEntity() {
		return this.counterResponseResponseEntity;
	}

	public Printer3DWorld setCounterResponseResponseEntity( final ResponseEntity<CounterResponse> counterResponseResponseEntity ) {
		this.counterResponseResponseEntity = counterResponseResponseEntity;
		return this;
	}

	public ResponseEntity<FinishingsResponse> getFinishingsResponseEntity() {
		return this.finishingsResponseEntity;
	}

	public Printer3DWorld setFinishingsResponseEntity( final ResponseEntity<FinishingsResponse> finishingsResponseEntity ) {
		this.finishingsResponseEntity = finishingsResponseEntity;
		return this;
	}

	public JobRequest getJobRequest() {
		return this.jobRequest;
	}

	public Printer3DWorld setJobRequest( final JobRequest jobRequest ) {
		this.jobRequest = jobRequest;
		return this;
	}

	public ResponseEntity<List<Job>> getListJobResponseEntity() {
		return this.listJobResponseEntity;
	}

	public Printer3DWorld setListJobResponseEntity( final ResponseEntity<List<Job>> listJobResponseEntity ) {
		this.listJobResponseEntity = listJobResponseEntity;
		return this;
	}

	public ResponseEntity<List<RequestV2>> getListRequestV2ResponseEntity() {
		return this.listRequestV2ResponseEntity;
	}

	public Printer3DWorld setListRequestV2ResponseEntity( final ResponseEntity<List<RequestV2>> listRequestV2ResponseEntity ) {
		this.listRequestV2ResponseEntity = listRequestV2ResponseEntity;
		return this;
	}

	public ResponseEntity<List<WeekAmount>> getListWeekAmountResponseEntity() {
		return this.listWeekAmountResponseEntity;
	}

	public Printer3DWorld setListWeekAmountResponseEntity( final ResponseEntity<List<WeekAmount>> listWeekAmountResponseEntity ) {
		this.listWeekAmountResponseEntity = listWeekAmountResponseEntity;
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

	public ResponseEntity<Machine> getMachineResponseEntity() {
		return this.machineResponseEntity;
	}

	public Printer3DWorld setMachineResponseEntity( final ResponseEntity<Machine> machineResponseEntity ) {
		this.machineResponseEntity = machineResponseEntity;
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

	public NewModelRequest getNewModelRequest() {
		return this.newModelRequest;
	}

	public Printer3DWorld setNewModelRequest( final NewModelRequest newModelRequest ) {
		this.newModelRequest = newModelRequest;
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

	public ResponseEntity<Part> getPartResponseEntity() {
		return this.partResponseEntity;
	}

	public Printer3DWorld setPartResponseEntity( final ResponseEntity<Part> partResponseEntity ) {
		this.partResponseEntity = partResponseEntity;
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

	public RequestV2 getRequestV2() {
		return this.requestV2;
	}

	public Printer3DWorld setRequestV2( final RequestV2 requestV2 ) {
		this.requestV2 = requestV2;
		return this;
	}

	public ResponseEntity<RequestV2> getRequestV2ResponseEntity() {
		return this.requestV2ResponseEntity;
	}

	public Printer3DWorld setRequestV2ResponseEntity( final ResponseEntity<RequestV2> requestV2ResponseEntity ) {
		this.requestV2ResponseEntity = requestV2ResponseEntity;
		return this;
	}

	public String getSelectionLabel() {
		return this.selectionLabel;
	}

	public Printer3DWorld setSelectionLabel( final String selectionLabel ) {
		this.selectionLabel = selectionLabel;
		return this;
	}

	public UpdateCoilRequest getUpdateCoilRequest() {
		return this.updateCoilRequest;
	}

	public Printer3DWorld setUpdateCoilRequest( final UpdateCoilRequest updateCoilRequest ) {
		this.updateCoilRequest = updateCoilRequest;
		return this;
	}

	public UpdateGroupPartRequest getUpdateGroupPartRequest() {
		return this.updateGroupPartRequest;
	}

	public Printer3DWorld setUpdateGroupPartRequest( final UpdateGroupPartRequest updateGroupPartRequest ) {
		this.updateGroupPartRequest = updateGroupPartRequest;
		return this;
	}

	public Integer getWeekCount() {
		return this.weekCount;
	}

	public Printer3DWorld setWeekCount( final Integer weekCount ) {
		this.weekCount = weekCount;
		return this;
	}
}
