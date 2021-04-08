package org.dimensinfin.printer3d.backend.inventory.coil.persistence;

import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_WEIGHT;

public class CoilEntityTest {
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass( CoilEntity.class )
				.withIgnoredFields( "id", "destructionTime" )
				.verify();
	}

	@Test
	public void getterContract() {
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
		// Assertions
		Assertions.assertEquals( TEST_COIL_ID, coilEntity.getId() );
		Assertions.assertEquals( TEST_COIL_MATERIAL, coilEntity.getMaterial() );
		Assertions.assertEquals( TEST_COIL_TRADE_MARK, coilEntity.getTradeMark() );
		Assertions.assertEquals( TEST_COIL_COLOR, coilEntity.getLabel() );
		Assertions.assertEquals( TEST_COIL_WEIGHT, coilEntity.getWeight() );
		Assertions.assertEquals( TEST_COIL_COLOR, coilEntity.getColor() );
		Assertions.assertEquals( TEST_COIL_ACTIVE, coilEntity.getActive() );
		Assertions.assertNull( coilEntity.getDestructionTime() );
	}

	@Test
	public void setterContract() {
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
		// Assertions
		Assertions.assertNull( coilEntity.getDestructionTime() );
		coilEntity.setDestructionTime( Instant.now() );
		Assertions.assertNotNull( coilEntity.getDestructionTime() );

		Assertions.assertEquals( TEST_COIL_TRADE_MARK, coilEntity.getTradeMark() );
		coilEntity.setTradeMark( "-NET-TRADE-MARK-" );
		Assertions.assertEquals( "-NET-TRADE-MARK-", coilEntity.getTradeMark() );

		Assertions.assertEquals( TEST_COIL_WEIGHT, coilEntity.getWeight() );
		coilEntity.setWeight( 543 );
		Assertions.assertEquals( 543, coilEntity.getWeight() );

		Assertions.assertEquals( TEST_COIL_COLOR, coilEntity.getLabel() );
		coilEntity.setLabel( "-COLOR-" );
		Assertions.assertEquals( "-COLOR-", coilEntity.getLabel() );
		Assertions.assertEquals( TEST_COIL_COLOR, coilEntity.getLabel() );

		Assertions.assertEquals( TEST_COIL_ACTIVE, coilEntity.getActive() );
		coilEntity.setActive( false );
		Assertions.assertEquals( false, coilEntity.getActive() );
	}
}