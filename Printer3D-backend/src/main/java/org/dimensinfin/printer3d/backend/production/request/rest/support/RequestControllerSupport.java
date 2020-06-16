package org.dimensinfin.printer3d.backend.production.request.rest.support;

import java.sql.SQLException;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;

@Profile({ "local", "acceptance", "test" })
@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
@Service
public class RequestControllerSupport {
	private static final RequestEntityToRequestConverter requestEntityToRequestConverter = new RequestEntityToRequestConverter();
	private final RequestsRepository requestsRepository;

	// - C O N S T R U C T O R S
	public RequestControllerSupport( final @NotNull RequestsRepository requestsRepository ) {
		this.requestsRepository = Objects.requireNonNull( requestsRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping("/production/requests/repository")
	public ResponseEntity<RequestList> getRepositoryRequests() {
		return new ResponseEntity<>( this.getRepositoryRequestsService(), HttpStatus.OK );
	}

	private RequestList getRepositoryRequestsService() {
		final RequestList requestList = new RequestList.Builder().build();
		this.requestsRepository.findAll().forEach( ( requestEntity ) -> requestList.addRequest(
				requestEntityToRequestConverter.convert( requestEntity ) ) );
		return requestList;
	}

	@GetMapping(path = "/production/requests/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CountResponse> deleteAllRequests() {
		return new ResponseEntity<>( this.deleteAllRequestsService(), HttpStatus.OK );
	}

	protected CountResponse deleteAllRequestsService() {
		try {
			final long recordCount = this.requestsRepository.count();
			this.requestsRepository.deleteAll();
			return new CountResponse.Builder()
					.withRecords( (int) recordCount )
					.build();
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new RepositoryException( ErrorInfo.REQUEST_STORE_REPOSITORY_FAILURE, new SQLException( sqle ) );
		}
	}
}
