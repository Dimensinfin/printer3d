package org.dimensinfin.printer3d.backend.production.request.rest.support;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestConverter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestEntityV2Converter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityV2ToRequestV2Converter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestToRequestEntityConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.backend.production.request.rest.v2.RequestServiceV2;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

@Profile({ "local", "acceptance", "test" })
@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
@Service
public class RequestControllerSupport {
	private static final RequestEntityToRequestEntityV2Converter requestV1ToV2Converter = new RequestEntityToRequestEntityV2Converter();
	private static final RequestEntityV2ToRequestV2Converter requestEntityV2ToRequestV2Converter = new RequestEntityV2ToRequestV2Converter();
	private final RequestsRepository requestsRepository;
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	public RequestControllerSupport( final @NotNull RequestsRepository requestsRepository,
	                                 final @NotNull RequestsRepositoryV2 requestsRepositoryV2 ) {
		this.requestsRepository = Objects.requireNonNull( requestsRepository );
		this.requestsRepositoryV2 = requestsRepositoryV2;
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping("/production/requests/repository")
	public ResponseEntity<List<RequestV2>> getRepositoryRequests() {
		return new ResponseEntity<>( this.getRepositoryRequestsService(), HttpStatus.OK );
	}

	private List<RequestV2> getRepositoryRequestsService() {
		return Stream.concat(
				this.requestsRepository.findAll()
						.stream()
						.map( requestV1ToV2Converter::convert ),
				this.requestsRepositoryV2.findAll().stream() ) // Get all the Requests and convert them to V2
				.map( requestEntityV2ToRequestV2Converter::convert )
				.collect( Collectors.toList() );
	}

	@GetMapping(path = "/production/requests/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CounterResponse> deleteAllRequests() {
		return new ResponseEntity<>( this.deleteAllRequestsService(), HttpStatus.OK );
	}

	@GetMapping(path = "/production/requests/v2/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CounterResponse> deleteAllRequestsV2() {
		return new ResponseEntity<>( this.deleteAllRequestsServiceV2(), HttpStatus.OK );
	}

	@PostMapping(path = "/production/requests",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Request> newRequestV1( final @RequestBody @Valid @NotNull Request request ) {
		return new ResponseEntity<>( this.newRequestService( request ), HttpStatus.CREATED );
	}

	protected CounterResponse deleteAllRequestsService() {
		try {
			final long recordCount = this.requestsRepository.count();
			this.requestsRepository.deleteAll();
			return new CounterResponse.Builder()
					.withRecords( (int) recordCount )
					.build();
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new DimensinfinRuntimeException( Printer3DErrorInfo.REQUEST_STORE_REPOSITORY_FAILURE( new SQLException( sqle ) ),
					"Detected exception while deleting all Requests from the repository." );
		}
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
			throw new DimensinfinRuntimeException( Printer3DErrorInfo.REQUEST_STORE_REPOSITORY_FAILURE( new SQLException( sqle ) ),
					"Detected exception while deleting all Requests from the repository." );
		}
	}

	protected Request newRequestService( final Request newRequest ) {
		LogWrapper.enter();
		try {
			// Search for the Part by id. If found reject the request because this should be a new creation.
			final Optional<RequestEntity> target = this.requestsRepository.findById( newRequest.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( RequestServiceV2.REQUEST_ALREADY_EXISTS( newRequest.getId() ) );
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
