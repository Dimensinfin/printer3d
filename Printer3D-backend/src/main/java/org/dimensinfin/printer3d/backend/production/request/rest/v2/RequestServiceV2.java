package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.common.exception.DimensinfinError;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.domain.StockManager;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestEntityV2Converter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityV2ToRequestV2Converter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestV2ToRequestEntityV2Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import static org.dimensinfin.printer3d.backend.Printer3DApplication.APPLICATION_ERROR_CODE_PREFIX;

@Service
public class RequestServiceV2 {
	private static final RequestEntityToRequestEntityV2Converter requestV1ToV2Converter = new RequestEntityToRequestEntityV2Converter();
	private static final RequestEntityV2ToRequestV2Converter requestConverterV2 = new RequestEntityV2ToRequestV2Converter();

	public static DimensinfinError REQUEST_NOT_FOUND( final UUID requestId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_NOT_FOUND" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".defined.repository.logic" )
				.withHttpStatus( HttpStatus.NOT_FOUND )
				.withMessage( MessageFormat.format( "Request record with id [{0}] not found at the repository.", requestId ) )
				.build();
	}

	public static DimensinfinError REQUEST_NOT_IN_CORRECT_STATE( final UUID requestId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_NOT_IN_CORRECT_STATE" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".logic.exception" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format(
						"Request record with id [{0}] is not on the correct state to perform the requested operation.", requestId ) )
				.build();
	}

	public static DimensinfinError REQUEST_ALREADY_EXISTS( final UUID requestId ) {
		return new DimensinfinError.Builder()
				.withErrorName( "REQUEST_ALREADY_EXISTS" )
				.withErrorCode( APPLICATION_ERROR_CODE_PREFIX + ".already.exists" )
				.withHttpStatus( HttpStatus.CONFLICT )
				.withMessage( MessageFormat.format(
						"Request with id [{0}] already exists at the repository. This should not be possible and means a development defect.",
						requestId ) )
				.build();
	}


	private final StockManager stockManager;
	private final RequestsRepository requestsRepositoryV1;
	private final RequestsRepositoryV2 requestsRepositoryV2;
	private final ModelRepository modelRepository;
	private final PartRepository partRepository;

	// - C O N S T R U C T O R S
	public RequestServiceV2( final @NotNull PartRepository partRepository,
	                         final @NotNull RequestsRepository requestsRepositoryV1,
	                         final @NotNull RequestsRepositoryV2 requestsRepositoryV2,
	                         final @NotNull ModelRepository modelRepository ) {
		this.requestsRepositoryV1 = Objects.requireNonNull( requestsRepositoryV1 );
		this.requestsRepositoryV2 = requestsRepositoryV2;
		this.modelRepository = modelRepository;
		this.partRepository = partRepository;

		this.stockManager = new StockManager( partRepository );
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
	public List<RequestV2> getOpenRequests() {
		LogWrapper.enter();
		try {
			this.stockManager.startStock(); // Initialize the stock with the current Part stock data
			// Get the list of OPEN Requests for processing. Join the old V1 to the new V2 requests after tranformation.
			return Stream.concat(
					this.requestsRepositoryV1.findAll()
							.stream()
							.filter( RequestEntity::isOpen )
							.map( requestV1ToV2Converter::convert ),
					this.requestsRepositoryV2.findAll()
							.stream()
							.filter( RequestEntityV2::isOpen ) )
					.map( requestEntityV2 -> {
						if (this.collectItemsFromStock( requestEntityV2 )) requestEntityV2.signalCompleted();
						return requestConverterV2.convert( requestEntityV2 );
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
	public RequestV2 closeRequest( final UUID requestId ) {
		LogWrapper.enter();
		try {
			this.stockManager.clean().startStock(); // Initialize the stock manager to get the Part prices.
			final Optional<RequestEntity> targetV1 = this.requestsRepositoryV1.findById( requestId );
			final Optional<RequestEntityV2> targetV2 = this.requestsRepositoryV2.findById( requestId );
			if (targetV1.isEmpty() && targetV2.isEmpty())
				throw new DimensinfinRuntimeException(
						REQUEST_NOT_FOUND( requestId ),
						"No Request found while trying to complete the Request." );
			// Transform the targets to the common V2 structure.
			RequestEntityV2 requestIntermediateEntity = null;
			if (targetV1.isPresent())
				requestIntermediateEntity = new RequestEntityToRequestEntityV2Converter().convert( targetV1.get() );
			if (targetV2.isPresent())
				requestIntermediateEntity = targetV2.get();
			final RequestEntityV2 requestEntityV2 = requestIntermediateEntity;
			this.stockManager.clean().startStock();
			if (this.collectItemsFromStock( requestEntityV2 )) { // Check that the Request can be closed now. Data on frontend may be obsolete.
				this.removeRequestPartsFromStock( requestEntityV2 );
				requestEntityV2.setAmount( this.calculateRequestAmount( requestEntityV2 ) );
				// When closing V1 requests we can save the data into V2 requests and then remove the V1 copy.
				targetV1.ifPresent( requestEntityV1lambda -> {
					this.requestsRepositoryV2.save( requestEntityV2.close() );
					this.requestsRepositoryV1.delete( requestEntityV1lambda );
				} );
				targetV2.ifPresent( requestEntityV2lambda -> this.requestsRepositoryV2.save( requestEntityV2lambda.close() ) );
				return new RequestEntityV2ToRequestV2Converter().convert( requestEntityV2.close() );
			} else throw new RepositoryConflictException( Printer3DErrorInfo.REQUEST_CANNOT_BE_FULFILLED( requestEntityV2.getId() ) );
		} finally {
			LogWrapper.exit();
		}
	}

	public CounterResponse deleteRequest( final UUID requestId ) {
		LogWrapper.enter();
		try {
			// Search for the Request by id. Search on both repositories
			final Optional<RequestEntity> targetV1 = this.requestsRepositoryV1.findById( requestId );
			final Optional<RequestEntityV2> targetV2 = this.requestsRepositoryV2.findById( requestId );
			if (targetV1.isEmpty() && targetV2.isEmpty())
				throw new DimensinfinRuntimeException( REQUEST_NOT_FOUND( requestId ),
						"No Request found while trying to delete the request." );
			targetV1.ifPresent( request -> {
				if (request.isOpen())
					this.requestsRepositoryV1.delete( request );
				else
					throw new RepositoryConflictException( REQUEST_NOT_IN_CORRECT_STATE( requestId ) );
			} );
			targetV2.ifPresent( request -> {
				if (request.isOpen())
					this.requestsRepositoryV2.delete( request );
				else
					throw new RepositoryConflictException( REQUEST_NOT_IN_CORRECT_STATE( requestId ) );
			} );
			return new CounterResponse.Builder().withRecords( 1 ).build();
		} finally {
			LogWrapper.exit();
		}
	}

	public RequestV2 newRequest( final RequestV2 newRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If found reject the request because this should be a new creation.
			final Optional<RequestEntityV2> target = this.requestsRepositoryV2.findById( newRequest.getId() );
			if (target.isPresent())
				throw new RepositoryConflictException( REQUEST_ALREADY_EXISTS( newRequest.getId() ) );
			return new RequestEntityV2ToRequestV2Converter().convert(
					this.requestsRepositoryV2.save(
							new RequestV2ToRequestEntityV2Converter().convert( newRequest )
					)
			);
		} finally {
			LogWrapper.exit();
		}
	}
	private float calculateRequestAmount( final RequestEntityV2 requestEntityV2 ) {
		float amount = 0.0F;
		for (RequestItem item : requestEntityV2.getContents()) {
			if (item.getType() == RequestContentType.PART) {
				final float targetPartPrice = this.stockManager.getPrice( item.getItemId() );
				amount = amount + item.getQuantity() * targetPartPrice;
			}
			if (item.getType() == RequestContentType.MODEL) {
				final Optional<ModelEntity> model = this.modelRepository.findById( item.getItemId() );
				if (model.isPresent()) {
					for (UUID modelPartId : model.get().getPartIdList()) {
						amount = amount + this.stockManager.getPrice( modelPartId ) * item.getQuantity();
					}
				}
			}
		}
		return amount;
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
		for (RequestItem content : requestEntityV2.getContents()) {
			if (content.getType() == RequestContentType.PART) {
				int missing = this.stockManager.minus( content.getItemId(), content.getQuantity() );
				if (missing < 0) { // Subtract the request quantity from the stock.
					underStocked = true;
					content.setMissing( Math.abs( missing ) );
				}
			}
			if (content.getType() == RequestContentType.MODEL)
				for (RequestItem modelContent : this.modelBOM( content.getItemId(), content.getQuantity() )) {
					int missing = this.stockManager.minus( modelContent.getItemId(), modelContent.getQuantity() );
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

	private void removeRequestPartsFromStock( final RequestEntityV2 requestEntityV2 ) {
		// Update the parts stocks reducing the stock with the Request quantities.
		for (RequestItem content : Objects.requireNonNull( requestEntityV2 ).getContents()) {
			if (content.getType() == RequestContentType.PART) {
				final Optional<PartEntity> partOpt = this.partRepository.findById( content.getItemId() );
				partOpt.ifPresent( partEntity -> {
					partEntity.decrementStock( content.getQuantity() );
					this.partRepository.save( partEntity );
				} );
			}
			if (content.getType() == RequestContentType.MODEL) {
				final Optional<ModelEntity> modelEntityOpt = this.modelRepository.findById( content.getItemId() );
				modelEntityOpt.ifPresent( modelEntity -> {
					for (UUID partIdentifier : modelEntity.getPartIdList()) {
						final Optional<PartEntity> partOpt = this.partRepository.findById( partIdentifier );
						partOpt.ifPresent( partEntity -> {
							partEntity.decrementStock( content.getQuantity() );
							this.partRepository.save( partEntity );
						} );
					}
				} );
			}
		}
	}
}
