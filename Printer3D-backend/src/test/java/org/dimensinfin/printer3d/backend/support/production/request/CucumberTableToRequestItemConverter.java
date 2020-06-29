package org.dimensinfin.printer3d.backend.support.production.request;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestContentType;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestItem;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ITEM_ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.QUANTITY;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.TYPE;

public class CucumberTableToRequestItemConverter extends CucumberTableConverter<RequestItem> {

	@Override
	public RequestItem convert( final Map<String, String> cucumberRow ) {
		return new RequestItem.Builder()
				.withItemId( UUID.fromString( cucumberRow.get( ITEM_ID ) ) )
				.withType( RequestContentType.valueOf( cucumberRow.get(TYPE) ) )
				.withQuantity( Integer.parseInt( cucumberRow.get( QUANTITY ) ) )
				.build();
	}
}
