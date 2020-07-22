package org.dimensinfin.printer3d.backend.production.request.rest.support;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestConverter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityToRequestEntityV2Converter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestEntityV2ToRequestV2Converter;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestToRequestEntityConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.backend.production.request.rest.RequestServiceCore;
import org.dimensinfin.printer3d.backend.production.request.rest.v2.RequestServiceV2;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

//@Profile({ "local", "acceptance", "test" })
@RestController
//@CrossOrigin
@Validated
@RequestMapping("/api/v1")
@Service
public class RequestControllerSupport  extends RequestServiceCore {
	private static final RequestEntityToRequestEntityV2Converter requestV1ToV2Converter = new RequestEntityToRequestEntityV2Converter();
	private static final RequestEntityV2ToRequestV2Converter requestEntityV2ToRequestV2Converter = new RequestEntityV2ToRequestV2Converter();
//	private final StockManager stockManager;
//	private final ModelRepository modelRepository;
	private final RequestsRepository requestsRepositoryV1;
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	public RequestControllerSupport( final @NotNull PartRepository partRepository,
	                                 final @NotNull ModelRepository modelRepository,
	                                 final @NotNull RequestsRepository requestsRepositoryV1,
	                                 final @NotNull RequestsRepositoryV2 requestsRepositoryV2 ) {
		super( partRepository , modelRepository);
//		this.modelRepository = modelRepository;
		this.requestsRepositoryV1 = Objects.requireNonNull( requestsRepositoryV1 );
		this.requestsRepositoryV2 = requestsRepositoryV2;
//		this.stockManager = new StockManager( partRepository );
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping("/production/requests/repository")
	public ResponseEntity<List<RequestV2>> getRepositoryRequests() {
		return new ResponseEntity<>( this.getRepositoryRequestsService(), HttpStatus.OK );
	}

	private List<RequestV2> getRepositoryRequestsService() {
		return Stream.concat(
				this.requestsRepositoryV1.findAll()
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

	@PutMapping("/production/requests/transform")
	public ResponseEntity<List<CounterResponse>> transformRequestsV1() {
		final int moveCount = (int) this.moveV1RequestsToV2();
		final int closeCount = (int) this.updateClosed();
		final int recalculateCount = (int) this.recalculateRequestV2Amounts();
		final List<CounterResponse> countList = new ArrayList<>();
		countList.add( new CounterResponse.Builder().withRecords( moveCount ).build() );
		countList.add( new CounterResponse.Builder().withRecords( closeCount ).build() );
		countList.add( new CounterResponse.Builder().withRecords( recalculateCount ).build() );
		return new ResponseEntity<>( countList, HttpStatus.OK );
	}

	protected CounterResponse deleteAllRequestsService() {
		try {
			final long recordCount = this.requestsRepositoryV1.count();
			this.requestsRepositoryV1.deleteAll();
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
			final Optional<RequestEntity> target = this.requestsRepositoryV1.findById( newRequest.getId() );
			if (target.isPresent())
				throw new DimensinfinRuntimeException( RequestServiceV2.REQUEST_ALREADY_EXISTS( newRequest.getId() ) );
			return new RequestEntityToRequestConverter().convert(
					this.requestsRepositoryV1.save(
							new RequestToRequestEntityConverter().convert( newRequest )
					)
			);
		} finally {
			LogWrapper.exit();
		}
	}
	private long moveV1RequestsToV2() {
		LogWrapper.enter();
		try {
			return this.requestsRepositoryV1.findAll()
					.stream()
					.map( repositoryEntity -> {
						LogWrapper.info(
								MessageFormat.format(
										"Moving RequestV1: [{0}]-{1}",
										repositoryEntity.getId(),
										repositoryEntity.getLabel() )
						);
						this.requestsRepositoryV2.save( requestV1ToV2Converter.convert( repositoryEntity ) );
						this.requestsRepositoryV1.delete( repositoryEntity );
						return repositoryEntity;
					} ).collect( Collectors.toList() ).size();
		} finally {
			LogWrapper.exit();
		}
	}

	private long recalculateRequestV2Amounts() {
		LogWrapper.enter();
		try {
			return this.requestsRepositoryV2.findAll()
					.stream()
					.filter( RequestEntityV2::isClosed )
					.map( requestEntityV2 -> {
						final float amount = this.calculateRequestAmount( requestEntityV2 );
						this.requestsRepositoryV2.save( requestEntityV2.setAmount( amount ) );
						LogWrapper.info(
								MessageFormat.format( "Recalculating amount for RequestV2: [{0}]-{1}",
										requestEntityV2.getId(),
										requestEntityV2.getLabel() )
						);
						LogWrapper.info(
								MessageFormat.format( "New Amount: {0}", amount )
						);
						return requestEntityV2;
					} )
					.count();
		} finally {
			LogWrapper.exit();
		}
	}

	private long updateClosed() {
		LogWrapper.enter();
		try {
			return this.requestsRepositoryV2.findAll()
					.stream()
					.filter( RequestEntityV2::isClosed )
					.map( requestEntityV2 -> {
						LogWrapper.info( MessageFormat.format(
								"Current close date: {0}",
								(null != requestEntityV2.getClosedDate()) ? requestEntityV2.getClosedDate().toString() : "null"
						) );
						final RequestEntityV2 updatedRequest = this.requestsRepositoryV2.save( requestEntityV2.close() );
						LogWrapper.info( MessageFormat.format(
								"Updated close date: {0}",
								(null != requestEntityV2.getClosedDate()) ? updatedRequest.getClosedDate().toString() : "null" ) );
						return updatedRequest;
					} )
					.count();
		} finally {
			LogWrapper.exit();
		}
	}
}
