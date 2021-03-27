package org.dimensinfin.printer3d.backend.inventory.coil.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
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
	public void getCoils() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		final List<Coil> coils = new ArrayList<>();
		coils.add( coil );
		coils.add( coil );
		coils.add( coil );
		// When
		Mockito.when( this.coilRepository.findAll() ).thenReturn( coils );
		// Test
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		final CoilList obtained = coilServiceV1.getCoils();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 3, obtained.getCount() );
		Assertions.assertEquals( 3, obtained.getCoils().size() );
	}

	@Test
	public void newCoil() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		// When
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.coilRepository.save( Mockito.any( Coil.class ) ) ).thenReturn( coil );
		// Test
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		final Coil obtained = coilServiceV1.newCoil( coil );
		// Assertions
		Assertions.assertNotNull( obtained );
	}

	@Test
	public void newCoilFound() {
		// Given
		final Coil coil = Mockito.mock( Coil.class );
		// When
		Mockito.when( coil.getId() ).thenReturn( UUID.randomUUID() );
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( coil ) );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
			coilServiceV1.newCoil( coil );
		} );
	}

	@Test
	public void updateCoil() {
		// Given
		final UpdateCoilRequest updateCoilRequest = Mockito.mock( UpdateCoilRequest.class );
		final Coil coil = new Coil.Builder().withId( TEST_COIL_ID )
				.withColor( TEST_COIL_COLOR )
				.withMaterial( TEST_COIL_MATERIAL )
				.withWeight( TEST_COIL_WEIGHT )
				.build();
		final Coil savedCoil = Mockito.mock( Coil.class );
		final UUID uuid = UUID.fromString( "1b92bdbd-27f9-4668-8b52-0c2a67effad2" );
		// When
		Mockito.when( updateCoilRequest.getId() ).thenReturn( uuid );
		Mockito.when( updateCoilRequest.getWeight() ).thenReturn( 100 );
		Mockito.when( this.coilRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( coil ) );
		Mockito.when( this.coilRepository.save( Mockito.any( Coil.class ) ) ).thenReturn( savedCoil );
		Mockito.when( savedCoil.getId() ).thenReturn( uuid );
		// Test
		final CoilServiceV1 coilServiceV1 = new CoilServiceV1( this.coilRepository );
		final Coil obtained = coilServiceV1.updateCoil( updateCoilRequest );
		// Assertions
		Assertions.assertEquals( "1b92bdbd-27f9-4668-8b52-0c2a67effad2", obtained.getId().toString() );
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
