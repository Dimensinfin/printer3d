package org.dimensinfin.printer3d.backend.inventory.finishing.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;

public class FinishingsServiceV1Test {

	private CoilRepository coilRepository;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
	}

	@Test
	public void constructorContract() {
		final FinishingsServiceV1 finishingsServiceV1 = new FinishingsServiceV1( this.coilRepository );
		Assertions.assertNotNull( finishingsServiceV1 );
	}

	@Test
	public void getFinishings() {
		// Given
		final List<Coil> allCoils = new ArrayList<>();
		allCoils.add( new Coil.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( "PLA" )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "ROJO" )
				.build() );
		allCoils.add( new Coil.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( "PLA" )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "VERDE" )
				.build() );
		allCoils.add( new Coil.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( "PLA" )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "BLANCO" )
				.build() );
		allCoils.add( new Coil.Builder()
				.withId( UUID.randomUUID() )
				.withMaterial( "TPU" )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( "BLANCO" )
				.build() );
		// When
		Mockito.when( this.coilRepository.findAll( Mockito.any( Sort.class ) ) ).thenReturn( allCoils );
		// Test
		final FinishingsServiceV1 finishingsServiceV1 = new FinishingsServiceV1( this.coilRepository );
		final FinishingsResponse obtained = finishingsServiceV1.getFinishings();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getMaterials().size() );
		Assertions.assertEquals( 3, obtained.getMaterials().get( 0 ).getColors().size() );
	}
}
