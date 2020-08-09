package org.dimensinfin.printer3d.backend.production.job.rest.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.domain.RequestJobGenerator;
import org.dimensinfin.printer3d.backend.production.domain.RequestPartCollector;
import org.dimensinfin.printer3d.backend.production.domain.StockLevelJobGenerator;
import org.dimensinfin.printer3d.backend.production.domain.StockManager;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

@Service
public class JobServiceV1 {
	private final PartRepository partRepository;
	private final ModelRepository modelRepository;
	private final RequestPartCollector requestPartCollector;
	private final RequestJobGenerator requestJobGenerator;
	private final StockLevelJobGenerator stockLevelJobGenerator;

	// - C O N S T R U C T O R S
	public JobServiceV1( final @NotNull PartRepository partRepository,
	                     final @NotNull ModelRepository modelRepository,
	                     final @NotNull RequestPartCollector requestPartCollector,
	                     final @NotNull RequestJobGenerator requestJobGenerator,
	                     final @NotNull StockLevelJobGenerator stockLevelJobGenerator ) {
		this.partRepository = Objects.requireNonNull( partRepository );
		this.modelRepository = modelRepository;
		this.requestPartCollector = requestPartCollector;
		this.requestJobGenerator = requestJobGenerator;
		this.stockLevelJobGenerator = stockLevelJobGenerator;
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * The pending list of jobs now has two targets. On the first priority level it is the fulfilment of all the Open Requests. Requests are then
	 * processed on order of arrival and if there are not enough Parts on the inventory to complete a Request then that missing Parts generate
	 * build jobs that should be completed on high priority.
	 * The second target is then add the jobs to complete the inventory levels. For that the system will search the **Inventory** needs and stocks
	 * mismatches to create a list of the Parts missing to reach the correct stocks levels for each part and each finishing.
	 * ## RULES
	 * 1. Jobs generated by Request missing parts have priority 1.
	 * 2. Jobs generated by stock leveling have priority 2.
	 * 3. Within priority 2 jobs sort by finishing count being the finishing with more elements up on the list.
	 *
	 * @return the list of build jobs required to complete the requests and the stocks.
	 */
	public List<Job> getPendingJobs() {
		LogWrapper.enter();
		try {
			// Initialize the result list
			final List<Job> jobs = new ArrayList<>();
			// Add the jobs required for Requests.
			jobs.addAll( this.requestJobGenerator.generateMissingRequestJobs(
					this.requestPartCollector.collectRequestPartsFromRepository( new StockManager( this.partRepository ).startStock() )
			) );
			// Add the jobs required to level the stocks after reserving models.
			jobs.addAll( this.stockLevelJobGenerator.generateStockLevelJobs(
					this.reserveModels( new StockManager( this.partRepository ).startStock() )
			) );
			return jobs;
		} finally {
			LogWrapper.exit();
		}
	}

	private StockManager reserveModels( final StockManager stockManager ) {
		this.modelRepository.findAll()
				.stream()
				.filter( ModelEntity::isActive )
				.forEach( modelEntity -> {
					for (UUID partId : modelEntity.getPartIdList())
						stockManager.minus( partId, modelEntity.getStockLevel() ); // Subtract the part instance required for the model
				} );
		return stockManager;
	}
}
