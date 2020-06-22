package org.dimensinfin.printer3d.backend.support.inventory.machine;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

public class CucumberTableToJobRequestConverter extends CucumberTableConverter<JobRequest> {
	private static final String JOB_REQUEST_JOB_ID = "jobId";
	private static final String JOB_REQUEST_PART_ID = "partId";
	private static final String JOB_REQUEST_COPIES = "copies";

	@Override
	public JobRequest convert( final Map<String, String> cucumberRow ) {
		JobRequest.Builder builder = new JobRequest.Builder();
		if (null != cucumberRow.get( JOB_REQUEST_JOB_ID ))
			builder = builder.withId( UUID.fromString( cucumberRow.get( JOB_REQUEST_JOB_ID ) ) );
		if (null != cucumberRow.get( JOB_REQUEST_PART_ID ))
			builder = builder.withPartId( UUID.fromString( cucumberRow.get( JOB_REQUEST_PART_ID ) ) );
		if (null != cucumberRow.get( JOB_REQUEST_COPIES ))
			builder = builder.withPartCopies( Integer.parseInt( cucumberRow.get( JOB_REQUEST_COPIES ) ) );
		return builder.build();
	}
}
