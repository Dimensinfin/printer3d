package org.dimensinfin.printer3d.backend.inventory.coil.persistence;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_WEIGHT;

public class CoilUpdaterTest {
	private static final String UPDATED = "-UPDATED";
	private CoilEntity coilEntity;

	@BeforeEach
	public void beforeEach() {
		this.coilEntity = new CoilEntity.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withLabel( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.withActive( TEST_COIL_ACTIVE )
				.build();
	}

	@Test
	public void constructorContract() {
		final CoilUpdater coilUpdater = new CoilUpdater( this.coilEntity );
		Assertions.assertNotNull( coilUpdater );
	}

	@Test
	public void update() {
		// Given
		final UpdateCoilRequest updateData = new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withWeight( 200 )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withLabel( TEST_COIL_COLOR )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		// Test
		final CoilUpdater coilUpdater = new CoilUpdater( this.coilEntity );
		final CoilEntity obtained = coilUpdater.update( updateData );
		// Assertions
		Assertions.assertNotNull( obtained );
	}

	@Test
	public void updateActive() {
		// Given
		final UpdateCoilRequest updateData = new UpdateCoilRequest.Builder()
				.withActive( true ).build();
		// Test
		final CoilUpdater coilUpdater = new CoilUpdater( this.coilEntity );
		final CoilEntity obtained = coilUpdater.update( updateData );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( true, obtained.getActive() );
		Assertions.assertNull( obtained.getDestructionTime() );
	}

	@Test
	public void updateLabel() {
		// Given
		final UpdateCoilRequest updateData = new UpdateCoilRequest.Builder()
				.withLabel( TEST_COIL_LABEL + UPDATED ).build();
		// Test
		final CoilUpdater coilUpdater = new CoilUpdater( this.coilEntity );
		final CoilEntity obtained = coilUpdater.update( updateData );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_COIL_LABEL + UPDATED, obtained.getLabel() );
	}

	@Test
	public void updateTradeMark() {
		// Given
		final UpdateCoilRequest updateData = new UpdateCoilRequest.Builder()
				.withTradeMark( TEST_COIL_TRADE_MARK + UPDATED ).build();
		// Test
		final CoilUpdater coilUpdater = new CoilUpdater( this.coilEntity );
		final CoilEntity obtained = coilUpdater.update( updateData );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_COIL_TRADE_MARK + UPDATED, obtained.getTradeMark() );
	}

	@Test
	public void updateUnActive() {
		// Given
		final UpdateCoilRequest updateData = new UpdateCoilRequest.Builder()
				.withActive( false ).build();
		// Test
		final CoilUpdater coilUpdater = new CoilUpdater( this.coilEntity );
		final CoilEntity obtained = coilUpdater.update( updateData );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( false, obtained.getActive() );
		Assertions.assertNotNull( obtained.getDestructionTime() );
		Assertions.assertTrue( 20000 > Math.abs( (Instant.now().plus( 1,
				ChronoUnit.DAYS ).toEpochMilli() - obtained.getDestructionTime().toEpochMilli()) )
		);
	}

	@Test
	public void updateWeight() {
		// Given
		final UpdateCoilRequest updateData = new UpdateCoilRequest.Builder()
				.withWeight( 200 ).build();
		// Test
		final CoilUpdater coilUpdater = new CoilUpdater( this.coilEntity );
		final CoilEntity obtained = coilUpdater.update( updateData );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 200, obtained.getWeight() );
	}
}