package org.dimensinfin.printer3d.backend.inventory.part.serializer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.client.domain.PartList;

public class PartListSerializerTest {
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach() {
		this.objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer( PartList.class, new PartListSerializer() );
		objectMapper.registerModule( module );
	}

	//	@Test
	public void serialize() throws JsonProcessingException {
		// Given
		final Part part = Mockito.mock( Part.class );
		final List<Part> partList = new ArrayList<>();
		partList.add( part );
		final PartList partContainer = new PartList.Builder()
				.withCount( 2 )
				.withPartList( partList )
				.build();
		// Testing
		final String expected = "";
		final String obtained = objectMapper.writeValueAsString( partContainer );
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
