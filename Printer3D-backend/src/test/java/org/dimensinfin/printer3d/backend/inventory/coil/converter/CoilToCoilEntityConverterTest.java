package org.dimensinfin.printer3d.backend.inventory.coil.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_WEIGHT;

public class CoilToCoilEntityConverterTest {

	@Test
	public void convert() {
		// Given
		final Coil coil = new Coil.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		final CoilToCoilEntityConverter converter = new CoilToCoilEntityConverter();
		// Test
		final CoilEntity obtained = converter.convert( coil );
		// Assertions
		Assertions.assertEquals( TEST_COIL_ID, obtained.getId() );
		Assertions.assertEquals( TEST_COIL_MATERIAL, obtained.getMaterial() );
		Assertions.assertEquals( TEST_COIL_TRADE_MARK, obtained.getTradeMark() );
		Assertions.assertEquals( TEST_COIL_COLOR, obtained.getLabel() );
		Assertions.assertEquals( TEST_COIL_WEIGHT, obtained.getWeight() );
		Assertions.assertEquals( TEST_COIL_COLOR, obtained.getColor() );
		Assertions.assertEquals( TEST_COIL_ACTIVE, obtained.getActive() );
		Assertions.assertNull( obtained.getDestructionTime() );
	}
}