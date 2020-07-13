package org.dimensinfin.printer3d.backend.production.job.rest.support;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryException;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobEntity;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.JobHistoric;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_JOBCOMPLETIONDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_JOBINSTALLMENTDATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_COPIES;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PART_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.JobConstants.TEST_JOB_PRICE;

public class JobControllerSupportTest {

	private JobRepository jobRepository;

	@BeforeEach
	public void beforeEach() {
		this.jobRepository = Mockito.mock( JobRepository.class );
	}

	@Test
	public void constructorContact() {
		final JobControllerSupport jobControllerSupport = new JobControllerSupport( this.jobRepository );
		Assertions.assertNotNull( jobControllerSupport );
	}

	@Test
	public void deleteAllJobs() {
		// When
		Mockito.when( this.jobRepository.count() ).thenReturn( (long) 2 );
		// Test
		final JobControllerSupport jobControllerSupport = new JobControllerSupport( this.jobRepository );
		final ResponseEntity<CounterResponse> obtained = jobControllerSupport.deleteAllJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 2, obtained.getBody().getRecords() );
	}

	@Test
	public void deleteAllJobsException() {
		// When
		Mockito.when( this.jobRepository.count() ).thenReturn( (long) 2 );
		Mockito.doThrow( RuntimeException.class ).when( this.jobRepository ).deleteAll();
		// Test
		final JobControllerSupport jobControllerSupport = new JobControllerSupport( this.jobRepository );
		Assertions.assertThrows( RepositoryException.class, () -> {
			 jobControllerSupport.deleteAllJobs();
		} );
	}

	@Test
	public void getCompletedJobs() {
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
		final List<JobEntity> jobs = new ArrayList<>();
		jobs.add( jobEntity );
		jobs.add( jobEntity );
		// When
		Mockito.when( this.jobRepository.findAll() ).thenReturn( jobs );
		// Test
		final JobControllerSupport jobControllerSupport = new JobControllerSupport( this.jobRepository );
		final ResponseEntity<List<JobHistoric>> obtained = jobControllerSupport.getCompletedJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		final List<JobHistoric> jobList = obtained.getBody();
		Assertions.assertEquals( 2, jobList.size() );
		Assertions.assertEquals( TEST_JOB_PART_ID, jobList.get( 0 ).getJobPartId() );
	}
}
