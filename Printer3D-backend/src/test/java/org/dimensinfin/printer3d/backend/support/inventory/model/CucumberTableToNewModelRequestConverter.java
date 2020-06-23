package org.dimensinfin.printer3d.backend.support.inventory.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PART_ID_LIST;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PRICE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STOCK_LEVEL;

public class CucumberTableToNewModelRequestConverter extends CucumberTableConverter<NewModelRequest> {
	@Override
	public NewModelRequest convert( final Map<String, String> cucumberRow ) {
		NewModelRequest.Builder builder = new NewModelRequest.Builder();
		if (null != cucumberRow.get( ID )) builder = builder.withId( UUID.fromString( cucumberRow.get( ID ) ) );
		if (null != cucumberRow.get( LABEL )) builder = builder.withLabel( cucumberRow.get( LABEL ) );
		if (null != cucumberRow.get( PRICE )) builder = builder.withPrice( Float.parseFloat( cucumberRow.get( PRICE ) ) );
		if (null != cucumberRow.get( STOCK_LEVEL )) builder = builder.withStockLevel( Integer.parseInt( cucumberRow.get( STOCK_LEVEL ) ) );
//		if (null != cucumberRow.get( STOCK_AVAILABLE ))
//			builder = builder.withStockAvailable( Integer.parseInt( cucumberRow.get( STOCK_AVAILABLE ) ) );
		if (null != cucumberRow.get( IMAGE_PATH )) builder = builder.withImagePath( cucumberRow.get( IMAGE_PATH ) );
		if (null != cucumberRow.get( PART_ID_LIST )) builder = builder.withPartIdList(
				this.decodeCucumberUUIDList( cucumberRow.get( PART_ID_LIST ) )
		);
		return builder.build();
	}

	private List<UUID> decodeCucumberUUIDList( final String uuidList ) {
		final List<UUID> results = new ArrayList<>();
		final String[] uuids = uuidList.split( "," );
		for (int i = 0; i < uuids.length; i++)
			results.add( UUID.fromString( uuids[i].trim() ) );
		return results;
	}
}
