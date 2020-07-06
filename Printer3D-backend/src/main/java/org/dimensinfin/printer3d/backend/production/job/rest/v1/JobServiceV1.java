package org.dimensinfin.printer3d.backend.production.job.rest.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.exception.LogWrapperLocal;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.domain.FinishingContainer;
import org.dimensinfin.printer3d.backend.production.domain.StockManager;
import org.dimensinfin.printer3d.backend.production.job.FinishingByCountComparator;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestEntityV2Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

@Service
public class JobServiceV1 {
	private static final RequestEntityToRequestEntityV2Converter requestV1ToV2Converter = new RequestEntityToRequestEntityV2Converter();
	private static final int REQUEST_PRIORITY = 1;
	private static final int STOCK_LEVEL_PRIORITY = 2;
	private final PartRepository partRepository;
	private final RequestsRepository requestsRepository;
	private final RequestsRepositoryV2 requestsRepositoryV2;
	private final ModelRepository modelRepository;
	private final StockManager stockManager;

	// - C O N S T R U C T O R S
	public JobServiceV1( final PartRepository partRepository,
	                     final RequestsRepository requestsRepository,
	                     final RequestsRepositoryV2 requestsRepositoryV2,
	                     final ModelRepository modelRepository ) {
		this.partRepository = Objects.requireNonNull( partRepository );
		this.requestsRepository = Objects.requireNonNull( requestsRepository );
		this.requestsRepositoryV2 = requestsRepositoryV2;
		this.modelRepository = modelRepository;
		this.stockManager = new StockManager( this.partRepository );
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
	 * 3. Within priority 2 jobs sort by finishing count being the finishing with more hits up on the list.
	 *
	 * @return the list of build jobs required to complete the requests and the stocks.
	 */
	public List<Job> getPendingJobs() {
		LogWrapper.enter();
		try {
			this.stockManager.startStock(); // Initialize the stock with the current repository values.
			this.collectRequestPartsFromRepository();
			// Initialize the result list
			final List<Job> jobs = new ArrayList<>( this.generateMissingRequestJobs() );
			this.stockManager.clean().startStock(); // Initialize the stock with the current repository values.
			this.reserveModels(); // Subtract the model parts from the inventory before leveling.
			jobs.addAll( this.sortByFinishingCount( this.generateStockLevelJobs() ) );
			return jobs;
		} finally {
			LogWrapper.exit();
		}
	}

	private void collectRequestPartsFromRepository() {
		LogWrapper.enter();
		try {
			Stream.concat(
					this.requestsRepository.findAll()
							.stream()
							.filter( RequestEntity::isOpen )
							.map( requestV1ToV2Converter::convert ),
					this.requestsRepositoryV2.findAll().stream() )
					.filter( RequestEntityV2::isOpen )
					.forEach( requestEntityV2 -> {
						// Subtract the Parts from the inventory
						for (RequestItem content : requestEntityV2.getContents()) {
							LogWrapper.info( "Processing Request: " + requestEntityV2.getId().toString() );
							if (content.getType() == RequestContentType.PART) {
								this.stockManager.minus( content.getItemId(), content.getQuantity() ); // Subtract the request
							}
							if (content.getType() == RequestContentType.MODEL) {
								for (RequestItem modelContent : this.modelBOM( content.getItemId(), content.getQuantity() )) {
									this.stockManager.minus( modelContent.getItemId(), modelContent.getQuantity() ); // Subtract the request
								}
							}
						}
					} );
		} catch (final RuntimeException rte) {
			LogWrapperLocal.error( rte );
		} finally {
			LogWrapper.exit();
		}
	}

	private String generateFinishingKey( final Part target ) {
		return target.getMaterial() + ":" + target.getColor();
	}

	private List<FinishingContainer> generateFinishingList( final List<Job> inputJobs ) {
		final Map<String, FinishingContainer> finishings = new HashMap<>(); // Initialize the result list
		for (Job job : inputJobs) {
			final String key = this.generateFinishingKey( job.getPart() );
			finishings.computeIfAbsent( key, finishingContainer -> new FinishingContainer.Builder().build() );
			finishings.compute( key, ( String targetKey, FinishingContainer container ) -> Objects.requireNonNull( container ).addJob( job ) );
		}
		LogWrapper.info( "Finishings: " + finishings.values().toString() );
		return new ArrayList<>( finishings.values() );
	}

	private List<Job> generateMissingRequestJobs() {
		LogWrapper.enter();
		final List<Job> jobs = new ArrayList<>(); // Initialize the result list
		try {
			// Generate the jobs after processing all Requests
			for (UUID stockId : this.stockManager.getStockIterator()) {
				if (this.stockManager.getStock( stockId ) < 0) {
					LogWrapper.info( "Stock missing: " + stockId.toString() );
					jobs.addAll( this.generateRequestJobs( stockId, Math.abs( this.stockManager.getStock( stockId ) ) ) );
				}
			}
			return jobs;
		} finally {
			LogWrapper.exit( "RequestJobList count:" + jobs.size() + "" );
		}
	}

	private List<Job> generateRequestJobs( final UUID partId, final int jobCount ) {
		LogWrapper.enter( "partId: " + partId.toString() + " jobcount: " + jobCount + "" );
		try {
			final List<Job> jobs = new ArrayList<>(); // Initialize the result list
			final Optional<PartEntity> partOpt = this.partRepository.findById( partId );
			if (partOpt.isEmpty()) throw new DimensinfinRuntimeException( ErrorInfo.PART_NOT_FOUND.getErrorMessage( partId ) );
			for (int i = 0; i < jobCount; i++)
				jobs.add( new Job.Builder().withPart( new PartEntityToPartConverter().convert(
						partOpt.get()
				) ).withPriority( REQUEST_PRIORITY ).build() );
			return jobs;
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * This processing step does not have into account the new stock levels that can be set after Request processing. The new data leveling uses
	 * the current repository information that does not include the Requests demands. This is correct from the standpoint of the current timeline.
	 * If the system wants to look ahead and be prepared for new demand while keeping the stocks levels then the process should have on account the
	 * expected new values for the stock levels.
	 */
	private List<Job> generateStockLevelJobs() {
		LogWrapper.enter();
		final List<Job> jobs = new ArrayList<>(); // Initialize the result list
		try {
			this.partRepository.findAll().forEach( part -> {
				final int missingParts = part.getStockLevel() - this.stockManager.getStock( part.getId() ); // Account for model requirements.
				LogWrapper.info( "Stock: " + part.getStockLevel() + " count: " + this.stockManager.getStock( part.getId() ) );
				LogWrapper.info( "Missing: " + missingParts );
				for (int count = 0; count < missingParts; count++)
					jobs.add( new Job.Builder().withPart(
							new PartEntityToPartConverter().convert( part )
					).withPriority( STOCK_LEVEL_PRIORITY ).build() );
			} );
			return jobs;
		} finally {
			LogWrapper.exit( "Job count: " + jobs.size() );
		}
	}

	private List<RequestItem> modelBOM( final UUID modelId, final int modelQuantity ) {
		final Map<UUID, Integer> contents = new HashMap<>();
		final ModelEntity model = this.modelRepository.findById( modelId ).orElseThrow();
		Integer hit;
		for (UUID contentId : model.getPartIdList()) {
			contents.putIfAbsent( contentId, 0 );
			hit = contents.get( contentId );
			contents.put( contentId, ++hit );
		}
		final List<RequestItem> resultContents = new ArrayList<>();
		for (Map.Entry<UUID, Integer> targetContent : contents.entrySet())
			resultContents.add( new RequestItem.Builder()
					.withItemId( targetContent.getKey() )
					.withQuantity( targetContent.getValue() * modelQuantity )
					.withType( RequestContentType.PART ).build() );
		return resultContents;
	}

	private void reserveModels() {
		this.modelRepository.findAll()
				.stream()
				.filter( modelEntity -> modelEntity.isActive() )
				.forEach( modelEntity -> {
					for (UUID partId : modelEntity.getPartIdList())
						this.stockManager.minus( partId, modelEntity.getStockLevel() ); // Subtract the part instance required for the model					}
				} );
	}

	private List<Job> sortByFinishingCount( final List<Job> inputList ) {
		final List<FinishingContainer> finishings = this.generateFinishingList( inputList );
		Collections.sort(finishings, new FinishingByCountComparator());
		return finishings
				.stream()
//				.sorted( ( fin1, fin2 ) -> new FinishingByCountComparator().compare( fin1, fin2 ) )
				.flatMap( finishingContainer -> finishingContainer.getJobs().stream() )
				.collect( Collectors.toList() );
	}
}
