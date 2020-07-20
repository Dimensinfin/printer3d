package org.dimensinfin.printer3d.backend.production.request.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntity;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestEntityV2;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;

import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_ID;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_LABEL;
import static org.dimensinfin.printer3d.backend.support.TestDataConstants.RequestConstants.TEST_REQUEST_STATE;

public class RequestEntityToRequestEntityV2ConverterTest {

	@Test
	public void convert() {
		// Given
		final List<PartRequest> partRequestList = new ArrayList<>();
		partRequestList.add( new PartRequest.Builder()
				.withPartId( UUID.fromString( "f0aed46a-eb94-4513-a76a-e479f138b4ac" ) )
				.withQuantity( 4 ).build() );
		final RequestEntity requestEntity = new RequestEntity.Builder()
				.withId( TEST_REQUEST_ID )
				.withLabel( TEST_REQUEST_LABEL )
				.withRequestDate( TEST_REQUEST_DATE )
				.withState( TEST_REQUEST_STATE )
				.withPartList( partRequestList )
				.build();
		final RequestEntityToRequestEntityV2Converter converter = new RequestEntityToRequestEntityV2Converter();
		// Test
		final RequestEntityV2 obtained = converter.convert( requestEntity );
		// Assertions
		Assertions.assertEquals( TEST_REQUEST_ID.toString(), obtained.getId().toString() );
		Assertions.assertEquals( TEST_REQUEST_LABEL, obtained.getLabel() );
		Assertions.assertEquals( TEST_REQUEST_DATE.toString(),
				obtained.getRequestDate().toString() );
		Assertions.assertEquals( TEST_REQUEST_STATE, obtained.getState() );
		Assertions.assertNotNull( obtained.getContents() );
		Assertions.assertEquals( 1, obtained.getContents().size() );
	}
}
