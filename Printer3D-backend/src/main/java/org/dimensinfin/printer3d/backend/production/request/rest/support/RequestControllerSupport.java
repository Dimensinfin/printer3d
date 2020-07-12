package org.dimensinfin.printer3d.backend.production.request.rest.support;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryException;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestConverter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestToRequestEntityConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
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
	private final RequestsRepositoryV2 requestsRepositoryV2;
	//	private final RequestServiceV1 requestServiceV1;

	// - C O N S T R U C T O R S
	public RequestControllerSupport( final @NotNull RequestsRepository requestsRepository,
	                                 final @NotNull RequestsRepositoryV2 requestsRepositoryV2 ) {
		this.requestsRepository = Objects.requireNonNull( requestsRepository );
		this.requestsRepositoryV2 = requestsRepositoryV2;
		//		this.requestServiceV1 = requestServiceV1;
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

	@GetMapping(path = "/production/requests/v2/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CountResponse> deleteAllRequestsV2() {
		return new ResponseEntity<>( this.deleteAllRequestsServiceV2(), HttpStatus.OK );
	}

	@PostMapping(path = "/production/requests",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Request> newRequest( final @RequestBody @Valid @NotNull Request request ) {
		return new ResponseEntity<>( this.newRequestService( request ), HttpStatus.CREATED );
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
			throw new RepositoryException( Printer3DErrorInfo.REQUEST_STORE_REPOSITORY_FAILURE( new SQLException( sqle ) ),
					"Detected exception while deleting all Requests from the repository." );
		}
	}

	protected CountResponse deleteAllRequestsServiceV2() {
		try {
			final long recordCount = this.requestsRepositoryV2.count();
			this.requestsRepositoryV2.deleteAll();
			return new CountResponse.Builder()
					.withRecords( (int) recordCount )
					.build();
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new RepositoryException( Printer3DErrorInfo.REQUEST_STORE_REPOSITORY_FAILURE( new SQLException( sqle ) ),
					"Detected exception while deleting all Requests from the repository." );
		}
	}

	protected Request newRequestService( final Request newRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If found reject the request because this should be a new creation.
			final Optional<RequestEntity> target = this.requestsRepository.findById( newRequest.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( Printer3DErrorInfo.REQUEST_ALREADY_EXISTS( newRequest.getId() ) );
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
