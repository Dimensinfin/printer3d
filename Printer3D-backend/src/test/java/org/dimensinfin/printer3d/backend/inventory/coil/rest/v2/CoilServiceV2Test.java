package org.dimensinfin.printer3d.backend.inventory.coil.rest.v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.coil.converter.CoilEntityToCoilConverter;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_WEIGHT;

public class CoilServiceV2Test {
	private CoilRepository coilRepository;
	private CoilEntityToCoilConverter coilConverter;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
		this.coilConverter = Mockito.mock( CoilEntityToCoilConverter.class );
	}

	@Test
	public void constructorContract() {
		final CoilServiceV2 coilServiceV2 = new CoilServiceV2( this.coilRepository, this.coilConverter );
		Assertions.assertNotNull( coilServiceV2 );
	}

	@Test
	public void getCoils() {
		// Given
		final CoilEntity coilEntity = new CoilEntity.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withLabel( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		final List<CoilEntity> coils = new ArrayList<>();
		coils.add( coilEntity );
		coils.add( coilEntity );
		coils.add( coilEntity );
		// When
		Mockito.when( this.coilRepository.findAll() ).thenReturn( coils );
		// Test
		final CoilServiceV2 coilServiceV2 = new CoilServiceV2( this.coilRepository, this.coilConverter );
		final List<Coil> obtained = coilServiceV2.getCoils();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 3, obtained.size() );
	}
}