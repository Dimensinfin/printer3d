package org.dimensinfin.printer3d.backend;

import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_WEIGHT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Printer3DApplication.class)
@AutoConfigureMockMvc
public class Printer3DApplicationIT {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private CoilRepository coilRepository;

	@Test
	public void validateCoilCreation() throws Exception {
		final Coil coil = new Coil.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withLabel( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		this.mockMvc.perform( post( "/api/v1/inventory/coils" )
				.contentType( "application/json" )
				.content( this.objectMapper.writeValueAsString( coil ) ) )
				.andExpect( status().isCreated() );
		final Optional<CoilEntity> coilEntity = this.coilRepository.findById( coil.getId() );
		Assertions.assertEquals( coilEntity.get().getColor(), TEST_COIL_COLOR );
	}
}