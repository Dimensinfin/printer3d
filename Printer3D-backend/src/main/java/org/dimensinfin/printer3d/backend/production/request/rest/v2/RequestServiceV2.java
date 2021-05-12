package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityV2ToRequestV2Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.backend.production.request.rest.CommonRequestService;
import org.dimensinfin.printer3d.backend.production.request.rest.RequestRestErrors;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

@Service
public class RequestServiceV2 extends CommonRequestService {
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	public RequestServiceV2( final @NotNull PartRepository partRepository,
	                         final @NotNull RequestsRepositoryV2 requestsRepositoryV2,
	                         final @NotNull ModelRepository modelRepository ) {
		super( partRepository, modelRepository );
		this.requestsRepositoryV2 = requestsRepositoryV2;
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * This is an entity used by the customers to report the items they want to buy. The **Request** contains the list of
	 * specific Part models along with the material and color that are required by the customer. Also Requests can contain Models that are packaged
	 * sets of Parts that are sell together. Requests are processed by order of arrival when the list of current open requests is solicited by the
	 * frontend. The system will follow the first come/first served pattern. Then Part build jobs generated by Requests have a higher priority than
	 * other jobs generated by inventory leveling.
	 * A **Request** can be on different states. It starts on the state **OPEN** when the request is created. When the frontend requests the list of
	 * open requests then it is compared against the current stock levels. If all the items on the request are available then it moves to the state
	 * **COMPLETED**. If the user at the frontend delivers the request to the customer it can then close is and remove the items from the inventory.
	 * At this point the request moved to the final state of **CLOSED**.
	 *
	 * This is a temporal implementation that should make V1 requests compatible until they are all closed and them migrated to the new table
	 * before removal. This service will read first V1 requests and convert them to V2 before joining them with the V2 requests and do the common
	 * processing and reporting.
	 */
	public List<CustomerRequestRequestV2> getOpenRequests() {
		LogWrapper.enter();
		try {
			this.stockManager.startStock(); // Initialize the stock with the current Part stock data
			// Get the list of OPEN Requests for processing. Join the old V1 to the new V2 requests after tranformation.
			return this.requestsRepositoryV2.findAll()
					.stream()
					.filter( RequestEntityV2::isOpen )
					.map( requestEntityV2 -> {
						if (this.collectItemsFromStock( requestEntityV2 )) requestEntityV2.signalCompleted();
						return requestEntityV2ToRequestV2Converter.convert( requestEntityV2 );
					} )
					.collect( Collectors.toList() );
		} catch (final RuntimeException rte) {
			LogWrapper.error( rte );
			return new ArrayList<>();
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * When the Request is closed is the moment where the Parts that compose the request are subtracted from the Parts inventory.
	 * The close should be able to close requests on the old repository and also on the new repository. So the identifier should be searched on
	 * both repositories to locate the right instance to be closed.
	 *
	 * When saving the data if the origin is the V1 request then the record is converted to a V2 request end then deleted from the V1 repository.
	 *
	 * Exceptions: 404 NOT FOUND - If the request searched by the request id is not found on neither of the v1 or v2 repositories then we should
	 * raise this exception. This has to be reported to the system administrator because there can show a data corruption problem. Other
	 * cause can
	 * be that the Request has been closed before on another session and that frontend data is stale.
	 *
	 * @param requestId the request identifier for the Request being closed.
	 * @return Returns the current state of the Request on the repository.
	 */
	@Transactional
	public CustomerRequestRequestV2 closeRequest( final UUID requestId ) {
		LogWrapper.enter();
		try {
			this.stockManager.clean().startStock(); // Initialize the stock manager to get the Part prices.
			final Optional<RequestEntityV2> targetV2 = this.requestsRepositoryV2.findById( requestId );
			final RequestEntityV2 requestEntityV2 = this.selectRequestEntity( requestId );
			if (this.collectItemsFromStock( requestEntityV2 )) { // Check that the Request can be closed now. Data on frontend may be obsolete.
				this.removeRequestPartsFromStock( requestEntityV2 );
				requestEntityV2.setAmount( this.calculateRequestAmount( requestEntityV2 ) );
				targetV2.ifPresent( requestEntityV2lambda -> this.requestsRepositoryV2.save( requestEntityV2lambda.close() ) );
				return new RequestEntityV2ToRequestV2Converter().convert( requestEntityV2.close() );
			} else
				throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorREQUESTCANNOTBEFULFILLED( requestEntityV2.getId() ) );
		} finally {
			LogWrapper.exit();
		}
	}

	public CounterResponse deleteRequest( final UUID requestId ) {
		LogWrapper.enter();
		try {
			final AtomicInteger deleteCounter = new AtomicInteger( 0 );
			this.requestsRepositoryV2.findById( requestId ).ifPresent( request -> {
				if (request.isOpen()) {
					this.requestsRepositoryV2.delete( request );
					deleteCounter.incrementAndGet();
				} else
					throw new RepositoryConflictException( RequestRestErrors.errorREQUESTNOTINCORRECTSTATE( requestId ) );
			} );
			if (deleteCounter.get() == 0) throw new DimensinfinRuntimeException( RequestRestErrors.errorREQUESTNOTFOUND( requestId ),
					"No Request found while trying to delete a request." );
			return new CounterResponse.Builder().withRecords( deleteCounter.get() ).build();
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * Creates a new Customer Request on the initial state with the minimum fields. The initial Request can have two states, one the default and
	 * another where the request is pre paid.
	 *
	 * @param newRequest the new request Customer Request data.
	 * @return the persisted Customer request.
	 */
	public CustomerRequestRequestV2 newRequest( final CustomerRequestRequestV2 newRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If found reject the request because this should be a new creation.
			final Optional<RequestEntityV2> target = this.requestsRepositoryV2.findById( newRequest.getId() );
			if (target.isPresent())
				throw new RepositoryConflictException( RequestRestErrors.errorREQUESTALREADYEXISTS( newRequest.getId() ) );
			return new RequestEntityV2ToRequestV2Converter().convert(
					this.requestsRepositoryV2.save(
							new RequestEntityV2.Builder()
									.withId( newRequest.getId() )
									.withLabel( newRequest.getLabel() )
									.withCustomerData( newRequest.getCustomer() )
									.withRequestDate(
											(null != newRequest.getRequestDate()) ?
													Instant.parse( newRequest.getRequestDate() ) :
													Instant.now()
									)
									.withState( newRequest.getState() )
									.withTotal( newRequest.getTotal() )
									.withContents( newRequest.getContents() )
									.withPaid( newRequest.isPaid() )
									.build()
					)
			);
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * For each of the requests found get the contents and subtract the required number of items from the stock values. If any of the subtractions
	 * results in a negative value then the Request should be kept open and this is signaled by the returned boolean.
	 * Extract the Ids from the stock. Check if stock goes negative. For Models this expands to the BOM.
	 *
	 * @param requestEntityV2 the request entity that should be processed
	 * @return true if there are missing items. This means the Request should be kept open. If false the Request can be COMPLETED.
	 */
	private boolean collectItemsFromStock( final RequestEntityV2 requestEntityV2 ) {
		boolean underStocked = false;
		for (final RequestItem content : requestEntityV2.getContents()) {
			if (content.getType() == RequestContentType.PART) {
				final int missing = this.stockManager.minus( content.getItemId(), content.getQuantity() );
				if (missing < 0) { // Subtract the request quantity from the stock.
					underStocked = true;
					content.setMissing( Math.abs( missing ) );
				}
			}
			if (content.getType() == RequestContentType.MODEL)
				for (final RequestItem modelContent : this.modelBOM( content.getItemId(), content.getQuantity() )) {
					final int missing = this.stockManager.minus( modelContent.getItemId(), modelContent.getQuantity() );
					if (missing < 0) {// Subtract the request quantity from the stock.
						underStocked = true;
						content.setMissing(
								Math.min(
										content.getQuantity(),
										Math.max(
												modelContent.getMissing(),
												(int) Math.ceil( (float) Math.abs( missing ) / (float) modelContent.getQuantity() )
										)
								)
						);
					}
				}
		}
		return !underStocked;
	}

	private void removeRequestPartsFromStock( final RequestEntityV2 requestEntityV2 ) {
		// Update the parts stocks reducing the stock with the Request quantities.
		for (final RequestItem content : Objects.requireNonNull( requestEntityV2 ).getContents()) {
			if (content.getType() == RequestContentType.PART)
				this.decrementStock( content.getItemId(), content.getQuantity() );
			if (content.getType() == RequestContentType.MODEL) {
				final Optional<ModelEntity> modelEntityOpt = this.modelRepository.findById( content.getItemId() );
				modelEntityOpt.ifPresent( modelEntity -> {
					for (final UUID partIdentifier : modelEntity.getPartIdList())
						this.decrementStock( partIdentifier, content.getQuantity() );
				} );
			}
		}
	}

	private RequestEntityV2 selectRequestEntity( final UUID requestId ) {
		return this.requestsRepositoryV2.findById( requestId )
				.orElseThrow( () -> new DimensinfinRuntimeException( RequestRestErrors.errorREQUESTNOTFOUND( requestId ),
						"No Request found while trying to complete the Request." ) );
	}
}
