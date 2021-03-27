package org.dimensinfin.printer3d.backend.support.production.job;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.JOB_PART_ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.JOB_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PRIORITY;

public class JobValidator implements Validator<Job> {

	@Override
	public boolean validate( final Map<String, String> rowData, final Job record ) {
		if (null != rowData.get( JOB_PART_ID ))
			Assertions.assertEquals( UUID.fromString( rowData.get( JOB_PART_ID ) ).toString(), record.getPart().getId().toString() );
		if (null != rowData.get( JOB_PART_LABEL ))
			Assertions.assertEquals( rowData.get( JOB_PART_LABEL ), record.getPart().getLabel() );
		if (null != rowData.get( PRIORITY )) Assertions.assertEquals( Integer.parseInt( rowData.get( PRIORITY ) ), record.getPriority() );
		return true;
	}
}
