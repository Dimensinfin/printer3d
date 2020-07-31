package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CURRENTJOBPARTID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CURRENTPARTBUILDTIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CURRENTPARTINSTANCES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_JOBINSTALLMENTDATE;

public class MachineEntityTest {
	@Test
	public void clearJob() {
		// Test
		final MachineEntity machineEntity = new MachineEntity();
		machineEntity.clearJob();
		// Assertions
		Assertions.assertNotNull( machineEntity );
		Assertions.assertNull( machineEntity.getLabel() );
		Assertions.assertNull( machineEntity.getCurrentJobPartId() );
		Assertions.assertNull( machineEntity.getJobInstallmentDate() );
		Assertions.assertEquals( 1, machineEntity.getCurrentPartInstances() );
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( MachineEntity.class )
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final MachineEntity machineEntity = new MachineEntity();
		machineEntity.setCurrentJobPartId( TEST_MACHINE_CURRENTJOBPARTID );
		// Assertions
		Assertions.assertNull( machineEntity.getCharacteristics() );
		Assertions.assertEquals( 0, machineEntity.getCurrentJobPartBuildTime() );
		machineEntity.setCurrentJobPartBuildTime( TEST_MACHINE_CURRENTPARTBUILDTIME );
		Assertions.assertEquals( TEST_MACHINE_CURRENTPARTBUILDTIME, machineEntity.getCurrentJobPartBuildTime() );
		Assertions.assertEquals( TEST_MACHINE_CURRENTJOBPARTID.toString(), machineEntity.getCurrentJobPartId().toString() );
		Assertions.assertEquals( 1, machineEntity.getCurrentPartInstances() );
		machineEntity.setCurrentPartInstances( TEST_MACHINE_CURRENTPARTINSTANCES );
		Assertions.assertEquals( TEST_MACHINE_CURRENTPARTINSTANCES, machineEntity.getCurrentPartInstances() );
		Assertions.assertNull( machineEntity.getId() );
		Assertions.assertNull( machineEntity.getJobInstallmentDate() );
		machineEntity.setJobInstallmentDate(TEST_MACHINE_JOBINSTALLMENTDATE);
		Assertions.assertEquals( TEST_MACHINE_JOBINSTALLMENTDATE.toString(), machineEntity.getJobInstallmentDate().toString() );
		Assertions.assertNull( machineEntity.getLabel() );
		Assertions.assertNull( machineEntity.getModel() );
		Assertions.assertNotNull( machineEntity );
	}

	@Test
	public void toStringContract() {
		// Given
		final MachineEntity machineEntity = new MachineEntity();
		// Test
		final String expected = "{\"id\":null,\"label\":null,\"model\":null,\"characteristics\":null,\"currentJobPartId\":null,\"currentPartInstances\":1,\"currentJobPartBuildTime\":0,\"jobInstallmentDate\":null}";
		final String obtained = machineEntity.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
