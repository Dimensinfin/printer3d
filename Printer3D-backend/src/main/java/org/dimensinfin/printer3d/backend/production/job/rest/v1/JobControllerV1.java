package org.dimensinfin.printer3d.backend.production.job.rest.v1;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.production.rest.dto.Job;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class JobControllerV1 {
	private final JobServiceV1 jobServiceV1;

	// - C O N S T R U C T O R S
	public JobControllerV1( final JobServiceV1 jobServiceV1 ) {
		this.jobServiceV1 = Objects.requireNonNull( jobServiceV1 );
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/production/jobs/pending",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<Job>> getPendingJobs() {
		return new ResponseEntity<>( this.jobServiceV1.getPendingJobs(), HttpStatus.OK );
	}
}
