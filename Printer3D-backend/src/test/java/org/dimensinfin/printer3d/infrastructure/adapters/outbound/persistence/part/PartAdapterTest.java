package org.dimensinfin.printer3d.infrastructure.adapters.outbound.persistence.part;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.application.ports.outbound.CoilPort;
import org.dimensinfin.printer3d.backend.inventory.coil.converter.CoilEntityToCoilConverter;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.support.TestInstanceGenerator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;

class PartAdapterTest {
	private CoilPort coilPort;
	private PartRepository partRepository;
	private PartEntityToPartConverter partConverter = new PartEntityToPartConverter();

	@BeforeEach
	public void beforeEach() {
		this.coilPort = Mockito.mock( CoilPort.class );
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final PartAdapter partAdapter = new PartAdapter( this.coilPort, this.partRepository );
		Assertions.assertNotNull( partAdapter );
	}

	@Test
	void findAll_isUnavailable() {
		// Given
		final Coil coil = new CoilEntityToCoilConverter().convert( new TestInstanceGenerator().getCoilEntity() );
		final List<Coil> coils = new ArrayList<>();
		coils.add( coil );
		coils.add( coil );
		coils.add( coil );
		final PartAdapter partAdapter = new PartAdapter( this.coilPort, this.partRepository );
		final PartEntity partEntity = new TestInstanceGenerator().getPartEntity();
		final List<PartEntity> partEntities = new ArrayList<>();
		partEntities.add( partEntity );
		partEntities.add( partEntity );
		partEntities.add( partEntity );
		partEntities.add( partEntity );
		partEntities.add( partEntity );
		// When
		Mockito.when( this.coilPort.findAll() ).thenReturn( coils );
		Mockito.when( this.partRepository.findAll() ).thenReturn( partEntities );
		// Then
		final List<Part> sut = partAdapter.findAll();
		Assertions.assertEquals( 5, sut.size() );
		Assertions.assertTrue( sut.get( 1 ).isUnavailable() );
	}

	@Test
	void findAll_isAavailable() {
		// Given
		final CoilEntity coilEntity= new TestInstanceGenerator().getCoilEntity();
		coilEntity.setActive( true );
		final Coil coil = new CoilEntityToCoilConverter().convert( coilEntity);
		final List<Coil> coils = new ArrayList<>();
		coils.add( coil );
		coils.add( coil );
		coils.add( coil );
		final PartAdapter partAdapter = new PartAdapter( this.coilPort, this.partRepository );
		// Connect to the available coil
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_COIL_MATERIAL )
				.withColor( TEST_COIL_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<PartEntity> partEntities = new ArrayList<>();
		partEntities.add( partEntity );
		partEntities.add( partEntity );
		partEntities.add( partEntity );
		partEntities.add( partEntity );
		partEntities.add( partEntity );
		// When
		Mockito.when( this.coilPort.findAll() ).thenReturn( coils );
		Mockito.when( this.partRepository.findAll() ).thenReturn( partEntities );
		// Then
		final List<Part> sut = partAdapter.findAll();
		Assertions.assertEquals( 5, sut.size() );
		Assertions.assertFalse( sut.get( 1 ).isUnavailable() );
	}
}