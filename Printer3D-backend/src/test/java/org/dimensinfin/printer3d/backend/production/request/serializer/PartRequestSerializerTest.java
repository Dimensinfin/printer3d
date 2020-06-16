package org.dimensinfin.printer3d.backend.production.request.serializer;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;

public class PartRequestSerializerTest {
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach() {
		this.objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer( PartRequest.class, new PartRequestSerializer() );
		objectMapper.registerModule( module );
	}

	@Test
	public void serialize() throws JsonProcessingException {
		// Given
		final PartRequest partRequest = new PartRequest.Builder()
				.withPartId( UUID.fromString( "98be9442-edbb-47fe-bc20-60b9e6f4a315" ) )
				.withQuantity( 12 )
				.build();
		// Testing
		final String expected = "{\"partId\":\"98be9442-edbb-47fe-bc20-60b9e6f4a315\",\"quantity\":12}";
		final String obtained = objectMapper.writeValueAsString( partRequest );
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
