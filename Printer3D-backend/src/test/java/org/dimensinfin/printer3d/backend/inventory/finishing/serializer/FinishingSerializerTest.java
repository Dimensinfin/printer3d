package org.dimensinfin.printer3d.backend.inventory.finishing.serializer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.domain.Finishing;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.FinishingConstants.TEST_FINISHING_MATERIAL;

public class FinishingSerializerTest {
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach() {
		this.objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer( Finishing.class, new FinishingSerializer() );
		objectMapper.registerModule( module );
	}

	@Test
	public void serialize() throws JsonProcessingException {
		// Given
		final List<String> colors = new ArrayList<>();
		colors.add( "ROJO" );
		colors.add( "BLANCO" );
		colors.add( "AMARILLO" );
		final Finishing finishing = new Finishing.Builder()
				.withMaterial( TEST_FINISHING_MATERIAL )
				.withColors( colors )
				.build();
		// Testing
		final String expected = "{\"material\":\"-TEST_FINISHING_MATERIAL-\",\"colors\":[\"ROJO\",\"BLANCO\",\"AMARILLO\"]}";
		final String obtained = objectMapper.writeValueAsString( finishing );
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
