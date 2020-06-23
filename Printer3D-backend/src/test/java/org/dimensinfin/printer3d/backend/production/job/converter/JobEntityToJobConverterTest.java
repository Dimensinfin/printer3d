package org.dimensinfin.printer3d.backend.production.job.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.inventory.machine.persistence.JobEntity;
import org.dimensinfin.printer3d.client.production.rest.dto.JobHistoric;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_JOBCOMPLETIONDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_JOBINSTALLMENTDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_COPIES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PRICE;

public class JobEntityToJobConverterTest {

	@Test
	public void convert() {
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
		final JobEntityToJobConverter jobEntityToJobConverter = new JobEntityToJobConverter();
		// Test
		final JobHistoric obtained = jobEntityToJobConverter.convert( jobEntity );
		// Assertions
		Assertions.assertEquals( jobEntity.getId().toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_JOB_PART_ID.toString(), obtained.getJobPartId().toString() );
		Assertions.assertEquals( TEST_JOB_JOBINSTALLMENTDATE.toString(), obtained.getJobInstallmentDate().toString() );
		Assertions.assertEquals( TEST_JOB_JOBCOMPLETIONDATE.toString(), obtained.getJobBuildDate().toString() );
		Assertions.assertEquals( TEST_JOB_BUILD_TIME, obtained.getBuildTime() );
		Assertions.assertEquals( TEST_JOB_COST, obtained.getCost(), 0.01);
		Assertions.assertEquals( TEST_JOB_PRICE, obtained.getPrice(),0.01);
		Assertions.assertEquals( TEST_JOB_PART_COPIES, obtained.getPartCopies());
	}
}
