package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.production.job.JobHistoricValidator;
import org.dimensinfin.printer3d.backend.support.production.job.rest.JobFeignClientSupport;
import org.dimensinfin.printer3d.client.production.rest.dto.JobHistoric;

import io.cucumber.java.en.Then;

public class P3D05JobsSteps extends StepSupport {
	private final JobFeignClientSupport jobFeignClientSupport;

	// - C O N S T R U C T O R S
	public P3D05JobsSteps( final @NotNull Printer3DWorld printer3DWorld,
	                       final JobFeignClientSupport jobFeignClientSupport ) {
		super( printer3DWorld );
		this.jobFeignClientSupport = jobFeignClientSupport;
	}

	@Then("on the Jobs repository there is a record for Part {string} with the next data")
	public void on_the_Jobs_repository_there_is_a_record_for_Part_with_the_next_data( final String partId,
	                                                                                  final List<Map<String, String>> dataTable ) throws IOException {
		final ResponseEntity<List<JobHistoric>> jobsResponse = this.jobFeignClientSupport.getCompletedJobs();
		Assertions.assertNotNull( jobsResponse );
		Assertions.assertNotNull( jobsResponse.getBody() );
		final List<JobHistoric> jobs = jobsResponse.getBody();
		for (JobHistoric job : jobs) {
			if (partId.equalsIgnoreCase( job.getJobPartId().toString() )) {
				Assertions.assertTrue( new JobHistoricValidator().validate( dataTable.get( 0 ), job ) );
			}
		}
	}

	@Then("the list of jobs has {string} records")
	public void the_list_of_jobs_has_records( final String jobCount ) {
		Assertions.assertNotNull( this.printer3DWorld.getJobListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getJobListResponseEntity().getBody() );
		Assertions.assertEquals( Integer.parseInt( jobCount ), this.printer3DWorld.getJobListResponseEntity().getBody().size() );
	}

	@Then("there are {string} records of priority {string}")
	public void there_are_records_of_priority( final String recordCount, final String priority ) {
		Assertions.assertNotNull( this.printer3DWorld.getJobListResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getJobListResponseEntity().getBody() );
		Assertions.assertEquals(
				Integer.parseInt( recordCount ),
				this.printer3DWorld.getJobListResponseEntity().getBody()
						.stream()
						.filter( ( job ) -> job.getPriority() == Integer.parseInt( priority ) )
						.collect( Collectors.toList() )
						.size()
		);
	}
}
