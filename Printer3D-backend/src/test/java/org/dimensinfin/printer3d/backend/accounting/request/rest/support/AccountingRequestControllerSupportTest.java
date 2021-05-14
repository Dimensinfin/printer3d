package org.dimensinfin.printer3d.backend.accounting.request.rest.support;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_TOTAL;

public class AccountingRequestControllerSupportTest {
	private final List<RequestItem> contents = new ArrayList<>();
	private RequestsRepositoryV2 requestsRepositoryV2;

	@BeforeEach
	public void beforeEach() {
		final RequestItem requestItem = Mockito.mock( RequestItem.class );
		this.contents.clear();
		this.contents.add( requestItem );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
	}

	@Test
	public void constructorContract() {
		final AccountingRequestControllerSupport accountingRequestControllerSupport = new AccountingRequestControllerSupport(
				this.requestsRepositoryV2
		);
		Assertions.assertNotNull( accountingRequestControllerSupport );
	}

	@Test
	public void setCloseDate() {
		// Given
		final Instant closeDate = Instant.now();
		final RequestEntityV2 requestEntityV201 = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( this.contents )
				.withRequestDate( Instant.now() )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final RequestEntityV2 requestEntityV202 = new RequestEntityV2.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( this.contents )
				.withRequestDate( Instant.now() )
				.withTotal( 15.0F )
				.build();
		final List<RequestEntityV2> requestEntityList = new ArrayList<>();
		requestEntityList.add( requestEntityV201 );
		requestEntityList.add( requestEntityV202.close() );
		// When
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestEntityList );
		// Test
		final AccountingRequestControllerSupport accountingRequestControllerSupport = new AccountingRequestControllerSupport(
				this.requestsRepositoryV2
		);
		final ResponseEntity<CounterResponse> obtained = accountingRequestControllerSupport.setCloseDate( closeDate );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().getRecords() );
	}
}
