package org.dimensinfin.printer3d.backend.inventory.part.rest.v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.coil.converter.CoilEntityToCoilConverter;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.CoilConstants.TEST_COIL_WEIGHT;
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

public class PartServiceV2Test {
	private PartRepository partRepository;
	private CoilRepository coilRepository;
	private CoilEntityToCoilConverter coilConverter;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
		this.coilRepository = Mockito.mock( CoilRepository.class );
		this.coilConverter = new CoilEntityToCoilConverter();
	}

	@Test
	public void constructorContract() {
		final PartServiceV2 partServiceV2 = new PartServiceV2( this.partRepository, this.coilRepository, this.coilConverter );
		Assertions.assertNotNull( partServiceV2 );
	}

	@Test
	public void getPartsV2_match() {
		// Given
		final String MATERIAL = "-MATERIAL-";
		final String COLOR = "-COLOR-";
		final CoilEntity coil = new CoilEntity.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.build();
		final List<CoilEntity> coilList = new ArrayList<>();
		coilList.add( coil );
		final PartEntity part = new PartEntity.Builder()
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
		final List<PartEntity> partList = new ArrayList<>();
		partList.add( part );
		// When
		Mockito.when( this.coilRepository.findAll() ).thenReturn( coilList );
		Mockito.when( this.partRepository.findAll() ).thenReturn( partList );
		// Test
		final PartServiceV2 serviceV2 = new PartServiceV2( this.partRepository, this.coilRepository, this.coilConverter );
		final List<Part> obtained = serviceV2.getPartsV2();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 1, obtained.size() );
	}

	@Test
	public void getPartsV2_nomatch() {
		// Given
		final CoilEntity coil = new CoilEntity.Builder()
				.withId( TEST_COIL_ID )
				.withMaterial( TEST_COIL_MATERIAL )
				.withTradeMark( TEST_COIL_TRADE_MARK )
				.withColor( TEST_COIL_COLOR )
				.withWeight( TEST_COIL_WEIGHT )
				.build();
		final List<CoilEntity> coilList = new ArrayList<>();
		coilList.add( coil );
		final PartEntity part = new PartEntity.Builder()
				.withId( TEST_PART_ID )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( "-NO-MATCH-" )
				.withColor( "-NO-MATCH-" )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<PartEntity> partList = new ArrayList<>();
		partList.add( part );
		// When
		Mockito.when( this.coilRepository.findAll() ).thenReturn( coilList );
		Mockito.when( this.partRepository.findAll() ).thenReturn( partList );
		// Test
		final PartServiceV2 serviceV2 = new PartServiceV2( this.partRepository, this.coilRepository, this.coilConverter );
		final List<Part> obtained = serviceV2.getPartsV2();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 1, obtained.size() );
		Assertions.assertTrue( obtained.get( 0 ).isUnavailable() );
	}
}