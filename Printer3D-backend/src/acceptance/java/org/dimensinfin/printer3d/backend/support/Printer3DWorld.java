package org.dimensinfin.printer3d.backend.support;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.acceptance.support.CommonWorld;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

public class Printer3DWorld extends CommonWorld {
	private DimensinfinRuntimeException applicationException;
	private Part part;
	private ResponseEntity<Part> partResponseEntity;
	private Coil coil;
	private ResponseEntity<Coil> coilResponseEntity;
	private ResponseEntity<FinishingsResponse> finishingsResponseEntity;
	private ResponseEntity<List<MachineV2>> listMachineV2ResponseEntity;
	private UUID partId;
	private UUID machineId;
	private ResponseEntity<MachineV2> machineResponseEntity;
	private ResponseEntity<List<Job>> listJobResponseEntity;
	private ModelRequest modelRequest;
	private ResponseEntity<Model> modelResponseEntity;
	private ResponseEntity<List<Model>> modelListResponseEntity;
	private UUID modelId;
	private UUID requestId;
	private JobRequest jobRequest;
	private String selectionLabel;
	private UpdateGroupPartRequest updateGroupPartRequest;
	private ResponseEntity<CounterResponse> counterResponseResponseEntity;
	private UpdateCoilRequest updateCoilRequest;
	private List<RequestItem> requestContents;
	private ResponseEntity<List<WeekAmount>> listWeekAmountResponseEntity;
	private Integer weekCount;
	private ResponseEntity<List<Coil>> coilV2ListResponseEntity;
	private ResponseEntity<List<Part>> partListV2ResponseEntity;
	private ResponseEntity<String> closedRequestsDataResponseEntity;
	private CustomerRequestRequestV2 customerRequestRequestV2;
	private ResponseEntity<CustomerRequestResponseV2> customerRequestResponseV2;
	private ResponseEntity<List<CustomerRequestResponseV2>> listRequestV2ResponseEntity;
	private ResponseEntity<CustomerRequestResponseV2> deliverRequestsDataResponseEntity;

	// - G E T T E R S   &   S E T T E R S
	public DimensinfinRuntimeException getApplicationException() {
		return this.applicationException;
	}

	public Printer3DWorld setApplicationException( final DimensinfinRuntimeException applicationException ) {
		this.applicationException = applicationException;
		return this;
	}

	public ResponseEntity<String> getClosedRequestsDataResponseEntity() {
		return this.closedRequestsDataResponseEntity;
	}

	public Printer3DWorld setClosedRequestsDataResponseEntity( final ResponseEntity<String> closedRequestsDataResponseEntity ) {
		this.closedRequestsDataResponseEntity = closedRequestsDataResponseEntity;
		return this;
	}

	public Coil getCoil() {
		return this.coil;
	}

	public Printer3DWorld setCoil( final Coil coil ) {
		this.coil = coil;
		return this;
	}

	public ResponseEntity<Coil> getCoilResponseEntity() {
		return this.coilResponseEntity;
	}

	public Printer3DWorld setCoilResponseEntity( final ResponseEntity<Coil> coilResponseEntity ) {
		this.coilResponseEntity = coilResponseEntity;
		return this;
	}

	public ResponseEntity<List<Coil>> getCoilV2ListResponseEntity() {
		return this.coilV2ListResponseEntity;
	}

	public Printer3DWorld setCoilV2ListResponseEntity( final ResponseEntity<List<Coil>> coilV2ListResponseEntity ) {
		this.coilV2ListResponseEntity = coilV2ListResponseEntity;
		return this;
	}

	public ResponseEntity<CounterResponse> getCounterResponseResponseEntity() {
		return this.counterResponseResponseEntity;
	}

	public Printer3DWorld setCounterResponseResponseEntity( final ResponseEntity<CounterResponse> counterResponseResponseEntity ) {
		this.counterResponseResponseEntity = counterResponseResponseEntity;
		return this;
	}

	public CustomerRequestRequestV2 getCustomerRequestRequestV2() {
		return this.customerRequestRequestV2;
	}

	public Printer3DWorld setCustomerRequestRequestV2( final CustomerRequestRequestV2 customerRequestRequestV2 ) {
		this.customerRequestRequestV2 = customerRequestRequestV2;
		return this;
	}

	public ResponseEntity<CustomerRequestResponseV2> getCustomerRequestResponseV2() {
		return this.customerRequestResponseV2;
	}

	public Printer3DWorld setCustomerRequestResponseV2( final ResponseEntity<CustomerRequestResponseV2> customerRequestResponseV2 ) {
		this.customerRequestResponseV2 = customerRequestResponseV2;
		return this;
	}

	public ResponseEntity<CustomerRequestResponseV2> getDeliverRequestsDataResponseEntity() {
		return this.deliverRequestsDataResponseEntity;
	}

	public Printer3DWorld setDeliverRequestsDataResponseEntity( final ResponseEntity<CustomerRequestResponseV2> deliverRequestsDataResponseEntity ) {
		this.deliverRequestsDataResponseEntity = deliverRequestsDataResponseEntity;
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

	public ResponseEntity<List<MachineV2>> getListMachineV2ResponseEntity() {
		return this.listMachineV2ResponseEntity;
	}

	public Printer3DWorld setListMachineV2ResponseEntity( final ResponseEntity<List<MachineV2>> listMachineV2ResponseEntity ) {
		this.listMachineV2ResponseEntity = listMachineV2ResponseEntity;
		return this;
	}

	public ResponseEntity<List<CustomerRequestResponseV2>> getListRequestV2ResponseEntity() {
		return this.listRequestV2ResponseEntity;
	}

	public Printer3DWorld setListRequestV2ResponseEntity( final ResponseEntity<List<CustomerRequestResponseV2>> listRequestV2ResponseEntity ) {
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

	public ResponseEntity<MachineV2> getMachineResponseEntity() {
		return this.machineResponseEntity;
	}

	public Printer3DWorld setMachineResponseEntity( final ResponseEntity<MachineV2> machineResponseEntity ) {
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

	public ResponseEntity<List<Model>> getModelListResponseEntity() {
		return this.modelListResponseEntity;
	}

	public Printer3DWorld setModelListResponseEntity( final ResponseEntity<List<Model>> modelListResponseEntity ) {
		this.modelListResponseEntity = modelListResponseEntity;
		return this;
	}

	public ModelRequest getModelRequest() {
		return this.modelRequest;
	}

	public Printer3DWorld setModelRequest( final ModelRequest modelRequest ) {
		this.modelRequest = modelRequest;
		return this;
	}

	public ResponseEntity<Model> getModelResponseEntity() {
		return this.modelResponseEntity;
	}

	public Printer3DWorld setModelResponseEntity( final ResponseEntity<Model> modelResponseEntity ) {
		this.modelResponseEntity = modelResponseEntity;
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

	public ResponseEntity<List<Part>> getPartListV2ResponseEntity() {
		return this.partListV2ResponseEntity;
	}

	public Printer3DWorld setPartListV2ResponseEntity( final ResponseEntity<List<Part>> partListV2ResponseEntity ) {
		this.partListV2ResponseEntity = partListV2ResponseEntity;
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
