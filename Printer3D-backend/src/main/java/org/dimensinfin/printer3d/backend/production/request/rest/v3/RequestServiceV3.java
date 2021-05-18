package org.dimensinfin.printer3d.backend.production.request.rest.v3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.backend.production.request.rest.CommonRequestService;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

@Service
public class RequestServiceV3 extends CommonRequestService {
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	public RequestServiceV3( final @NotNull PartRepository partRepository,
	                         final @NotNull RequestsRepositoryV2 requestsRepositoryV2,
	                         final @NotNull ModelRepository modelRepository ) {
		super( partRepository, modelRepository );
		this.requestsRepositoryV2 = requestsRepositoryV2;
	}

	// - G E T T E R S   &   S E T T E R S
	public List<CustomerRequestResponseV2> getClosedRequestsV3() {
		LogWrapper.enter();
		try {
			return new ArrayList<>();
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
}