package org.dimensinfin.printer3d.backend.accounting.request.rest.support;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;

@Profile({ "local", "dev", "acceptance", "test" })
@RestController
@Validated
@RequestMapping("/api/v1")
@Service
public class AccountingRequestControllerSupport {
	private final RequestsRepositoryV2 requestsRepositoryV2;

	// - C O N S T R U C T O R S
	public AccountingRequestControllerSupport( final RequestsRepositoryV2 requestsRepositoryV2 ) {this.requestsRepositoryV2 = requestsRepositoryV2;}

	@PutMapping("/accounting/requests/closedate/{closeDate}")
	public ResponseEntity<CounterResponse> setCloseDate( final @PathVariable @NotNull Instant closeDate ) {
		final AtomicInteger counter = new AtomicInteger( 0 );
		this.requestsRepositoryV2.findAll()
				.stream()
				.filter( RequestEntityV2::isClosed )
				.map( object -> {
					counter.incrementAndGet();
					return object;
				} )
				.forEach( request -> this.requestsRepositoryV2.save( request.setPaymentDate( closeDate ) ) );
		return new ResponseEntity<>( new CounterResponse.Builder().withRecords( counter.get() ).build(), HttpStatus.OK );
	}
}
