package org.dimensinfin.printer3d.backend.inventory.machine.serializer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;

public class MachineListV2SerializerTest {
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach() {
		this.objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer( MachineListV2.class, new MachineListV2Serializer() );
		objectMapper.registerModule( module );
	}

	@Test
	public void serialize() throws JsonProcessingException {
		// Given
		final List<MachineV2> machines = new ArrayList<>();
		final MachineListV2 machineListV2 = new MachineListV2.Builder().withMachines( machines ).build();
		// Test
		final String expected = "{\"count\":0,\"machines\":[]}";
		final String obtained = objectMapper.writeValueAsString( machineListV2 );
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
