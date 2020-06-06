package org.dimensinfin.printer3d.backend.support.inventory.machine;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;

import static org.dimensinfin.printer3d.backend.support.inventory.machine.BuildRecordMapConstants.BUILDRECORD_JOB_INSTALLMENT_STATE;
import static org.dimensinfin.printer3d.backend.support.inventory.machine.BuildRecordMapConstants.BUILDRECORD_PART_COPIES;
import static org.dimensinfin.printer3d.backend.support.inventory.machine.BuildRecordMapConstants.BUILDRECORD_PART_ID;
import static org.dimensinfin.printer3d.backend.support.inventory.machine.BuildRecordMapConstants.BUILDRECORD_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.inventory.machine.BuildRecordMapConstants.BUILDRECORD_REMAINING_TIME;
import static org.dimensinfin.printer3d.backend.support.inventory.machine.BuildRecordMapConstants.BUILDRECORD_STATE;

public class BuildRecordValidator implements Validator<BuildRecord> {

	@Override
	public boolean validate( final Map<String, String> rowData, final BuildRecord record ) {
		if (null != rowData.get( BUILDRECORD_STATE ))
			Assertions.assertEquals( rowData.get( BUILDRECORD_STATE ), record.getState().name() );
		if (null != rowData.get( BUILDRECORD_PART_ID ))
			Assertions.assertEquals( rowData.get( BUILDRECORD_PART_ID ), record.getPart().getId().toString() );
		if (null != rowData.get( BUILDRECORD_PART_LABEL ))
			Assertions.assertEquals( rowData.get( BUILDRECORD_PART_LABEL ), record.getPart().getLabel() );
		if (null != rowData.get( BUILDRECORD_PART_LABEL ))
			Assertions.assertEquals( Integer.parseInt( rowData.get( BUILDRECORD_PART_COPIES ) ), record.getPartCopies() );
		if (null != rowData.get( BUILDRECORD_JOB_INSTALLMENT_STATE ))
			Assertions.assertEquals( rowData.get( BUILDRECORD_JOB_INSTALLMENT_STATE ),
					(null == record.getJobInstallmentDate()) ? record.getJobInstallmentDate() : record.getJobInstallmentDate().toString() );
		if (null != rowData.get( BUILDRECORD_REMAINING_TIME ))
			Assertions.assertEquals( Integer.parseInt( rowData.get( BUILDRECORD_REMAINING_TIME ) ), record.getRemainingTime() );
		return true;
	}
}
