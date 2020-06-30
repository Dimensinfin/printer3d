package org.dimensinfin.printer3d.backend.support.production.request;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ITEM_ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.MISSING;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.QUANTITY;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.TYPE;

public class RequestContentValidator implements Validator<RequestItem> {
	@Override
	public boolean validate( final Map<String, String> rowData, final RequestItem record ) {
		if (null != rowData.get( ITEM_ID )) Assertions.assertEquals( rowData.get( ITEM_ID ), record.getItemId().toString() );
		if (null != rowData.get( QUANTITY )) Assertions.assertEquals( Integer.parseInt( rowData.get( QUANTITY ) ), record.getQuantity() );
		if (null != rowData.get( MISSING )) Assertions.assertEquals( Integer.parseInt( rowData.get( MISSING ) ), record.getMissing() );
		if (null != rowData.get( TYPE ))
			Assertions.assertEquals( RequestContentType.valueOf( rowData.get( TYPE ) ), record.getType() );
		return true;
	}
}
