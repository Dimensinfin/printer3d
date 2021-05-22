package org.dimensinfin.printer3d.backend.production.request.rest.support;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.backend.production.request.rest.CommonRequestService;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

@RestController
@Validated
@Service
public class RequestControllerSupport extends CommonRequestService {
	// - C O N S T R U C T O R S
	public RequestControllerSupport( @NotNull final PartRepository partRepository,
	                                 @NotNull final ModelRepository modelRepository,
	                                 @NotNull final RequestsRepositoryV2 requestsRepositoryV2 ) {
		super( partRepository, modelRepository, requestsRepositoryV2 );
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping("/api/v1/production/requests/repository")
	public ResponseEntity<List<CustomerRequestResponseV2>> getRepositoryRequests() {
		return new ResponseEntity<>( this.getAllRepositoryRequests(), HttpStatus.OK );
	}

	@GetMapping(path = "/api/v2/production/requests/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CounterResponse> deleteAllRequestsV2() {
		return new ResponseEntity<>( this.deleteAllRequestsServiceV2(), HttpStatus.OK );
	}

	protected CounterResponse deleteAllRequestsServiceV2() {
		try {
			final long recordCount = this.requestsRepositoryV2.count();
			this.requestsRepositoryV2.deleteAll();
			return new CounterResponse.Builder()
					.withRecords( (int) recordCount )
					.build();
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorREQUESTSTOREREPOSITORYFAILURE( new SQLException( sqle ) ),
					"Detected exception while deleting all Requests from the repository." );
		}
	}

	private List<CustomerRequestResponseV2> getAllRepositoryRequests() {
		return this.requestsRepositoryV2.findAll()
				.stream()
				.map( requestEntityV2ToCustomerRequestResponseConverter::convert )
				.collect( Collectors.toList() );
	}
}
