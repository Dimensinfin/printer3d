package org.dimensinfin.printer3d.backend.inventory.part.serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;

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

public class PartListSerializerTest {
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach() {
		this.objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer( PartList.class, new PartListSerializer() );
		objectMapper.registerModule( module );
	}

	@Test
	public void serialize() throws JsonProcessingException {
		// Given
		final Part part = new Part.Builder()
				.withId( UUID.fromString( "112ad653-9eea-4124-ab20-9fcd92d0527b" ) )
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
		final List<Part> partList = new ArrayList<>();
		partList.add( part );
		final PartList partContainer = new PartList.Builder()
				.withPartList( partList )
				.build();
		// Testing
		final String expected = "{\"jsonClass\":\"PartList\",\"count\":1,\"parts\":[{\"id\":\"112ad653-9eea-4124-ab20-9fcd92d0527b\",\"label\":\"-TEST_PART_LABEL-\",\"description\":\"-TEST_PART_DESCRIPTION-\",\"material\":\"PLA\",\"colorCode\":\"VERDE-T\",\"buildTime\":60,\"cost\":0.76,\"price\":2.0,\"stockLevel\":4,\"stockAvailable\":4,\"imagePath\":\"https://ibb.co/3dGbsRh\",\"modelPath\":\"pieza3.STL\",\"active\":false}]}";
		final String obtained = objectMapper.writeValueAsString( partContainer );
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
