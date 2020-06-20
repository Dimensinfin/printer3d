package org.dimensinfin.printer3d.backend.steps;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.production.job.rest.JobFeignClientSupport;

import io.cucumber.java.en.Given;

public class GivenACleanJobsRepository {
	private final JobFeignClientSupport jobFeignClientSupport;

	// - C O N S T R U C T O R S
	public GivenACleanJobsRepository( final JobFeignClientSupport jobFeignClientSupport ) {
		this.jobFeignClientSupport = Objects.requireNonNull( jobFeignClientSupport );
	}

	@Given("a clean Jobs repository")
	public void a_clean_Jobs_repository() {
		final Integer records = this.jobFeignClientSupport.clearJobsRepositoryTable();
		Assertions.assertNotNull( records );
	}
}
