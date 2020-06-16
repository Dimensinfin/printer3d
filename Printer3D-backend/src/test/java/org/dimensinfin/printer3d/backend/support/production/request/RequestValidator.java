package org.dimensinfin.printer3d.backend.support.production.request;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;
import org.dimensinfin.printer3d.client.production.rest.dto.Request;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PART_REQUEST_LIST;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STATE;

public class RequestValidator implements Validator<Request> {

	@Override
	public boolean validate( final Map<String, String> rowData, final Request record ) {
		if (null != rowData.get( ID )) Assertions.assertEquals( rowData.get( ID ), record.getId().toString() );
		if (null != rowData.get( LABEL )) Assertions.assertEquals( rowData.get( LABEL ), record.getLabel() );
		if (null != rowData.get( REQUEST_DATE )) Assertions.assertEquals(
				OffsetDateTime.parse( rowData.get( REQUEST_DATE ), DateTimeFormatter.ISO_OFFSET_DATE_TIME ),
				OffsetDateTime.parse( record.getRequestDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME )
		);
		if (null != rowData.get( STATE )) Assertions.assertEquals( rowData.get( STATE ), record.getState().name() );
		if (null != rowData.get( PART_REQUEST_LIST )) Assertions.assertTrue( this.validatePartRequestList(
				rowData.get( PART_REQUEST_LIST ),
				record.getPartList()
		) );
		return true;
	}

	private boolean validatePartRequestList( final String dataList, final List<PartRequest> partRequestList ) {
		final String[] data = dataList.split( "," );
		for (int i = 0; i < data.length; i++) {
			final String[] dataFields = data[i].split( "/" );
			Assertions.assertEquals( dataFields[0], partRequestList.get( i ).getPartId().toString() );
			Assertions.assertEquals( dataFields[1], partRequestList.get( i ).getQuantity().toString() );
		}
		return true;
	}
}
