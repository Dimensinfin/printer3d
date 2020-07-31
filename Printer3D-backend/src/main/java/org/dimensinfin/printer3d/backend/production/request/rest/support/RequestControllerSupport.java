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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityV2ToRequestV2Converter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.backend.production.request.rest.RequestServiceCore;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

@RestController
@Validated
@RequestMapping("/api/v1")
@Service
public class RequestControllerSupport  extends RequestServiceCore {
	private static final RequestEntityV2ToRequestV2Converter requestEntityV2ToRequestV2Converter = new RequestEntityV2ToRequestV2Converter();
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	public RequestControllerSupport( final @NotNull PartRepository partRepository,
	                                 final @NotNull ModelRepository modelRepository,
	                                 final @NotNull RequestsRepositoryV2 requestsRepositoryV2 ) {
		super( partRepository , modelRepository);
		this.requestsRepositoryV2 = requestsRepositoryV2;
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping("/production/requests/repository")
	public ResponseEntity<List<RequestV2>> getRepositoryRequests() {
		return new ResponseEntity<>( this.getRepositoryRequestsService(), HttpStatus.OK );
	}

	private List<RequestV2> getRepositoryRequestsService() {
		return this.requestsRepositoryV2.findAll()
				.stream()
				.map( requestEntityV2ToRequestV2Converter::convert )
				.collect( Collectors.toList() );
	}

	@GetMapping(path = "/production/requests/v2/delete/all",
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
}
