package org.dimensinfin.printer3d.backend.production.request.rest.v3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityV2ToCustomerRequestResponseConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.backend.production.request.rest.CommonRequestService;
import org.dimensinfin.printer3d.backend.production.request.rest.RequestRestErrors;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

@Service
public class RequestServiceV3 extends CommonRequestService {
	// - C O N S T R U C T O R S
	public RequestServiceV3( final @NotNull PartRepository partRepository,
	                         final @NotNull ModelRepository modelRepository,
	                         final @NotNull RequestsRepositoryV2 requestsRepositoryV2 ) {
		super( partRepository, modelRepository, requestsRepositoryV2 );
	}

	// - G E T T E R S   &   S E T T E R S
	public List<CustomerRequestResponseV2> getClosedRequestsV3() {
		LogWrapper.enter();
		try {
			return this.requestsRepositoryV2.findAll()
					.stream()
					.filter( RequestEntityV2::isClosed )
					.map( requestEntityV2ToCustomerRequestResponseConverter::convert )
					.collect( Collectors.toList() );
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * Get the complete list of **Open Requests** persisted at the Production repository. Open requests are those that are in the **OPEN** or
	 * **COMPLETED** states, meaning that are still on production and not delivered to the customer.
	 * During the processing each of the requests in FIFO sequence is processed against the Part stocks. If there are stocks for all the Parts in the
	 * request the state changes to COMPLETED during this request. The Part stocks are updated for each Request processed.
	 *
	 * @return the list of Requests on the repository that are still on the construction process and not delivered.
	 */
	public List<CustomerRequestResponseV2> getOpenRequestsV3() {
		LogWrapper.enter();
		try {
			this.stockManager.startStock(); // Initialize the stock with the current Part stock data
			// Get the list of OPEN Requests for processing. Join the old V1 to the new V2 requests after transformation.
			return this.requestsRepositoryV2.findAll()
					.stream()
					.filter( RequestEntityV2::isOpen )
					.sorted( Comparator.comparing( RequestEntityV2::getRequestDate ) )
					.map( requestEntityV2 -> {
						if (this.collectItemsFromStock( requestEntityV2 )) requestEntityV2.signalCompleted();
						return requestEntityV2ToCustomerRequestResponseConverter.convert( requestEntityV2 );
					} )
					.collect( Collectors.toList() );
		} catch (final RuntimeException rte) {
			LogWrapper.error( rte );
			return new ArrayList<>();
		} finally {
			LogWrapper.exit();
		}
	}

	public CustomerRequestResponseV2 deleteRequest( final UUID requestId ) {
		LogWrapper.enter();
		try {
			final var requestEntityV2 = this.selectRequestEntity( requestId );
			if (requestEntityV2.getState() == RequestState.DELIVERED)
				throw new RepositoryConflictException( RequestRestErrors.errorREQUESTINCORRECTSTATE( requestId ) );
			return new RequestEntityV2ToCustomerRequestResponseConverter().convert(
					this.requestsRepositoryV2.save( requestEntityV2.signalDeleted() )
			);
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * Changes the state of the Customer Request to the DELIVERED state. This means that the Request is fulfilled and that all elements are
	 * available and packaged ready to be delivered to the Customer.
	 * At this moment the Request may be unpaid and then the request moved to the **DELIVERED** state and it is left pending payment registration
	 * or it is paid and then the Request ends its flow because it can be moved directly to the **CLOSED** state after registering delivery date.
	 * Delivering will also perform the tasks to remove the Customer Request contents from the stocks. So when *delivering* a Request is when we
	 * update any other stock information to later recalculate Request states and stocks availability.
	 *
	 * @param requestId the unique identifier of the Customer Request to deliver.
	 * @return the response for the Request entity after state update.
	 */
	@Transactional
	public CustomerRequestResponseV2 deliverRequest( final UUID requestId ) {
		LogWrapper.enter();
		try {
			// Remove the request contents from the stocks.
			this.stockManager.clean().startStock(); // Initialize the stock manager to get the Part prices.
			final var requestEntityV2 = this.selectRequestEntity( requestId );
			if (this.collectItemsFromStock( requestEntityV2 )) { // Check that the Request can be delivered now. Data on frontend may be obsolete.
				this.removeRequestPartsFromStock( requestEntityV2 );
				return new RequestEntityV2ToCustomerRequestResponseConverter().convert(
						this.requestsRepositoryV2.save( requestEntityV2.signalDelivered() )
				);
			} else
				throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorREQUESTCANNOTBEFULFILLED( requestEntityV2.getId() ) );
		} finally {
			LogWrapper.exit();
		}
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
}