package org.dimensinfin.printer3d.backend.support.production.request;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.REQUEST_DATE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STATE;

public class RequestV2Validator implements Validator<RequestV2> {

	@Override
	public boolean validate( final Map<String, String> rowData, final RequestV2 record ) {
		if (null != rowData.get( ID )) Assertions.assertEquals( rowData.get( ID ), record.getId().toString() );
		if (null != rowData.get( LABEL )) Assertions.assertEquals( rowData.get( LABEL ), record.getLabel() );
		if (null != rowData.get( REQUEST_DATE )) Assertions.assertEquals(
				OffsetDateTime.parse( rowData.get( REQUEST_DATE ), DateTimeFormatter.ISO_OFFSET_DATE_TIME ),
				OffsetDateTime.parse( record.getRequestDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME )
		);
		if (null != rowData.get( STATE )) Assertions.assertEquals( rowData.get( STATE ), record.getState().name() );
		return true;
	}
}
