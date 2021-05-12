package org.dimensinfin.printer3d.backend.accounting.request.rest.v1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class AccountingRequestControllerV1Test {

	private AccountingRequestServiceV1 accountingRequestServiceV1;
	private RequestsRepositoryV2 requestsRepositoryV2;

	@BeforeEach
	public void beforeEach() {
		this.accountingRequestServiceV1 = Mockito.mock( AccountingRequestServiceV1.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
	}

	@Test
	public void constructorContract() {
		final AccountingRequestControllerV1 controllerV1 = new AccountingRequestControllerV1( this.accountingRequestServiceV1,
				this.requestsRepositoryV2 );
		Assertions.assertNotNull( controllerV1 );
	}

	@Test
	public void downloadClosedRequestsData() throws IOException {
		// Given
		final HttpServletResponse response = Mockito.mock( HttpServletResponse.class );
		final List<RequestEntityV2> requests = new ArrayList();
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( new ArrayList<>() )
				.withTotal( TEST_REQUEST_AMOUNT )
				.build();
		requests.add( requestEntityV2 );
		// When
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requests );
		Mockito.when( response.getWriter() ).thenReturn( new PrintWriter( "extraction.csv" ) );
		// Test
		final AccountingRequestControllerV1 controllerV1 = new AccountingRequestControllerV1(
				this.accountingRequestServiceV1,
				this.requestsRepositoryV2 );
		controllerV1.downloadClosedRequestsData( response );
	}

	@Test
	public void downloadClosedRequestsData_exception() throws IOException {
		// Given
		final HttpServletResponse response = Mockito.mock( HttpServletResponse.class );
		final List<RequestEntityV2> requests = new ArrayList();
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( new ArrayList<>() )
				.withTotal( TEST_REQUEST_AMOUNT )
				.build();
		requests.add( requestEntityV2 );
		// When
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requests );
		Mockito.when( response.getWriter() ).thenThrow( new IOException( "IO exception on the CSV writer" ) );
		// Test
		final AccountingRequestControllerV1 controllerV1 = new AccountingRequestControllerV1(
				this.accountingRequestServiceV1,
				this.requestsRepositoryV2 );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			controllerV1.downloadClosedRequestsData( response );
		} );
	}

	@Test
	public void getRequestsAmountPerWeekComplete() {
		// Given
		final List<WeekAmount> weekList = new ArrayList<>();
		weekList.add( new WeekAmount.Builder().withYear( 2020 ).withWeek( 29 ).withAmount( 12.0F ).build() );
		// When
		Mockito.when( this.accountingRequestServiceV1.getRequestsAmountPerWeek( Mockito.anyInt() ) ).thenReturn( weekList );
		// Test
		final AccountingRequestControllerV1 controllerV1 = new AccountingRequestControllerV1( this.accountingRequestServiceV1,
				this.requestsRepositoryV2 );
		final ResponseEntity<List<WeekAmount>> obtained = controllerV1.getRequestsAmountPerWeek( 1 );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().size() );
	}
}
