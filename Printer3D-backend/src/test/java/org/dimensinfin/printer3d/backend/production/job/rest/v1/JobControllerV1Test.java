package org.dimensinfin.printer3d.backend.production.job.rest.v1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.production.domain.Job;

public class JobControllerV1Test {

	private JobServiceV1 jobServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.jobServiceV1 = Mockito.mock(JobServiceV1.class);
	}

	@Test
	public void constructorContract() {
		final JobControllerV1 jobControllerV1=new JobControllerV1( this.jobServiceV1 );
		Assertions.assertNotNull( jobControllerV1 );
	}

	@Test
	public void getPendingJobs() {
		// Given
		final Job job = Mockito.mock(Job.class);
		final List<Job> jobs = new ArrayList<>();
		jobs.add(job);
		jobs.add(job);
		jobs.add(job);
		// When
		Mockito.when( this.jobServiceV1.getPendingJobs() ).thenReturn( jobs );
		// Test
		final JobControllerV1 jobControllerV1=new JobControllerV1( this.jobServiceV1 );
		final ResponseEntity<List<Job>> obtained = jobControllerV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 3, obtained.getBody().size() );
	}
}
