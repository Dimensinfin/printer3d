package org.dimensinfin.printer3d.backend.inventory.coil.rest.v1;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_WEIGHT;

public class CoilServiceV1Test {

	private CoilRepository coilRepository;

	@BeforeEach
	public void beforeEach() {
		this.coilRepository = Mockito.mock( CoilRepository.class );
	}

	@Test
	public void constructorContract() {
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		Assertions.assertNotNull( coilServiceV1 );
	}

	@Test
	public void newCoil() {
		// Given
		final Coil coil = new Coil.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withLabel( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		final CoilEntity coilEntity = new CoilEntity.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withLabel( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		// When
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.coilRepository.save( Mockito.any( CoilEntity.class ) ) ).thenReturn( coilEntity );
		// Test
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		final Coil obtained = coilServiceV1.newCoil( coil );
		// Assertions
		Assertions.assertNotNull( obtained );
	}

	@Test
	public void newCoilFound() {
		// Given
		final Coil coil = new Coil.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.build();
		final CoilEntity coilEntity = Mockito.mock( CoilEntity.class );
		// When
		Mockito.when( coilEntity.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( coilEntity ) );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
			coilServiceV1.newCoil( coil );
		} );
	}

	@Test
	public void updateCoil() {
		// Given
		final UpdateCoilRequest updateCoilRequest = new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withWeight( 200 )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withLabel( TEST_COIL_COLOR )
				.withActive( TEST_COIL_ACTIVE )
				.build();
		final CoilEntity coilEntity = new CoilEntity.Builder().withId( TEST_COIL_ID )
				.withId( UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" ) )
				.withColor( TEST_COIL_COLOR )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withWeight( TEST_COIL_WEIGHT )
				.build();
		// When
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( coilEntity ) );
		Mockito.when( this.coilRepository.save( Mockito.any( CoilEntity.class ) ) ).thenReturn( coilEntity );
		// Test
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		final Coil obtained = coilServiceV1.updateCoil( updateCoilRequest );
		// Assertions
		Assertions.assertEquals( "47461aa3-24f0-4cc5-a335-53e8bb61accc", obtained.getId().toString() );
	}

	@Test
	public void updateCoilNotFound() {
		// Given
		final UpdateCoilRequest updateCoilRequest = Mockito.mock( UpdateCoilRequest.class );
		// When
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Test
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			coilServiceV1.updateCoil( updateCoilRequest );
		} );
	}

	@Test
	public void updateCoilSqlException() {
		// Given
		final UpdateCoilRequest updateCoilRequest = Mockito.mock( UpdateCoilRequest.class );
		final Coil coil = Mockito.mock( Coil.class );
		// When
		Mockito.when( updateCoilRequest.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.doThrow( new IllegalArgumentException( "-TEST-EXCEPTION-MESSAGE-" ) )
				.when( this.coilRepository ).findById( Mockito.any( UUID.class ) );
		// Test
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		Assertions.assertThrows( IllegalArgumentException.class, () -> {
			coilServiceV1.updateCoil( updateCoilRequest );
		} );
	}
}
