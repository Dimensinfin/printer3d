package org.dimensinfin.printer3d.backend.support.inventory.coil.converter;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ACTIVE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.WEIGHT;

public class CucumberTableToUpdateCoilRequestConverter extends CucumberTableConverter<UpdateCoilRequest> {
	@Override
	public UpdateCoilRequest convert( final Map<String, String> cucumberRow ) {
		UpdateCoilRequest.Builder builder = new UpdateCoilRequest.Builder()
				.withId( UUID.fromString( cucumberRow.get( ID ) ) );
		if (null != cucumberRow.get( WEIGHT )) builder = builder.withWeight( Integer.parseInt( cucumberRow.get( WEIGHT ) ) );
		if (null != cucumberRow.get( TRADE_MARK )) builder = builder.withTradeMark( cucumberRow.get( TRADE_MARK ) );
		if (null != cucumberRow.get( LABEL )) builder = builder.withLabel( cucumberRow.get( LABEL ) );
		if (null != cucumberRow.get( ACTIVE )) builder = builder.withActive( Boolean.parseBoolean( cucumberRow.get( ACTIVE ) ) );
		return builder.build();
	}
}
