package org.dimensinfin.printer3d.backend.production.request.converter;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestEntityToRequestConverterTest {

	@Test
	public void convert() {
		// Given
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withPartList( new ArrayList<>() )
				.build();
		final RequestEntityToRequestConverter requestEntityToRequestConverter = new RequestEntityToRequestConverter();
		// Test
		final Request obtained = requestEntityToRequestConverter.convert( requestEntity );
		// Assertions
		Assertions.assertEquals( TEST_REQUEST_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_DATE.toString(),
				obtained.getRequestDate() );
		Assertions.assertEquals( TEST_REQUEST_STATE, obtained.getState() );
		Assertions.assertNotNull( obtained.getPartList() );
		Assertions.assertEquals( 0, obtained.getPartList().size() );
	}
}
