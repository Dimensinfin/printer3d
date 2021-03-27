package org.dimensinfin.printer3d.backend.inventory.machine.rest.converter;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CHARACTERISTICS;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CURRENTJOBPARTID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CURRENTPARTINSTANCES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_JOBINSTALLMENTDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_MODEL;

public class MachineEntityToMachineV2ConverterTest {
	@Test
	public void constructorContract() {
		final BuildRecord buildRecord = Mockito.mock( BuildRecord.class );
		final MachineEntityToMachineV2Converter machineEntityToMachineConverter = new MachineEntityToMachineV2Converter( buildRecord );
		Assertions.assertNotNull( machineEntityToMachineConverter );
	}

	@Test
	public void convert() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final BuildRecord buildRecord = Mockito.mock( BuildRecord.class );
		// When
		Mockito.when( part.getId() ).thenReturn( UUID.fromString( "4ba6c6e4-bef3-41a5-bc6d-4c239d301852" ) );
		Mockito.when( machineEntity.getId() ).thenReturn( TEST_MACHINE_ID );
		Mockito.when( machineEntity.getLabel() ).thenReturn( TEST_MACHINE_LABEL );
		Mockito.when( machineEntity.getModel() ).thenReturn( TEST_MACHINE_MODEL );
		Mockito.when( machineEntity.getCharacteristics() ).thenReturn( TEST_MACHINE_CHARACTERISTICS );
		Mockito.when( machineEntity.getCurrentJobPartId() ).thenReturn( TEST_MACHINE_CURRENTJOBPARTID );
		Mockito.when( machineEntity.getCurrentPartInstances() ).thenReturn( TEST_MACHINE_CURRENTPARTINSTANCES );
		Mockito.when( machineEntity.getJobInstallmentDate() ).thenReturn( TEST_MACHINE_JOBINSTALLMENTDATE );
		// Test
		final MachineEntityToMachineV2Converter machineEntityToMachineConverter = new MachineEntityToMachineV2Converter( buildRecord );
		final MachineV2 obtained = machineEntityToMachineConverter.convert( machineEntity );
		// Assertions
		Assertions.assertEquals( TEST_MACHINE_ID, obtained.getId() );
		Assertions.assertEquals( TEST_MACHINE_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_MACHINE_MODEL, obtained.getModel() );
		Assertions.assertEquals( TEST_MACHINE_CHARACTERISTICS, obtained.getCharacteristics() );
		Assertions.assertNotNull( obtained.getBuildRecord() );
	}

	@Test
	public void convertException() {
		// Given
		final MachineEntity machineEntity = Mockito.mock( MachineEntity.class );
		final BuildRecord buildRecord = Mockito.mock( BuildRecord.class );
		final MachineEntityToMachineV2Converter machineEntityToMachineConverter = new MachineEntityToMachineV2Converter( buildRecord );
		// Test
		final DimensinfinRuntimeException exception = Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			machineEntityToMachineConverter.convert( machineEntity );
		} );
		// Assertions
		Assertions.assertEquals( "RUNTIME_INTERNAL_ERROR", exception.getErrorName() );
		Assertions.assertEquals( "dimensinfin.uncatalogued.runtime", exception.getErrorCode() );
		Assertions.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, exception.getHttpStatus() );
	}
}
