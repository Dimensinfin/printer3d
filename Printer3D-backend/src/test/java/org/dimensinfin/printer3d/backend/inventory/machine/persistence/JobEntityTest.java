package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_JOBCOMPLETIONDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_JOBINSTALLMENTDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_COPIES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PRICE;

public class JobEntityTest {
	@Test
	public void buildContract() {
		final JobEntity jobEntity = new JobEntity.Builder()
				.withPartId( TEST_JOB_PART_ID )
				.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
				.withBuildTime( TEST_JOB_BUILD_TIME )
				.withCost( TEST_JOB_COST )
				.withPrice( TEST_JOB_PRICE )
				.withPartCopies( TEST_JOB_PART_COPIES )
				.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
				.build();
		Assertions.assertNotNull( jobEntity );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new JobEntity.Builder()
					.withPartId( null )
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withCost( TEST_JOB_COST )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withJobInstallmentDate( null )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withCost( TEST_JOB_COST )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withBuildTime( null )
					.withCost( TEST_JOB_COST )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withCost( null )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withCost( TEST_JOB_COST )
					.withPrice( null )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withCost( TEST_JOB_COST )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( null )
					.build();
		} );
	}

	@Test
	public void buildNotBound() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new JobEntity.Builder()
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withCost( TEST_JOB_COST )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withCost( TEST_JOB_COST )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withCost( TEST_JOB_COST )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withCost( TEST_JOB_COST )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
					.build();
			new JobEntity.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
					.withBuildTime( TEST_JOB_BUILD_TIME )
					.withCost( TEST_JOB_COST )
					.withPrice( TEST_JOB_PRICE )
					.withPartCopies( TEST_JOB_PART_COPIES )
					.build();
		} );
	}

	@Test
	public void getterContract() {
		// Given
		final JobEntity jobEntity = new JobEntity.Builder()
				.withPartId( TEST_JOB_PART_ID )
				.withJobInstallmentDate( TEST_JOB_JOBINSTALLMENTDATE )
				.withBuildTime( TEST_JOB_BUILD_TIME )
				.withCost( TEST_JOB_COST )
				.withPrice( TEST_JOB_PRICE )
				.withPartCopies( TEST_JOB_PART_COPIES )
				.withJobBuildDate( TEST_JOB_JOBCOMPLETIONDATE )
				.build();
		// Assertions
		Assertions.assertNotNull( jobEntity.getId() );
		Assertions.assertEquals( TEST_JOB_PART_ID, jobEntity.getJobPartId() );
		Assertions.assertEquals( TEST_JOB_JOBINSTALLMENTDATE, jobEntity.getJobInstallmentDate() );
		Assertions.assertEquals( TEST_JOB_JOBCOMPLETIONDATE, jobEntity.getJobBuildDate() );
		Assertions.assertEquals( TEST_JOB_BUILD_TIME, jobEntity.getBuildTime() );
		Assertions.assertEquals( TEST_JOB_COST, jobEntity.getCost() );
		Assertions.assertEquals( TEST_JOB_PRICE, jobEntity.getPrice() );
		Assertions.assertEquals( TEST_JOB_PART_COPIES, jobEntity.getPartCopies() );
	}
}
