package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.exception.LogWrapperLocal;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
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

@Service
public class RequestServiceV2 {
	private static final RequestEntityToRequestEntityV2Converter requestV1ToV2Converter = new RequestEntityToRequestEntityV2Converter();
	private static final RequestEntityV2ToRequestV2Converter requestConverterV2 = new RequestEntityV2ToRequestV2Converter();
	private final StockManager stockManager;
	private final RequestsRepository requestsRepository;
	private final RequestsRepositoryV2 requestsRepositoryV2;
	private final ModelRepository modelRepository;

	// - C O N S T R U C T O R S
	public RequestServiceV2( final @NotNull StockManager stockManager,
	                         final @NotNull RequestsRepository requestsRepository,
	                         final @NotNull RequestsRepositoryV2 requestsRepositoryV2,
	                         final @NotNull ModelRepository modelRepository ) {
		this.stockManager = Objects.requireNonNull( stockManager );
		this.requestsRepository = Objects.requireNonNull( requestsRepository );
		this.requestsRepositoryV2 = requestsRepositoryV2;
		this.modelRepository = modelRepository;
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * This is an entity used by the customers to report the items they want to buy. The **Request** contains the list of
	 * specific Part models along with the material and color that are required by the customer. Also Requests can contain Models that are packaged
	 * sets of Parts that are sell together. Requests are processed by order of arrival when the list of current open requests is solicited by the
	 * frontend. The system will follow the first come/first served pattern. Then Part build jobs generated by Requests have a higher priority than
	 * other jobs generated by inventory leveling.
	 * A **Request** can be on different states. It starts on the state **OPEN** when the request is created. When the frontend requests the list of
	 * open
	 * buy requests then it is compared against the current stock levels. If all the items on the request are available then it moves to the state
	 * **COMPLETED**. If the user at the frontend delivers the request to the customer it can then close is and remove the items from the inventory.
	 * At
	 * this point the request moved to the final state of **CLOSED**.
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
					this.requestsRepository.findAll()
							.stream()
							.filter( RequestEntity::isOpen )
							.map( requestV1ToV2Converter::convert ),
					this.requestsRepositoryV2.findAll()
							.stream()
							.filter( RequestEntityV2::isOpen ) )
					.map( requestEntityV2 -> {
						// Extract the Ids from the stock. Check if stock goes negative. For Models this expands to the BOM.
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
												Math.max( content.getQuantity(),
														Math.max( modelContent.getMissing(), Math.abs( missing ) / modelContent.getQuantity() ) )
										);
									}
								}
						}
						if (!underStocked) requestEntityV2.signalCompleted();
						return requestConverterV2.convert( requestEntityV2 );
					} )
					.collect( Collectors.toList() );
		} catch (final RuntimeException rte) {
			LogWrapperLocal.error( rte );
			return null;
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
				throw new DimensinfinRuntimeException( ErrorInfo.REQUEST_ALREADY_EXISTS, newRequest.getId().toString() );
			return new RequestEntityV2ToRequestV2Converter().convert(
					this.requestsRepositoryV2.save(
							new RequestV2ToRequestEntityV2Converter().convert( newRequest )
					)
			);
		} finally {
			LogWrapper.exit();
		}
	}

	private List<RequestItem> modelBOM( final UUID modelId, final int modelQuantity ) {
		final List<RequestItem> contents = new ArrayList<>();
		final ModelEntity model = this.modelRepository.findById( modelId ).orElseThrow();
		for (UUID contentId : model.getPartIdList())
			contents.add( new RequestItem.Builder()
					.withItemId( contentId )
					.withQuantity( 1 * modelQuantity )
					.withType( RequestContentType.PART ).build() );
		return contents;
	}
}
