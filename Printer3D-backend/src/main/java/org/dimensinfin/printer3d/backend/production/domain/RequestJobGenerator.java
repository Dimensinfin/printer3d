package org.dimensinfin.printer3d.backend.production.domain;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.rest.v1.PartServiceV1;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

public class RequestJobGenerator {
	private static final int REQUEST_PRIORITY = 1;
	private final PartRepository partRepository;

	// - C O N S T R U C T O R S
	public RequestJobGenerator( final @NotNull PartRepository partRepository ) {this.partRepository = partRepository;}

	/**
	 * Generate the list of jobs required to complete all open Requests. The list should be ordered by the number of copies requires being the Part
	 * with more missing copies the first on the list and so on.
	 * The method requires an already processed stock where all the parts required for the Requests have been already removed.
	 * THe Part count included the already missing copies before the Requests are processed so the final number of copies should also be the count
	 * required to level all the stocks levels.
	 *
	 * @param stockManager the stock list to be used for the job generation.
	 * @return the ordered list of jobs to generate the parts required by all the open requests.
	 */
	public List<Job> generateMissingRequestJobs( final StockManager stockManager ) {
		LogWrapper.enter();
		final List<Job> jobs = new ArrayList<>(); // Initialize the result list
		try {
			for (UUID stockId : stockManager.getStockIterator()) { // Generate jobs to reach the required stock level.
				if (stockManager.getStock( stockId ) < 0) {
					LogWrapper.info( MessageFormat.format( "Missing stock [{0}]", stockId ) );
					jobs.addAll( this.generateRequestJobs( stockId, Math.abs( stockManager.getStock( stockId ) ) ) );
				} else
					LogWrapper.info( MessageFormat.format( "Processing stock [{0}] OK", stockId ) );
			}
			return jobs;
		} finally {
			LogWrapper.exit( MessageFormat.format( "RequestJobList count: {0}", jobs.size() ) );
		}
	}

	/**
	 * Generates a list of identical jobs for the number of copies as parameter. The jobs generated are the required jobs to complete open requests
	 * so their priority is set accordingly.
	 *
	 * @param partId   the part to build on the job.
	 * @param jobCount the number of job copies.
	 * @return the new list of jobs.
	 */
	private List<Job> generateRequestJobs( final UUID partId, final int jobCount ) {
		LogWrapper.enter( MessageFormat.format( "Generate jobs: partId [{0}] {1} copies", partId, jobCount ) );
		try {
			final List<Job> jobs = new ArrayList<>(); // Initialize the result list
			final PartEntity partOpt = this.partRepository.findById( partId )
					.orElseThrow( () -> new DimensinfinRuntimeException( PartServiceV1.errorPARTNOTFOUND( partId ) ) );
			for (int i = 0; i < jobCount; i++)
				jobs.add( new Job.Builder().withPart(
						new PartEntityToPartConverter().convert( partOpt )
				).withPriority( REQUEST_PRIORITY ).build() );
			return jobs;
		} finally {
			LogWrapper.exit();
		}
	}
}
