package org.dimensinfin.printer3d.backend.production.request.rest.v2;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelEntity;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestState;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ACTIVE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.ModelConstants.TEST_MODEL_STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COLOR;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_COST;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MATERIAL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.PartConstants.TEST_PART_PRICE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_AMOUNT;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_CUSTOMER;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_IVA;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_TOTAL;

public class RequestServiceV2Test {
	private final List<RequestItem> contents = new ArrayList<>();
	private PartRepository partRepository;
	private RequestsRepositoryV2 requestsRepositoryV2;
	private ModelRepository modelRepository;

	@BeforeEach
	public void beforeEach() {
		final RequestItem requestItem = Mockito.mock( RequestItem.class );
		this.contents.clear();
		this.contents.add( requestItem );

		this.partRepository = Mockito.mock( PartRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
	}

	@Test
	public void closeRequest() {
		// Given
		final UUID partId = UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" );
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( new RequestItem.Builder().withItemId( partId ).withQuantity( 1 ).withType( RequestContentType.PART ).build() );
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( contents )
				.withRequestDate( Instant.now() )
				.withTotal( 15.0F )
				.build();
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( partId )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 5 )
				.withStockAvailable( 10 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<PartEntity> stockPartList = new ArrayList<>();
		stockPartList.add( partEntity );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2 ) );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		final CustomerRequestResponseV2 obtained = requestServiceV2.closeRequest( TEST_REQUEST_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( RequestState.CLOSED, obtained.getState() );
		//		Assertions.assertTrue( Instant.now().toString().startsWith( obtained.getClosedDate().substring( 0, 15 ) ) );
	}

	@Test
	public void closeRequestException() {
		// Given
		final UUID partId = UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" );
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( new RequestItem.Builder().withItemId( partId ).withQuantity( 1 ).withType( RequestContentType.PART ).build() );
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( contents )
				.withRequestDate( Instant.now() )
				.withTotal( 15.0F )
				.build();
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( partId )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 5 )
				.withStockAvailable( 0 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<PartEntity> stockPartList = new ArrayList<>();
		stockPartList.add( partEntity );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2 ) );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> requestServiceV2.closeRequest( TEST_REQUEST_ID ) );
	}

	@Test
	public void closeRequestWithModel() {
		// Given
		final UUID partId = UUID.fromString( "47461aa3-24f0-4cc5-a335-53e8bb61accc" );
		final List<RequestItem> contents = new ArrayList<>();
		contents.add( new RequestItem.Builder().withItemId( partId ).withQuantity( 1 ).withType( RequestContentType.MODEL ).build() );
		final RequestEntityV2 requestEntityV2 = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withState( RequestState.OPEN )
				.withContents( contents )
				.withRequestDate( Instant.now() )
				.withTotal( 15.0F )
				.build();
		final PartEntity partEntity = new PartEntity.Builder()
				.withId( partId )
				.withLabel( TEST_PART_LABEL )
				.withDescription( TEST_PART_DESCRIPTION )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( TEST_PART_BUILD_TIME )
				.withCost( TEST_PART_COST )
				.withPrice( TEST_PART_PRICE )
				.withStockLevel( 5 )
				.withStockAvailable( 10 )
				.withImagePath( TEST_PART_IMAGE_PATH )
				.withModelPath( TEST_PART_MODEL_PATH )
				.withActive( false )
				.build();
		final List<PartEntity> stockPartList = new ArrayList<>();
		stockPartList.add( partEntity );
		final List<UUID> partIdList = new ArrayList<>();
		partIdList.add( partId );
		final ModelEntity modelEntity = new ModelEntity.Builder()
				.withId( TEST_MODEL_ID )
				.withLabel( TEST_MODEL_LABEL )
				.withPrice( TEST_MODEL_PRICE )
				.withStockLevel( TEST_MODEL_STOCK_LEVEL )
				.withImagePath( TEST_MODEL_IMAGE_PATH )
				.withActive( TEST_MODEL_ACTIVE )
				.withPartIdList( partIdList )
				.build();
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntityV2 ) );
		Mockito.when( this.partRepository.findAll() ).thenReturn( stockPartList );
		Mockito.when( this.modelRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( modelEntity ) );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		final CustomerRequestResponseV2 obtained = requestServiceV2.closeRequest( TEST_REQUEST_ID );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( RequestState.CLOSED, obtained.getState() );
		//		Assertions.assertTrue( Instant.now().toString().startsWith( obtained.getClosedDate().substring( 0, 15 ) ) );
	}

	@Test
	public void closedRequestFailureSelectRequestEntity() {
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		Assertions.assertThrows( DimensinfinRuntimeException.class, () -> requestServiceV2.closeRequest( TEST_REQUEST_ID ) );
	}

	@Test
	public void collectItemsFromStock_UnderStocked() {
	}

	@Test
	public void constructorContract() {
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		Assertions.assertNotNull( requestServiceV2 );
	}

	@Test
	public void getOpenRequests() {
		// Given
		final RequestItem item = new RequestItem.Builder()
				.withItemId( UUID.fromString( "bf3cdc04-c1f3-46db-940c-9a0d551a5496" ) )
				.withQuantity( 1 )
				.withType( RequestContentType.PART )
				.build();
		final List<RequestItem> contentsLocal = new ArrayList<>();
		contentsLocal.add( item );
		final RequestEntityV2 requestEntityV2Open = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( contentsLocal )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		final RequestEntityV2 requestEntityV2Closed = new RequestEntityV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withCustomerData( TEST_REQUEST_CUSTOMER )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withContents( contentsLocal )
				.withPaid( false )
				.withTotal( TEST_REQUEST_TOTAL )
				.build();
		requestEntityV2Closed.signalCompleted();
		final List<RequestEntityV2> requestList = new ArrayList<>();
		requestList.add( requestEntityV2Open );
		requestList.add( requestEntityV2Closed );
		final PartEntity part = new PartEntity.Builder()
				.withId( UUID.fromString( "bf3cdc04-c1f3-46db-940c-9a0d551a5496" ) )
				.withLabel( TEST_PART_LABEL )
				.withMaterial( TEST_PART_MATERIAL )
				.withColor( TEST_PART_COLOR )
				.withBuildTime( 10 )
				.withCost( 1.0F )
				.withPrice( 1.0F )
				.withStockLevel( 1 )
				.build();
		final List<PartEntity> partList = new ArrayList<>();
		partList.add( part );
		// When
		Mockito.when( this.requestsRepositoryV2.findAll() ).thenReturn( requestList );
		Mockito.when( this.partRepository.findAll() ).thenReturn( partList );
		// Tests
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		final List<CustomerRequestResponseV2> obtained = requestServiceV2.getOpenRequests();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.size() );
	}

	@Test
	public void newRequestAlreadyExist() {
		// Given
		final RequestEntityV2 requestEntity = Mockito.mock( RequestEntityV2.class );
		final CustomerRequestRequestV2 request = Mockito.mock( CustomerRequestRequestV2.class );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( requestEntity ) );
		Mockito.when( request.getId() ).thenReturn( UUID.randomUUID() );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		Assertions.assertThrows( RepositoryConflictException.class, () -> requestServiceV2.newRequest( request ) );
	}

	@Test
	public void newRequest_nodate() {
		// Given
		final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withContents( this.contents )
				.withTotalAmount( TEST_REQUEST_TOTAL )
				.withPaid( false )
				.build()
				.setCustomer( TEST_REQUEST_CUSTOMER );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.requestsRepositoryV2.save( Mockito.any( RequestEntityV2.class ) ) ).thenAnswer( i -> i.getArguments()[0] );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		final CustomerRequestResponseV2 obtained = requestServiceV2.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_REQUEST_ID, obtained.getId() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_CUSTOMER, obtained.getCustomer() );
		Assertions.assertNotNull( obtained.getRequestDate() );
		Assertions.assertNull( obtained.getDeliveredDate() );
		Assertions.assertNull( obtained.getPaymentDate() );
		Assertions.assertEquals( RequestState.OPEN, obtained.getState() );
		Assertions.assertFalse( obtained.isPaid() );
		Assertions.assertNotNull( obtained.getContents() );
		Assertions.assertEquals( 1, obtained.getContents().size() );
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, obtained.getAmount() );
		Assertions.assertEquals( TEST_REQUEST_IVA, obtained.getIva() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, obtained.getTotal() );
	}

	@Test
	public void newRequest_notpaid() {
		// Given
		final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withContents( this.contents )
				.withTotalAmount( TEST_REQUEST_TOTAL )
				.withPaid( false )
				.build()
				.setCustomer( TEST_REQUEST_CUSTOMER );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.requestsRepositoryV2.save( Mockito.any( RequestEntityV2.class ) ) ).thenAnswer( new Answer() {
			@Override
			public Object answer( final InvocationOnMock invocation ) {
				return invocation.getArguments()[0];
			}
		} );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		final CustomerRequestResponseV2 obtained = requestServiceV2.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_REQUEST_ID, obtained.getId() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_CUSTOMER, obtained.getCustomer() );
		Assertions.assertEquals( TEST_REQUEST_DATE, obtained.getRequestDate() );
		Assertions.assertEquals( null, obtained.getDeliveredDate() );
		Assertions.assertEquals( null, obtained.getPaymentDate() );
		Assertions.assertEquals( RequestState.OPEN, obtained.getState() );
		Assertions.assertFalse( obtained.isPaid() );
		Assertions.assertNotNull( obtained.getContents() );
		Assertions.assertEquals( 1, obtained.getContents().size() );
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, obtained.getAmount() );
		Assertions.assertEquals( TEST_REQUEST_IVA, obtained.getIva() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, obtained.getTotal() );
	}

	@Test
	public void newRequest_paid() {
		// Given
		final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withContents( this.contents )
				.withTotalAmount( TEST_REQUEST_TOTAL )
				.withPaid( true )
				.build()
				.setCustomer( TEST_REQUEST_CUSTOMER );
		// When
		Mockito.when( this.requestsRepositoryV2.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.empty() );
		Mockito.when( this.requestsRepositoryV2.save( Mockito.any( RequestEntityV2.class ) ) ).thenAnswer( new Answer() {
			@Override
			public Object answer( final InvocationOnMock invocation ) {
				return invocation.getArguments()[0];
			}
		} );
		// Test
		final RequestServiceV2 requestServiceV2 = new RequestServiceV2(
				this.partRepository,
				this.modelRepository,
				this.requestsRepositoryV2 );
		final CustomerRequestResponseV2 obtained = requestServiceV2.newRequest( request );
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( TEST_REQUEST_ID, obtained.getId() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_CUSTOMER, obtained.getCustomer() );
		Assertions.assertEquals( TEST_REQUEST_DATE, obtained.getRequestDate() );
		Assertions.assertNull( obtained.getDeliveredDate() );
		Assertions.assertNotNull( obtained.getPaymentDate() );
		Assertions.assertEquals( RequestState.OPEN, obtained.getState() );
		Assertions.assertTrue( obtained.isPaid() );
		Assertions.assertNotNull( obtained.getContents() );
		Assertions.assertEquals( 1, obtained.getContents().size() );
		Assertions.assertEquals( TEST_REQUEST_AMOUNT, obtained.getAmount() );
		Assertions.assertEquals( TEST_REQUEST_IVA, obtained.getIva() );
		Assertions.assertEquals( TEST_REQUEST_TOTAL, obtained.getTotal() );
	}
}
