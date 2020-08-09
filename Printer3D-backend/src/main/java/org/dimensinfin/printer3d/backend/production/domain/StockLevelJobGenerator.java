package org.dimensinfin.printer3d.backend.production.domain;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

@Component
public class StockLevelJobGenerator {
	private static final int STOCK_LEVEL_PRIORITY = 2;
	private final PartRepository partRepository;

	// - C O N S T R U C T O R S
	public StockLevelJobGenerator( final @NotNull PartRepository partRepository ) {
		this.partRepository = partRepository;
	}

	/**
	 * Generate the jobs to level the stocks to the user set level demands. The stocks received may have been processed to comply with other user
	 * requirements to the process should not expect to start form a clean stock list.
	 * The list of jobs generated is ordered so the Part with most required number of copies is displayed first end so on.
	 *
	 * @param stockManager the stock list to be used for the job generation.
	 * @return the ordered list of jobs to generate the parts required to level the stock requirements.
	 */
	public List<Job> generateStockLevelJobs( final StockManager stockManager ) {
		LogWrapper.enter();
		final List<Job> jobs = new ArrayList<>(); // Initialize the result list
		try {
			this.partRepository.findAll().forEach( part -> {
				final int missingParts = part.getStockLevel() - stockManager.getStock( part.getId() ); // Account for model requirements.
				LogWrapper.enter( MessageFormat.format( "Generate jobs: [{0}-{1}] stock level/current: {2}/{3} missing: {4}",
						part.getId(), part.getLabel(), part.getStockLevel(), stockManager.getStock( part.getId() ), missingParts )
				);
				for (int count = 0; count < missingParts; count++)
					jobs.add( new Job.Builder()
							.withPart( new PartEntityToPartConverter().convert( part ) )
							.withPriority( STOCK_LEVEL_PRIORITY )
							.build()
					);
			} );
			return new JobSorter().sortByFinishingCount( jobs );
		} finally {
			LogWrapper.exit( MessageFormat.format( "Job count: {0}", jobs.size() ) );
		}
	}
}
