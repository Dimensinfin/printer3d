package org.dimensinfin.printer3d.infrastructure.adapters.outbound.persistence.coil;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.support.TestInstanceGenerator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

class CoilAdapterTest {
	private CoilRepository coilRepository;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
	}

	@Test
	public void constructorContract() {
		final CoilAdapter coilAdapter = new CoilAdapter( this.coilRepository );
		Assertions.assertNotNull( coilAdapter );
	}

	@Test
	void findAll() {
		// Given
		final CoilEntity coilEntity = new TestInstanceGenerator().getCoilEntity();
		final List<CoilEntity> coilEntities = new ArrayList<>();
		coilEntities.add( coilEntity );
		coilEntities.add( coilEntity );
		coilEntities.add( coilEntity );
		final CoilAdapter coilAdapter = new CoilAdapter( this.coilRepository );
		// When
		Mockito.when( this.coilRepository.findAll()  ).thenReturn(  coilEntities);
		final List<Coil> sut = coilAdapter.findAll();
		// Then
		Assertions.assertEquals( 3,  sut.size());
	}
}