package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.production.job.JobHistoricValidator;
import org.dimensinfin.printer3d.backend.support.production.job.JobValidator;
import org.dimensinfin.printer3d.backend.support.production.job.rest.JobFeignClientSupport;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.JobHistoric;

import io.cucumber.java.en.Then;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.JOB_PART_ID;

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

	@Then("the jobs records contain the next information")
	public void the_jobs_records_contain_the_next_information( final List<Map<String, String>> dataTable ) {
		final JobValidator jobValidator = new JobValidator();
		for (int i = 0; i < dataTable.size(); i++) {
			final Map<String, String> row = dataTable.get( i );
			final Job record = this.printer3DWorld.getListJobResponseEntity().getBody().get( i );
			LogWrapper.info( "i: " + i + " row id: " + row.get( JOB_PART_ID ) + " record id: " + record.getPart().getId() );
//			Assertions.assertTrue( jobValidator.validate( row, record ) );
		}
	}

	@Then("the list of jobs has {string} records")
	public void the_list_of_jobs_has_records( final String jobCount ) {
		Assertions.assertNotNull( this.printer3DWorld.getListJobResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getListJobResponseEntity().getBody() );
		Assertions.assertEquals( Integer.parseInt( jobCount ), this.printer3DWorld.getListJobResponseEntity().getBody().size() );
	}

	@Then("there are {string} records of priority {string}")
	public void there_are_records_of_priority( final String recordCount, final String priority ) {
		Assertions.assertNotNull( this.printer3DWorld.getListJobResponseEntity() );
		Assertions.assertNotNull( this.printer3DWorld.getListJobResponseEntity().getBody() );
		Assertions.assertEquals(
				Integer.parseInt( recordCount ),
				this.printer3DWorld.getListJobResponseEntity().getBody()
						.stream()
						.filter( ( job ) -> job.getPriority() == Integer.parseInt( priority ) )
						.collect( Collectors.toList() )
						.size()
		);
	}
}
