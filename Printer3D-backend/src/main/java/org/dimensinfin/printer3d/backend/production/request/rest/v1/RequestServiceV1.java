package org.dimensinfin.printer3d.backend.production.request.rest.v1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestConverter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestToRequestEntityConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;

@Service
public class RequestServiceV1 {
	private static final RequestEntityToRequestConverter requestConverter = new RequestEntityToRequestConverter();
	private final RequestsRepository requestsRepository;
	private final PartRepository partRepository;

	// - C O N S T R U C T O R S
	public RequestServiceV1( final @NotNull RequestsRepository requestsRepository,
	                         final @NotNull PartRepository partRepository ) {
		this.requestsRepository = Objects.requireNonNull( requestsRepository );
		this.partRepository = Objects.requireNonNull( partRepository );
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
			// Fill the Parts stocks from the repository records.
			Map<UUID, Integer> stocks = new HashMap<>();
			this.partRepository.findAll().forEach( ( part ) ->
					stocks.put( part.getId(), part.getStockAvailable() )
			);
			// Get the list of OPEN Requests for processing.
			final RequestList requestList = new RequestList.Builder().build();
			this.requestsRepository.findAll()
					.stream()
					.filter( ( request ) -> request.isOpen() )
					.forEach( ( request ) -> {
						// Extract the Parts from the stock. Check if stock goes negative.
						boolean underStocked = false;
						for (PartRequest partRequest : request.getPartList()) {
							if (stocks.containsKey( partRequest.getPartId() )) {
								stocks.computeIfPresent( partRequest.getPartId(), ( UUID key, Integer value ) -> value - partRequest.getQuantity() );
								if (stocks.get( partRequest.getPartId() ) < 0)
									underStocked = true;
							} else throw new DimensinfinRuntimeException( ErrorInfo.REQUEST_PROCESSING_FAILURE.getErrorMessage(
									request.getId(), partRequest.getPartId()
							) );
						}
						if (!underStocked) request.signalCompleted();
						requestList.addRequest( this.requestConverter.convert( request ) );
					} );
			return requestList;
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
