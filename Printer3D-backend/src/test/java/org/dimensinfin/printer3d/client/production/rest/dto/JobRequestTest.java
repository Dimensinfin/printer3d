package org.dimensinfin.printer3d.client.production.rest.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_COPIES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_ID;

public class JobRequestTest {
	@Test
	public void buildContract() {
		final JobRequest jobRequest = new JobRequest.Builder()
				.withId( TEST_JOB_ID )
				.withPartId( TEST_JOB_PART_ID )
				.withCopies( TEST_JOB_PART_COPIES )
				.build();
		Assertions.assertNotNull( jobRequest );
	}

	@Test
	public void buildFailure() {
		Assertions.assertThrows( NullPointerException.class, () -> {
			new JobRequest.Builder()
					.withId( null )
					.withPartId( TEST_JOB_PART_ID )
					.withCopies( TEST_JOB_PART_COPIES )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new JobRequest.Builder()
					.withId( TEST_JOB_ID )
					.withPartId( null )
					.withCopies( TEST_JOB_PART_COPIES )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new JobRequest.Builder()
					.withPartId( TEST_JOB_PART_ID )
					.withCopies( TEST_JOB_PART_COPIES )
					.build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new JobRequest.Builder()
					.withId( TEST_JOB_ID )
					.withCopies( TEST_JOB_PART_COPIES )
					.build();
		} );
	}

	@Test
	public void gettersContract() {
		// Given
		final JobRequest jobRequest = new JobRequest.Builder()
				.withId( TEST_JOB_ID )
				.withPartId( TEST_JOB_PART_ID )
				.withCopies( TEST_JOB_PART_COPIES )
				.build();
		Assertions.assertNotNull( jobRequest );
		// Assertions
		Assertions.assertEquals( TEST_JOB_ID.toString(), jobRequest.getId().toString() );
		Assertions.assertEquals( TEST_JOB_PART_ID.toString(), jobRequest.getPartId().toString() );
		Assertions.assertEquals( TEST_JOB_PART_COPIES, jobRequest.getCopies() );
	}
}
