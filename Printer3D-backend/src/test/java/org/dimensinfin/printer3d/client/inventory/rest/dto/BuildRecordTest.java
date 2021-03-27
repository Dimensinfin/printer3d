package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.BuildRecordConstants.TEST_BUILDRECORD_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.BuildRecordConstants.TEST_BUILDRECORD_JOBINSTALLMENTDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.BuildRecordConstants.TEST_BUILDRECORD_PARTCOPIES;

public class BuildRecordTest {
	@Test
	public void buildContract() {
		final Part part = Mockito.mock( Part.class );
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( part )
				.withJobInstallmentDate( TEST_BUILDRECORD_JOBINSTALLMENTDATE )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.withPartBuildTime( TEST_BUILDRECORD_BUILD_TIME )
				.build();
		Assertions.assertNotNull( buildRecord );
	}

	@Test
	public void buildFailure() {
		final Part part = Mockito.mock( Part.class );
		Assertions.assertThrows( NullPointerException.class, () -> {
			BuildRecord buildRecord = new BuildRecord.Builder()
					.withPart( part )
					.withJobInstallmentDate( TEST_BUILDRECORD_JOBINSTALLMENTDATE )
					.withPartCopies( null )
					.build();
		} );
	}

	@Test
	public void buildIdle() {
		// Test
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( null )
				.withJobInstallmentDate( TEST_BUILDRECORD_JOBINSTALLMENTDATE )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.withPartBuildTime( 0 )
				.build();
		// Assertions
		Assertions.assertEquals( BuildState.IDLE, buildRecord.getState() );
		Assertions.assertNull( buildRecord.getPart() );
		Assertions.assertNull( buildRecord.getJobInstallmentDate() );
		Assertions.assertEquals( TEST_BUILDRECORD_PARTCOPIES, buildRecord.getPartCopies() );
		Assertions.assertEquals( 0, buildRecord.getJobBuildTime() );
		Assertions.assertEquals( 0, buildRecord.getRemainingTime() );
	}

	@Test
	public void buildNoCopies() {
		// Given
		final Part part = Mockito.mock( Part.class );
		BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( part )
				.withJobInstallmentDate( TEST_BUILDRECORD_JOBINSTALLMENTDATE )
				.withPartCopies( null )
				.withPartBuildTime( TEST_BUILDRECORD_BUILD_TIME )
				.build();
		// Assertions
		Assertions.assertEquals( BuildState.RUNNING, buildRecord.getState() );
		Assertions.assertEquals( TEST_BUILDRECORD_JOBINSTALLMENTDATE, buildRecord.getJobInstallmentDate() );
		Assertions.assertEquals( 1, buildRecord.getPartCopies() );
		assertThat( buildRecord.getRemainingTime() ).isBetween( TEST_BUILDRECORD_BUILD_TIME * 60 - 20, TEST_BUILDRECORD_BUILD_TIME * 60 );

		buildRecord = new BuildRecord.Builder()
				.withPart( part )
				.withJobInstallmentDate( TEST_BUILDRECORD_JOBINSTALLMENTDATE )
				.withPartBuildTime( TEST_BUILDRECORD_BUILD_TIME )
				.build();
		// Assertions
		Assertions.assertEquals( BuildState.RUNNING, buildRecord.getState() );
		Assertions.assertEquals( TEST_BUILDRECORD_JOBINSTALLMENTDATE, buildRecord.getJobInstallmentDate() );
		Assertions.assertEquals( 1, buildRecord.getPartCopies() );
		assertThat( buildRecord.getRemainingTime() ).isBetween( TEST_BUILDRECORD_BUILD_TIME * 60 - 20, TEST_BUILDRECORD_BUILD_TIME * 60 );
	}

	@Test
	public void clearJob() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( part )
				.withJobInstallmentDate( TEST_BUILDRECORD_JOBINSTALLMENTDATE )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.withPartBuildTime( TEST_BUILDRECORD_BUILD_TIME )
				.build();
		// Assertions
		Assertions.assertEquals( BuildState.RUNNING, buildRecord.getState() );
		Assertions.assertNotNull( buildRecord.getPart() );
		Assertions.assertEquals( TEST_BUILDRECORD_JOBINSTALLMENTDATE, buildRecord.getJobInstallmentDate() );
		Assertions.assertEquals( TEST_BUILDRECORD_PARTCOPIES, buildRecord.getPartCopies() );
		// Test
		buildRecord.clearJob();
		// Assertions
		Assertions.assertEquals( BuildState.IDLE, buildRecord.getState() );
		Assertions.assertNull( buildRecord.getPart() );
		Assertions.assertNull( buildRecord.getJobInstallmentDate() );
		Assertions.assertEquals( 1, buildRecord.getPartCopies() );
	}

	@Test
	public void getRemainingTime() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( part )
				.withJobInstallmentDate( Instant.now().minus( Duration.ofMinutes( 3 ) ) )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.withPartBuildTime( TEST_BUILDRECORD_BUILD_TIME )
				.build();
		// When
		Mockito.when( part.getBuildTime() ).thenReturn( 15 );
		// Assertions
		Assertions.assertEquals( BuildState.RUNNING, buildRecord.getState() );
		Assertions.assertNotNull( buildRecord.getPart() );
		Assertions.assertEquals( TEST_BUILDRECORD_PARTCOPIES, buildRecord.getPartCopies() );
		final int newBuildTime = (TEST_BUILDRECORD_BUILD_TIME * 60 * TEST_BUILDRECORD_PARTCOPIES) - 3 * 60;
		Assertions.assertEquals( newBuildTime, buildRecord.getRemainingTime() );
	}

	@Test
	public void getRemainingTimeIdle() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( null )
				.withJobInstallmentDate( Instant.now().minus( Duration.ofMinutes( 3 ) ) )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.build();
		// When
		Mockito.when( part.getBuildTime() ).thenReturn( 15 );
		// Assertions
		Assertions.assertEquals( BuildState.IDLE, buildRecord.getState() );
		Assertions.assertNull( buildRecord.getPart() );
		Assertions.assertEquals( TEST_BUILDRECORD_PARTCOPIES, buildRecord.getPartCopies() );
		Assertions.assertEquals( 0, buildRecord.getRemainingTime() );
	}

	@Test
	public void getterContract() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( part )
				.withJobInstallmentDate( TEST_BUILDRECORD_JOBINSTALLMENTDATE )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.withPartBuildTime( TEST_BUILDRECORD_BUILD_TIME )
				.build();
		// Assertions
		Assertions.assertEquals( BuildState.RUNNING, buildRecord.getState() );
		Assertions.assertNotNull( buildRecord.getPart() );
		Assertions.assertEquals( TEST_BUILDRECORD_JOBINSTALLMENTDATE, buildRecord.getJobInstallmentDate() );
		Assertions.assertEquals( TEST_BUILDRECORD_PARTCOPIES, buildRecord.getPartCopies() );
	}

	@Test
	public void toStringContract() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final BuildRecord buildRecord = new BuildRecord.Builder()
				.withPart( null )
				.withJobInstallmentDate( Instant.now().minus( Duration.ofMinutes( 3 ) ) )
				.withPartCopies( TEST_BUILDRECORD_PARTCOPIES )
				.build();
		// Test
		final String expected = "{\"state\":\"IDLE\",\"part\":null,\"partCopies\":8,\"buildTime\":0,\"jobInstallmentDate\":\"null\"}";
		final String obtained = buildRecord.toString();
		// Assertions
		Assertions.assertEquals( expected, obtained );
	}
}
