package org.dimensinfin.printer3d.backend.inventory.machine.serializer;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.BuildRecordConstants.TEST_BUILDRECORD_PARTCOPIES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_CHARACTERISTICS;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.MachineConstants.TEST_MACHINE_MODEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;

public class MachineV2SerializerTest {
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach() {
		this.objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer( MachineV2.class, new MachineV2Serializer() );
		objectMapper.registerModule( module );
	}

	@Test
	public void serialize() throws JsonProcessingException {
		// Given
		final Part part = new Part.Builder()
				.withId( UUID.fromString( "a4ba0dd6-acde-483c-ad68-5efb9ac9886e" ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
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
				.withJobInstallmentDate( OffsetDateTime.parse( "2020-06-05T21:54:00.226181+02:00" ) )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.build();
		final MachineV2 machineV2 = new MachineV2.Builder()
				.withId( UUID.fromString( "a4ba0dd6-acde-483c-ad68-5efb9ac9886e" ) )
				.withLabel( TEST_MACHINE_LABEL )
				.withModel( TEST_MACHINE_MODEL )
				.withCharacteristics( TEST_MACHINE_CHARACTERISTICS )
				.withBuildRecord( buildRecord )
				.build();
		// Test
		final String expected = "{\"id\":\"a4ba0dd6-acde-483c-ad68-5efb9ac9886e\",\"label\":\"-TEST_MACHINE_LABEL-\",\"model\":\"-TEST_MACHINE_MODEL-\",\"characteristics\":\"-TEST_MACHINE_CHARACTERISTICS-\",\"buildRecord\":{\"state\":\"RUNNING\",\"part\":{\"id\":\"a4ba0dd6-acde-483c-ad68-5efb9ac9886e\",\"label\":\"-TEST_PART_LABEL-\",\"description\":\"-TEST_PART_DESCRIPTION-\",\"material\":\"PLA\",\"color\":\"VERDE-T\",\"buildTime\":60,\"cost\":0.76,\"price\":2.0,\"stockLevel\":4,\"stockAvailable\":4,\"imagePath\":\"https://ibb.co/3dGbsRh\",\"modelPath\":\"pieza3.STL\",\"active\":false},\"partCopies\":8,\"jobInstallmentDate\":{\"offset\":{\"totalSeconds\":7200,\"id\":\"+02:00\",\"rules\":{\"fixedOffset\":true,\"transitions\":[],\"transitionRules\":[]}},\"nano\":226181000,\"year\":2020,\"monthValue\":6,\"dayOfMonth\":5,\"hour\":21,\"minute\":54,\"second\":0,\"dayOfWeek\":\"FRIDAY\",\"dayOfYear\":157,\"month\":\"JUNE\"},\"remainingTime\":0,\"running\":true}}";
		final String obtained = objectMapper.writeValueAsString( machineV2 );
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
