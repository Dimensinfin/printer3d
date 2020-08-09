package org.dimensinfin.printer3d.backend.production.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_STOCK_LEVEL;

public class JobSorterTest {
	private static final String PART_IDENTIFIER_1 = "49f536a4-9086-42d5-b61c-35adc73b3e58";
	private static final String PART_IDENTIFIER_2 = "c6b90686-a321-4f86-b567-a389b2a48624";

	@Test
	public void sortByFinishingCount() {
		// Given
		final Part part1 = new Part.Builder()
				.withId( UUID.fromString( PART_IDENTIFIER_1 ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( "ONE" )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final Part part2 = new Part.Builder()
				.withId( UUID.fromString( PART_IDENTIFIER_2 ) )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( "TWO" )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( TEST_PART_STOCK_LEVEL )
				.withStockAvailable( TEST_PART_STOCK_AVAILABLE )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<Job> inputJobList = new ArrayList<>();
		inputJobList.add( new Job.Builder().withPart( part1 ).build() );
		inputJobList.add( new Job.Builder().withPart( part2 ).build() );
		inputJobList.add( new Job.Builder().withPart( part2 ).build() );
		inputJobList.add( new Job.Builder().withPart( part1 ).build() );
		inputJobList.add( new Job.Builder().withPart( part1 ).build() );
		// Tests
		final JobSorter jobSorter = new JobSorter();
		final List<Job> obtained = jobSorter.sortByFinishingCount( inputJobList );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 5, obtained.size() );
		Assertions.assertEquals(
				UUID.fromString( PART_IDENTIFIER_1 ).toString(),
				obtained.get( 0 ).getPart().getId().toString()
		);
		Assertions.assertEquals(
				UUID.fromString( PART_IDENTIFIER_1 ).toString(),
				obtained.get( 1 ).getPart().getId().toString()
		);
		Assertions.assertEquals(
				UUID.fromString( PART_IDENTIFIER_1 ).toString(),
				obtained.get( 2 ).getPart().getId().toString()
		);
		Assertions.assertEquals(
				UUID.fromString( PART_IDENTIFIER_2 ).toString(),
				obtained.get( 3 ).getPart().getId().toString()
		);
		Assertions.assertEquals(
				UUID.fromString( PART_IDENTIFIER_2 ).toString(),
				obtained.get( 4 ).getPart().getId().toString()
		);
	}
}
