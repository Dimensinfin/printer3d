package org.dimensinfin.printer3d.backend.support;

public enum RequestType {
	NEW_PART,
	UPDATE_PART,
	UPDATE_GROUP_PART,
	GET_PARTS_V2,
	NEW_COIL,
	GET_COILS_V2,
	UPDATE_COIL,
	GET_FINISHINGS,
	GET_MACHINES_V2,
	START_BUILDV2,
	CANCEL_BUILD,
	COMPLETE_BUILD,
	GET_JOBS,
	NEW_MODEL,
	UPDATE_MODEL,
	GET_MODELS,
	NEW_REQUESTV2,
	CLOSE_REQUEST,
	DELETE_REQUEST,
	SCHEDULER_COIL_RESET,
	ACCOUNTING_REQUEST_BY_WEEK,
	ACCOUNTING_CLOSED_REQUESTS_CSV_DATA,
	DELIVER_REQUEST,
	GET_OPEN_REQUESTSV3,
	GET_CLOSED_REQUESTSV3
}
