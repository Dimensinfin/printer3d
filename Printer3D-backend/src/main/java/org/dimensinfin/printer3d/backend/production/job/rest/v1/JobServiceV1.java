package org.dimensinfin.printer3d.backend.production.job.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.production.domain.Job;

@Service
public class JobServiceV1 {
	private final PartRepository partRepository;

	// - C O N S T R U C T O R S
	public JobServiceV1( final PartRepository partRepository ) {
		this.partRepository = Objects.requireNonNull( partRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	/**
	 * This service scans the current inventory of active Parts. For each of the found parts it generates build jobs to increase the current stock
	 * values to reach the desired stock level if this is not reached.
	 * This is the basic job generation. The listing will return a list of build requests in the order of the Parts stored at the database and will
	 * all the jobs related to a specific Part together.
	 *
	 * There are cases where this ordering should be changed to increase stocks in a prioritized way or grouped by other criteria.
	 *
	 * @return the simplest list of build jobs required to complete the stocks.
	 */
	public List<Job> getPendingJobs() {
		// Get the list of parts
		final List<Job> jobs = new ArrayList<>();
		this.partRepository.findAll()
				.stream()
				.forEach( part -> {
					for (int stock = part.getStockAvailable(); stock < part.getStockLevel(); stock++)
						jobs.add( new Job.Builder().withPart( part ).build() );
				} );
		return jobs;
	}
}
