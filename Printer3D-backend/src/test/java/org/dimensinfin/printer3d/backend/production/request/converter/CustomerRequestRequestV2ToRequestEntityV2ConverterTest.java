package org.dimensinfin.printer3d.backend.production.request.converter;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_TOTAL;

public class CustomerRequestRequestV2ToRequestEntityV2ConverterTest {
	private final List<RequestItem> contents = new ArrayList<>();

	//	@BeforeEach
	public void beforeEach() {
		final RequestItem requestItem = Mockito.mock( RequestItem.class );
		this.contents.clear();
		this.contents.add( requestItem );
	}

	//	@Test
	public void convert() {
		// Given
		final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withContents( this.contents )
				.withTotalAmount( TEST_REQUEST_TOTAL )
				.withPaid( false )
				.build();
		final CustomerRequestRequestV2ToRequestEntityV2Converter requestToRequestEntityConverter = new CustomerRequestRequestV2ToRequestEntityV2Converter();
		// Test
		final RequestEntityV2 obtained = requestToRequestEntityConverter.convert( request );
		// Assertions
		Assertions.assertEquals( TEST_REQUEST_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_DATE.toString(),
				obtained.getRequestDate().toString() );
		Assertions.assertEquals( TEST_REQUEST_STATE, obtained.getState() );
		Assertions.assertNotNull( obtained.getContents() );
		Assertions.assertEquals( 0, obtained.getContents().size() );
	}
}
