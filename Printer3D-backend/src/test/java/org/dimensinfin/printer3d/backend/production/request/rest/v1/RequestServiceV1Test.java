package org.dimensinfin.printer3d.backend.production.request.rest.v1;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.domain.StockManager;
import org.dimensinfin.printer3d.backend.production.request.converter.RequestToRequestEntityConverter;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepository;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestList;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestServiceV1Test {

	private RequestsRepository requestsRepository;
	private PartRepository partRepository;
	private StockManager stockManager;

	@BeforeEach
	public void beforeEach() {
		this.requestsRepository = Mockito.mock( RequestsRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
		this.stockManager = Mockito.mock( StockManager.class );
	}

	@Test
	public void closeRequest() {
		// Given
		final PartEntity part = Mockito.mock( PartEntity.class );
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( new ArrayList<>() )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final UUID requestId = UUID.fromString( "d00af11c-8a78-45f6-bcf0-a4649596ae7c" );
		// When
		Mockito.when( this.requestsRepository.findById( requestId ) ).thenReturn( Optional.of( requestEntity ) );
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		Mockito.when( this.requestsRepository.save( Mockito.any( RequestEntity.class ) ) ).thenReturn( requestEntity.close() );
		// Test
		final RequestServiceV1 requestServiceV1 = new RequestServiceV1( this.requestsRepository, this.partRepository, this.stockManager );
		final Request obtained = requestServiceV1.closeRequest( requestId );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( RequestState.CLOSE, obtained.getState() );
	}

	@Test
	public void constructorContract() {
		final RequestServiceV1 requestServiceV1 = new RequestServiceV1( this.requestsRepository, this.partRepository, this.stockManager );
		Assertions.assertNotNull( requestServiceV1 );
	}

	@Test
	public void getOpenRequests() {
		// Given
		final List<PartRequest> requestPartList = new ArrayList<>();
		requestPartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withQuantity( 2 )
				.build() );
		requestPartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withQuantity( 3 )
				.build() );
		requestPartList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) )
				.withQuantity( 3 )
				.build() );
		final List<PartRequest> requestPartList2 = new ArrayList<>();
		requestPartList2.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) )
				.withQuantity( 2 )
				.build() );
		requestPartList2.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) )
				.withQuantity( 3 )
				.build() );
		final RequestEntity requestEntity1 = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( requestPartList )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final RequestEntity requestEntity2 = new RequestEntity.Builder()
				.withId( UUID.randomUUID() )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withPartList( requestPartList2 )
				.withRequestDate( OffsetDateTime.now() )
				.build();
		final List<RequestEntity> requestList = new ArrayList<>();
		requestList.add( requestEntity1 );
		requestList.add( requestEntity2 );
		// When
		Mockito.when( this.requestsRepository.findAll() ).thenReturn( requestList );
		Mockito.when( this.stockManager.getStock( UUID.fromString( "467ea75a-108d-42f6-a3c6-70484704c49b" ) ) )
				.thenReturn( 2 );
		Mockito.when( this.stockManager.getStock( UUID.fromString( "b031f097-6f69-4f72-8564-20cb4597d30a" ) ) )
				.thenReturn( 0 );
		Mockito.when( this.stockManager.getStock( UUID.fromString( "266ef180-fd8c-46ae-a5a7-83056cf0f656" ) ) )
				.thenReturn( -1 );
		// Test
		final RequestServiceV1 requestServiceV1 = new RequestServiceV1( this.requestsRepository, this.partRepository, this.stockManager );
		final RequestList obtained = requestServiceV1.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.getCount() );
		Assertions.assertEquals( RequestState.OPEN, obtained.getRequests().get( 0 ).getState() );
		Assertions.assertEquals( RequestState.COMPLETED, obtained.getRequests().get( 1 ).getState() );
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
		final RequestServiceV1 requestServiceV1 = new RequestServiceV1( this.requestsRepository, this.partRepository, this.stockManager );
		final Request obtained = requestServiceV1.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
	}

	@Test
	public void newRequestException() {
		// Given
		final RequestEntity requestEntity = Mockito.mock( RequestEntity.class );
		final Request request = Mockito.mock( Request.class );
		// When
		Mockito.when( this.requestsRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntity ) );
		Mockito.when( request.getId() ).thenReturn( UUID.randomUUID() );
		// Exceptions
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> {
			final RequestServiceV1 requestServiceV1 = new RequestServiceV1( this.requestsRepository, this.partRepository, this.stockManager );
			requestServiceV1.newRequest( request );
		} );
	}
}
