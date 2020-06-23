package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.BuildRecordConstants.TEST_BUILDRECORD_PARTCOPIES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CHARACTERISTICS;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_MODEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR_CODE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;

public class MachineV2Test {
	@Test
	public void buildContract() {
		final BuildRecord buildRecord = Mockito.mock( BuildRecord.class );
		final MachineV2 machineV2 = new MachineV2.Builder()
				.withId( TEST_MACHINE_ID )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withBuildRecord( buildRecord )
				.build();
		Assertions.assertNotNull( machineV2 );
	}

	@Test
	public void buildFailure() {
		final BuildRecord buildRecord = Mockito.mock( BuildRecord.class );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new MachineV2.Builder()
					.withId( null )
					.withLabel( TEST_MACHINE_LABEL )
					.withModel( TEST_MACHINE_MODEL )
					.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
					.withBuildRecord( buildRecord )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new MachineV2.Builder()
					.withId( TEST_MACHINE_ID )
					.withLabel( null )
					.withModel( TEST_MACHINE_MODEL )
					.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
					.withBuildRecord( buildRecord )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new MachineV2.Builder()
					.withId( TEST_MACHINE_ID )
					.withLabel( TEST_MACHINE_LABEL )
					.withModel( null )
					.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
					.withBuildRecord( buildRecord )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new MachineV2.Builder()
					.withId( TEST_MACHINE_ID )
					.withLabel( TEST_MACHINE_LABEL )
					.withModel( TEST_MACHINE_MODEL )
					.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
					.withBuildRecord( null )
					.build();
		} );
	}

	@Test
	public void buildUndefined() {
		final BuildRecord buildRecord = Mockito.mock( BuildRecord.class );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new MachineV2.Builder()
					.withLabel( TEST_MACHINE_LABEL )
					.withModel( TEST_MACHINE_MODEL )
					.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
					.withBuildRecord( buildRecord )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new MachineV2.Builder()
					.withId( TEST_MACHINE_ID )
					.withModel( TEST_MACHINE_MODEL )
					.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
					.withBuildRecord( buildRecord )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new MachineV2.Builder()
					.withId( TEST_MACHINE_ID )
					.withLabel( TEST_MACHINE_LABEL )
					.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
					.withBuildRecord( buildRecord )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new MachineV2.Builder()
					.withId( TEST_MACHINE_ID )
					.withLabel( TEST_MACHINE_LABEL )
					.withModel( TEST_MACHINE_MODEL )
					.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
					.build();
		} );
	}

	@Test
	public void getRemainingTime() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( part )
				.withJobInstallmentDate( OffsetDateTime.now().minus( Duration.ofMinutes( 6 ) ) )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.build();
		final MachineV2 machineV2 = new MachineV2.Builder()
				.withId( UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" ) )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withBuildRecord( buildRecord )
				.build();
		// When
		Mockito.when( part.getBuildTime() ).thenReturn( 10 );
		// Test
		final int obtained = machineV2.getRemainingTime();
		// Assertions
		final int newBuildTime = (10 * 60 * TEST_BUILDRECORD_PARTCOPIES) - 6 * 60;
		Assertions.assertEquals( newBuildTime, obtained );
	}

	@Test
	public void getterContract() {
		// Given
		final BuildRecord buildRecord = Mockito.mock( BuildRecord.class );
		final MachineV2 machineV2 = new MachineV2.Builder()
				.withId( TEST_MACHINE_ID )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withBuildRecord( buildRecord )
				.build();
		// Assertions
		Assertions.assertEquals( TEST_MACHINE_ID.toString(), machineV2.getId().toString() );
		Assertions.assertEquals( TEST_MACHINE_LABEL, machineV2.getLabel() );
		Assertions.assertEquals( TEST_MACHINE_MODEL, machineV2.getModel() );
		Assertions.assertEquals( TEST_MACHINE_CHARACTERISTICS, machineV2.getCharacteristics() );
		Assertions.assertNotNull( machineV2.getBuildRecord() );
	}

	@Test
	public void isRunningIdle() {
		// Given
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( null )
				.withJobInstallmentDate( OffsetDateTime.now().minus( Duration.ofMinutes( 6 ) ) )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.build();
		final MachineV2 machineV2 = new MachineV2.Builder()
				.withId( UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" ) )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withBuildRecord( buildRecord )
				.build();
		// Assertions
		Assertions.assertFalse( machineV2.isRunning() );
	}

	@Test
	public void isRunningRunning() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( part )
				.withJobInstallmentDate( OffsetDateTime.now().minus( Duration.ofMinutes( 6 ) ) )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.build();
		final MachineV2 machineV2 = new MachineV2.Builder()
				.withId( UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" ) )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withBuildRecord( buildRecord )
				.build();
		// Assertions
		Assertions.assertTrue( machineV2.isRunning() );
	}

	@Test
	public void toStringContract() {
		// Given
		final Part part = new Part.Builder()
				.withId( UUID.fromString( "a4ba0dd6-acde-483c-ad68-5efb9ac9886e" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR_CODE )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( part )
				.withJobInstallmentDate( OffsetDateTime.parse( "2020-06-06T21:54:00.226181+02:00" ) )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.build();
		final MachineV2 machineV2 = new MachineV2.Builder()
				.withId( UUID.fromString( "27c021cc-1b58-49db-870f-98d291041952" ) )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withBuildRecord( buildRecord )
				.build();
		// Test
		final String expected = "{\"id\":\"27c021cc-1b58-49db-870f-98d291041952\",\"label\":\"-TEST_MACHINE_LABEL-\",\"model\":\"-TEST_MACHINE_MODEL-\",\"characteristics\":\"-TEST_MACHINE_CHARACTERISTICS-\",\"buildRecord\":\"{\\\"state\\\":\\\"RUNNING\\\",\\\"part\\\":{\\\"id\\\":\\\"a4ba0dd6-acde-483c-ad68-5efb9ac9886e\\\",\\\"label\\\":\\\"-TEST_PART_LABEL-\\\",\\\"description\\\":\\\"-TEST_PART_DESCRIPTION-\\\",\\\"material\\\":\\\"PLA\\\",\\\"color\\\":\\\"VERDE-T\\\",\\\"buildTime\\\":60,\\\"cost\\\":0.76,\\\"price\\\":2.0,\\\"stockLevel\\\":4,\\\"stockAvailable\\\":4,\\\"imagePath\\\":\\\"https:\\\\\\/\\\\\\/ibb.co\\\\\\/3dGbsRh\\\",\\\"modelPath\\\":\\\"pieza3.STL\\\",\\\"active\\\":false},\\\"partCopies\\\":8,\\\"jobInstallmentDate\\\":\\\"2020-06-06T21:54:00.226181+02:00\\\"}\"}";
		final String obtained = machineV2.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
