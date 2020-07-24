package org.dimensinfin.printer3d.backend.production.job.rest.support;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.production.job.converter.JobEntityToJobConverter;
import org.dimensinfin.printer3d.backend.production.job.persistence.JobRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.JobHistoric;

@Profile({ "local", "acceptance", "test" })
@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
@Service
public class JobControllerSupport {
	private final JobRepository jobRepository;

	// - C O N S T R U C T O R S
	public JobControllerSupport( final JobRepository jobRepository ) {
		this.jobRepository = Objects.requireNonNull( jobRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/production/jobs/completed",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<List<JobHistoric>> getCompletedJobs() {
		return new ResponseEntity<>( this.getCompletedJobsService(), HttpStatus.OK );
	}

	private List<JobHistoric> getCompletedJobsService() {
		LogWrapper.enter();
		try {
			return this.jobRepository.findAll()
					.stream()
					.map( ( jobEntity ) -> new JobEntityToJobConverter().convert( jobEntity ) )
					.collect( Collectors.toList() );
		} finally {
			LogWrapper.exit();
		}
	}

	@GetMapping(path = "/production/jobs/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CounterResponse> deleteAllJobs() {
		return new ResponseEntity<>( this.deleteAllJobsService(), HttpStatus.OK );
	}

	protected CounterResponse deleteAllJobsService() {
		try {
			final long recordCount = this.jobRepository.count();
			this.jobRepository.deleteAll();
			return new CounterResponse.Builder()
					.withRecords( (int) recordCount )
					.build();
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorREQUESTSTOREREPOSITORYFAILURE( new SQLException( sqle ) ),
					"Detected exception while deleting all Jobs from the repository." );
		}
	}
}
