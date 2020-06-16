package org.dimensinfin.printer3d.client.production.rest.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RequestListTest {
	@Test
	public void buildContract() {
		final RequestList requestList = new RequestList.Builder().build();
		Assertions.assertNotNull( requestList );
	}

	@Test
	public void getterContract() {
		// Given
		final Request request = Mockito.mock( Request.class );
		final RequestList requestList = new RequestList.Builder().build();
		requestList.addRequest( request );
		requestList.addRequest( request );
		// Assertions
		Assertions.assertEquals( 2, requestList.getCount() );
		Assertions.assertEquals( 2, requestList.getRequests().size() );
	}
}
