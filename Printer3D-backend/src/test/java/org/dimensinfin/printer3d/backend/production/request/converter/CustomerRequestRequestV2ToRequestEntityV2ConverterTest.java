package org.dimensinfin.printer3d.backend.production.request.converter;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestRequestV2;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE_STRING;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class CustomerRequestRequestV2ToRequestEntityV2ConverterTest {

	@Test
	public void convert() {
		// Given
		final CustomerRequestRequestV2 request = new CustomerRequestRequestV2.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE_STRING )
				.withState( TEST_REQUEST_STATE )
				.withContents( new ArrayList<>() )
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
