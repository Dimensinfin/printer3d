package org.dimensinfin.printer3d.backend.support.production.request;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.production.rest.dto.PartRequest;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PART_ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.QUANTITY;

public class CucumberTableToPartRequestConverter extends CucumberTableConverter<PartRequest> {

	@Override
	public PartRequest convert( final Map<String, String> cucumberRow ) {
		return new PartRequest.Builder()
				.withPartId( UUID.fromString( cucumberRow.get( PART_ID ) ) )
				.withQuantity( Integer.parseInt( cucumberRow.get( QUANTITY ) ) )
				.build();
	}
}
