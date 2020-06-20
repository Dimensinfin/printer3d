package org.dimensinfin.printer3d.backend.support.production.job;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.production.rest.dto.JobHistoric;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.COST;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.JOB_INSTALLMENT_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PART_COPIES;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PRICE;

public class JobHistoricValidator implements Validator<JobHistoric>  {

	@Override
	public boolean validate( final Map<String, String> rowData, final JobHistoric record ) {
		if (null != rowData.get( BUILD_TIME )) Assertions.assertEquals( Integer.parseInt( rowData.get( BUILD_TIME )), record.getBuildTime() );
		if (null != rowData.get( COST )) Assertions.assertEquals( Float.parseFloat( rowData.get( COST )), record.getCost());
		if (null != rowData.get( PRICE )) Assertions.assertEquals( Float.parseFloat( rowData.get( PRICE )), record.getPrice());
		if (null != rowData.get( PART_COPIES )) Assertions.assertEquals( Integer.parseInt( rowData.get( PART_COPIES )), record.getPartCopies() );
		if (null != rowData.get( JOB_INSTALLMENT_DATE ))
			Assertions.assertEquals(  rowData.get( JOB_INSTALLMENT_DATE ), record.getJobInstallmentDate().format( DateTimeFormatter.ISO_OFFSET_DATE_TIME ) );
		return true;
	}
}
