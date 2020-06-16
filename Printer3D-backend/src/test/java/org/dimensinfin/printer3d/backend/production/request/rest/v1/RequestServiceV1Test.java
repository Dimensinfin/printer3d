package org.dimensinfin.printer3d.backend.production.request.rest.v1;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestToRequestEntityConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestServiceV1Test {

	private RequestsRepository requestsRepository;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.requestsRepository = Mockito.mock( RequestsRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final RequestServiceV1 requestServiceV1 = new RequestServiceV1( this.requestsRepository, this.partRepository );
		Assertions.assertNotNull( requestServiceV1 );
	}

	@Test
	public void newRequest() {
		// Given
		final Request request = new Request.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		// When
		Mockito.when( this.requestsRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.requestsRepository.save( Mockito.any( RequestEntity.class ) ) ).thenReturn(
				new RequestToRequestEntityConverter().convert( request )
		);
		// Test
		final RequestServiceV1 requestServiceV1 = new RequestServiceV1( this.requestsRepository, this.partRepository );
		final Request obtained = requestServiceV1.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
	}
}
