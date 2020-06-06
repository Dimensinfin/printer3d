package org.dimensinfin.printer3d.client.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CHARACTERISTICS;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CURRENTPARTINSTANCES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_JOBINSTALLMENTDATE;
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

public class MachineTest {
	@Test
	public void buildContract() {
		final Part part = Mockito.mock( Part.class );
		final Machine machine = new Machine.Builder()
				.withId( TEST_MACHINE_ID )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withPart( part )
				.withInstallmentDate( OffsetDateTime.now() )
				.withInstances( TEST_MACHINE_CURRENTPARTINSTANCES )
				.build();
		Assertions.assertNotNull( machine );
	}

		@Test
	public void equalsContract() {
		EqualsVerifier.forClass( Machine.class )
				.suppress( Warning.STRICT_INHERITANCE)
				.verify();
	}

	@Test
	public void getterContract() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final Machine machine = new Machine.Builder()
				.withId( TEST_MACHINE_ID )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withPart( part )
				.withInstallmentDate( TEST_MACHINE_JOBINSTALLMENTDATE )
				.withInstances( TEST_MACHINE_CURRENTPARTINSTANCES )
				.build();
		// When
		Mockito.when( part.getId() ).thenReturn( UUID.randomUUID() );
		// Assertions
		Assertions.assertEquals( TEST_MACHINE_ID, machine.getId() );
		Assertions.assertEquals( TEST_MACHINE_LABEL, machine.getLabel() );
		Assertions.assertEquals( TEST_MACHINE_MODEL, machine.getModel() );
		Assertions.assertEquals( TEST_MACHINE_CHARACTERISTICS, machine.getCharacteristics() );
		Assertions.assertEquals( part.getId().toString(), machine.getCurrentJobPart().getId().toString() );
		Assertions.assertEquals( TEST_MACHINE_JOBINSTALLMENTDATE.toString(), machine.getJobInstallmentDate().toString() );
		Assertions.assertEquals( TEST_MACHINE_CURRENTPARTINSTANCES, machine.getCurrentPartInstances() );
	}

	@Test
	public void toStringContractNoJob() {
		// Given
		final Machine machine = new Machine.Builder()
				.withId( TEST_MACHINE_ID )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withPart( null )
				.withInstallmentDate( null )
				.withInstances( TEST_MACHINE_CURRENTPARTINSTANCES )
				.build();
		// Test
		final String expected = "{\"id\":\"27c021cc-1b58-49db-870f-98d291041952\",\"label\":\"-TEST_MACHINE_LABEL-\",\"model\":\"-TEST_MACHINE_MODEL-\",\"characteristics\":\"-TEST_MACHINE_CHARACTERISTICS-\",\"currentJobPart\":\"null\",\"currentPartInstances\":8,\"jobInstallmentDate\":\"null\"}";
		final String obtained = machine.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
	@Test
	public void toStringContractRunning() {
		// Given
		final Part part = new Part.Builder()
				.withId( UUID.fromString( "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColorCode( TEST_PART_COLOR_CODE )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final Machine machine = new Machine.Builder()
				.withId( UUID.fromString( "85403a7a-4bf8-4e99-bbc1-8283ea91f99b" ) )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withPart( part )
				.withInstallmentDate( OffsetDateTime.parse( "2020-06-04T13:27:04.062792" ) )
				.withInstances( TEST_MACHINE_CURRENTPARTINSTANCES )
				.build();
		// Test
		final String expected = "{\"id\":\"85403a7a-4bf8-4e99-bbc1-8283ea91f99b\",\"label\":\"-TEST_MACHINE_LABEL-\",\"model\":\"-TEST_MACHINE_MODEL-\",\"characteristics\":\"-TEST_MACHINE_CHARACTERISTICS-\",\"currentJobPart\":\"{\\\"id\\\":\\\"85403a7a-4bf8-4e99-bbc1-8283ea91f99b\\\",\\\"label\\\":\\\"-TEST_PART_LABEL-\\\",\\\"description\\\":\\\"-TEST_PART_DESCRIPTION-\\\",\\\"material\\\":\\\"PLA\\\",\\\"colorCode\\\":\\\"VERDE-T\\\",\\\"buildTime\\\":60,\\\"cost\\\":0.76,\\\"price\\\":2.0,\\\"stockLevel\\\":4,\\\"stockAvailable\\\":4,\\\"imagePath\\\":\\\"https:\\\\\\/\\\\\\/ibb.co\\\\\\/3dGbsRh\\\",\\\"modelPath\\\":\\\"pieza3.STL\\\",\\\"active\\\":false}\",\"currentPartInstances\":8,\"jobInstallmentDate\":\"2020-06-04T13:27:04.062792\"}";
		final String obtained = machine.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
