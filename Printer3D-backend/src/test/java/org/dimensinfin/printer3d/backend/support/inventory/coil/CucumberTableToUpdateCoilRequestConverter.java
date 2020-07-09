package org.dimensinfin.printer3d.backend.support.inventory.coil;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.WEIGHT;

public class CucumberTableToUpdateCoilRequestConverter extends CucumberTableConverter<UpdateCoilRequest> {
	@Override
	public UpdateCoilRequest convert( final Map<String, String> cucumberRow ) {
		return new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( cucumberRow.get( ID ) ) )
				.withWeight( Integer.parseInt( cucumberRow.get( WEIGHT ) ) )
				.build();
	}
}
