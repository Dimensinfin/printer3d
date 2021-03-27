package org.dimensinfin.printer3d.backend.accounting.request.rest.v1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

public class AccountingRequestControllerV1Test {

	private AccountingRequestServiceV1 accountingRequestServiceV1;

	@BeforeEach
	public void beforeEach() {
		this.accountingRequestServiceV1 = Mockito.mock( AccountingRequestServiceV1.class );
	}

	@Test
	public void constructorContract() {
		final AccountingRequestControllerV1 controllerV1 = new AccountingRequestControllerV1( this.accountingRequestServiceV1 );
		Assertions.assertNotNull( controllerV1 );
	}

	@Test
	public void getRequestsAmountPerWeekComplete() {
		// Given
		final List<WeekAmount> weekList = new ArrayList<>();
		weekList.add( new WeekAmount.Builder().withYear( 2020 ).withWeek( 29 ).withAmount( 12.0F ).build() );
		// When
		Mockito.when( this.accountingRequestServiceV1.getRequestsAmountPerWeek( Mockito.anyInt() ) ).thenReturn( weekList );
		// Test
		final AccountingRequestControllerV1 controllerV1 = new AccountingRequestControllerV1( this.accountingRequestServiceV1 );
		final ResponseEntity<List<WeekAmount>> obtained = controllerV1.getRequestsAmountPerWeek( 1 );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( 1, obtained.getBody().size() );
	}
}
