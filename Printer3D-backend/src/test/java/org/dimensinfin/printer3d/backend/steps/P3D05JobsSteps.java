package org.dimensinfin.printer3d.backend.steps;

import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;

import io.cucumber.java.en.Then;

public class P3D05JobsSteps extends StepSupport {
	// - C O N S T R U C T O R S
	public P3D05JobsSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
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
