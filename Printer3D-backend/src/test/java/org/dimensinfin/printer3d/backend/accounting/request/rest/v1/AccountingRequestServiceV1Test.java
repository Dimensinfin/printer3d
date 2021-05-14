package org.dimensinfin.printer3d.backend.accounting.request.rest.v1;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.threeten.extra.YearWeek;

import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class AccountingRequestServiceV1Test {
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
		final AccountingRequestServiceV1 accountingRequestServiceV1 = new AccountingRequestServiceV1( this.requestsRepositoryV2 );
		Assertions.assertNotNull( accountingRequestServiceV1 );
	}

	@Test
	public void getRequestsAmountPerWeek() {
		// Given
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( this.contents )
				.withTotal( 100.0F )
				.build();
		final List<RequestEntityV2> requestList = new ArrayList<>();
		requestList.add( requestEntityV2.close() );
		// When
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestList );
		// Test
		final AccountingRequestServiceV1 accountingRequestServiceV1 = new AccountingRequestServiceV1( this.requestsRepositoryV2 );
		final List<WeekAmount> obtained = accountingRequestServiceV1.getRequestsAmountPerWeek( 2 );
		final YearWeek today = YearWeek.from( Instant.now().atZone( ZoneId.systemDefault() ) );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 1, obtained.size() );
		Assertions.assertEquals( LocalDate.now().getYear(), obtained.get( 0 ).getYear() );
		Assertions.assertEquals( 100.0F, obtained.get( 0 ).getAmount() );
	}
}
