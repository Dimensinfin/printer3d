package org.dimensinfin.printer3d.backend.inventory.machine.serializer;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.inventory.rest.dto.BuildRecord;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.BuildRecordConstants.TEST_BUILDRECORD_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.BuildRecordConstants.TEST_BUILDRECORD_JOBINSTALLMENTDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.BuildRecordConstants.TEST_BUILDRECORD_PARTCOPIES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PROJECT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;

public class BuildRecordSerializerTest {
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach() {
		this.objectMapper = new ObjectMapper();
		final SimpleModule module = new SimpleModule();
		module.addSerializer( BuildRecord.class, new BuildRecordSerializer() );
		this.objectMapper.registerModule( module );
	}

	@Test
	public void serializeIdle() throws JsonProcessingException {
		// Given
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( null )
				.withJobInstallmentDate( TEST_BUILDRECORD_JOBINSTALLMENTDATE )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.build();
		// Test
		final String expected = "{\"state\":\"IDLE\",\"partCopies\":8,\"buildTime\":0,\"part\":null,\"jobInstallmentDate\":null,\"remainingTime\":0}";
		final String obtained = this.objectMapper.writeValueAsString( buildRecord );
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}

	@Test
	public void serializeRunning() throws JsonProcessingException {
		// Given
		final Part part = new Part.Builder()
				.withId( UUID.fromString( "a4ba0dd6-acde-483c-ad68-5efb9ac9886e" ) )
				.withLabel( TEST_PART_LABEL )
				.withProject( TEST_PART_PROJECT )
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
				.withJobInstallmentDate( Instant.parse( "2020-06-05T21:54:00.226181Z" ) )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.withPartBuildTime( TEST_BUILDRECORD_BUILD_TIME )
				.build();
		// Test
		final String expected = "{\"state\":\"RUNNING\",\"partCopies\":8,\"buildTime\":30,\"part\":{\"id\":\"a4ba0dd6-acde-483c-ad68-5efb9ac9886e\",\"label\":\"-TEST_PART_LABEL-\",\"project\":\"-TEST_PART_PROJECT-\",\"description\":\"-TEST_PART_DESCRIPTION-\",\"material\":\"PLA\",\"color\":\"VERDE-T\",\"weight\":1,\"buildTime\":60,\"cost\":0.76,\"price\":2.0,\"stockLevel\":4,\"stockAvailable\":4,\"imagePath\":\"https://ibb.co/3dGbsRh\",\"modelPath\":\"pieza3.STL\",\"active\":false,\"unavailable\":false},\"jobInstallmentDate\":\"2020-06-05T21:54:00.226181Z\",\"remainingTime\":0}";
		final String obtained = this.objectMapper.writeValueAsString( buildRecord );
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
