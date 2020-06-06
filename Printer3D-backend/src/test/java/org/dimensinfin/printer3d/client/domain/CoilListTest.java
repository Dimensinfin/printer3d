package org.dimensinfin.printer3d.client.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;

public class CoilListTest {
	@Test
	public void buildContract() {
		final CoilList coilList = new CoilList.Builder()
				.withCoilList( new ArrayList<>() )
				.build();
		Assertions.assertNotNull( coilList );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new CoilList.Builder()
					.withCoilList( null )
					.build();
		} );
	}

	@Test
	public void getterContract() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		final List<Coil> coilList = new ArrayList<>();
		coilList.add( coil );
		coilList.add( coil );
		coilList.add( coil );
		final CoilList coilListContainer = new CoilList.Builder()
				.withCoilList( coilList )
				.build();
		// Assertions
		Assertions.assertEquals( 3, coilListContainer.getCount() );
		Assertions.assertEquals( 3, coilListContainer.getCount() );
		Assertions.assertEquals( 3, coilListContainer.getCoils().size() );
	}
}
