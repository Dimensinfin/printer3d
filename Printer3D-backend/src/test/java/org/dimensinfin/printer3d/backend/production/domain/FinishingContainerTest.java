package org.dimensinfin.printer3d.backend.production.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.client.production.rest.dto.Job;

public class FinishingContainerTest {
	@Test
	public void buildContract() {
		final FinishingContainer finishingContainer = new FinishingContainer.Builder().build();
		Assertions.assertNotNull( finishingContainer );
	}

	@Test
	public void getterContract() {
		// Given
		final Job job = Mockito.mock( Job.class );
		final FinishingContainer finishingContainer = new FinishingContainer.Builder().build();
		finishingContainer.addJob( job );
		finishingContainer.addJob( job );
		// Assertions
		Assertions.assertEquals( 2, finishingContainer.getJobCount() );
		Assertions.assertEquals( 2, finishingContainer.getJobs().size() );
	}
}
