package org.dimensinfin.printer3d.backend.support.inventory.machine;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.domain.Machine;

import static org.dimensinfin.printer3d.backend.support.inventory.machine.MachineMapConstants.MACHINE_CURRENT_JOB_PART;
import static org.dimensinfin.printer3d.backend.support.inventory.machine.MachineMapConstants.MACHINE_CURRENT_PART_INSTANCES;
import static org.dimensinfin.printer3d.backend.support.inventory.machine.MachineMapConstants.MACHINE_JOB_INSTALLMENT_DATE;

public class MachineValidator implements Validator<Machine> {

	@Override
	public boolean validate( final Map<String, String> rowData, final Machine record ) {
		if (null != rowData.get( MACHINE_CURRENT_JOB_PART ))
			Assertions.assertEquals( rowData.get( MACHINE_CURRENT_JOB_PART ), record.getCurrentJobPartId().toString() );
		if (null != rowData.get( MACHINE_JOB_INSTALLMENT_DATE ))
			Assertions.assertEquals( rowData.get( MACHINE_JOB_INSTALLMENT_DATE ), record.getJobInstallmentDate().toString() );
		if (null != rowData.get( MACHINE_CURRENT_PART_INSTANCES ))
			Assertions.assertEquals( Integer.parseInt( rowData.get( MACHINE_CURRENT_PART_INSTANCES ) ), record.getCurrentPartInstances() );
		return true;
	}
}
