package org.dimensinfin.printer3d.backend.production.request.rest.v1;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.domain.StockManager;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestConverter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestToRequestEntityConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;

@Service
public class RequestServiceV1 {
	protected static final RequestEntityToRequestConverter requestConverter = new RequestEntityToRequestConverter();
	protected final RequestsRepository requestsRepository;
	protected final PartRepository partRepository;
	protected final StockManager stockManager;

	// - C O N S T R U C T O R S
	public RequestServiceV1( final @NotNull RequestsRepository requestsRepository,
	                         final @NotNull PartRepository partRepository ) {
		this.requestsRepository = Objects.requireNonNull( requestsRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
		this.stockManager = new StockManager( this.partRepository );
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * Get the complete list of **Requests** persisted at the Production repository and that are on an Open state. During the processing each of
	 * the requests in FIFO sequence is processed against the Part stocks. If there are stocks for all the Parts in the request the state changes
	 * to COMPLETED during this request. The Part stocks are updated for each Request processed.
	 *
	 * @return the list of OPEN or COMPLETED requests on the repository.
	 */
	public RequestList getOpenRequests() {
		LogWrapper.enter();
		try {
			this.stockManager.startStock(); // Initialize the stock with the current Part stock data
			// Get the list of OPEN Requests for processing.
			final RequestList requestList = new RequestList.Builder().build();
			this.requestsRepository.findAll()
					.stream()
					.filter( RequestEntity::isOpen )
					.forEach( request -> {
						// Extract the Parts from the stock. Check if stock goes negative.
						boolean underStocked = false;
						for (PartRequest partRequest : request.getPartList()) {
							this.stockManager
									.minus( partRequest.getPartId(), partRequest.getQuantity() ); // Subtract the request quantity from the stock.
							if (this.stockManager.getStock( partRequest.getPartId() ) < 0) // There is stock shortage. Generate jobs.
								underStocked = true;
						}
						if (!underStocked) request.signalCompleted();
						requestList.addRequest( requestConverter.convert( request ) );
					} );
			return requestList;
		} finally {
			LogWrapper.exit();
		}
	}

	/**
	 * When the Request is closed is the moment where the Parts that compose the request are subtracted from the Parts inventory.
	 *
	 * @param requestId the request identifier for the Request being closed.
	 */
	public Request closeRequest( final UUID requestId ) {
		LogWrapper.enter();
		try {
			final Optional<RequestEntity> target = this.requestsRepository.findById( requestId );
			if (target.isEmpty())
				throw new DimensinfinRuntimeException( ErrorInfo.REQUEST_NOT_FOUND, requestId.toString() );
			// Update the parts stocks reducing the stock with the Request quantities.
			for (PartRequest partRequest : target.get().getPartList()) {
				final Optional<PartEntity> partOpt = this.partRepository.findById( partRequest.getPartId() );
				partOpt.ifPresent( partEntity -> {
					partEntity.decrementStock( partRequest.getQuantity() );
					this.partRepository.save( partEntity );
				} );
			}
			return new RequestEntityToRequestConverter().convert( this.requestsRepository.save( target.get().close() ) );
		} finally {
			LogWrapper.exit();
		}
	}

	public Request newRequest( final Request newRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If found reject the request because this should be a new creation.
			final Optional<RequestEntity> target = this.requestsRepository.findById( newRequest.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( ErrorInfo.REQUEST_ALREADY_EXISTS, newRequest.getId().toString() );
			return new RequestEntityToRequestConverter().convert(
					this.requestsRepository.save(
							new RequestToRequestEntityConverter().convert( newRequest )
					)
			);
		} finally {
			LogWrapper.exit();
		}
	}
}
