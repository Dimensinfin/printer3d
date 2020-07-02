package org.dimensinfin.printer3d.backend.support.inventory.part;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ACTIVE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.COLOR;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.COST;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.MATERIAL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PRICE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STOCK_LEVEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.WEIGHT;

public class CucumberTableToPartConverter extends CucumberTableConverter<Part> {
	@Override
	public Part convert( final Map<String, String> cucumberRow ) {
		Part.Builder builder = new Part.Builder();
		if (null != cucumberRow.get( ID )) builder = builder.withId( UUID.fromString( cucumberRow.get( ID ) ) );
		if (null != cucumberRow.get( LABEL )) builder = builder.withLabel( cucumberRow.get( LABEL ) );
		if (null != cucumberRow.get( DESCRIPTION )) builder = builder.withDescription( cucumberRow.get( DESCRIPTION ) );
		if (null != cucumberRow.get( MATERIAL )) builder = builder.withMaterial( cucumberRow.get( MATERIAL ) );
		if (null != cucumberRow.get( COLOR )) builder = builder.withColor( cucumberRow.get( COLOR ) );
		if (null != cucumberRow.get( WEIGHT )) builder = builder.withWeight( Integer.parseInt( cucumberRow.get( WEIGHT ) ) );
		if (null != cucumberRow.get( BUILD_TIME )) builder = builder.withBuildTime( Integer.parseInt( cucumberRow.get( BUILD_TIME ) ) );
		if (null != cucumberRow.get( COST )) builder = builder.withCost( Float.parseFloat( cucumberRow.get( COST ) ) );
		if (null != cucumberRow.get( PRICE )) builder = builder.withPrice( Float.parseFloat( cucumberRow.get( PRICE ) ) );
		if (null != cucumberRow.get( STOCK_LEVEL )) builder = builder.withStockLevel( Integer.parseInt( cucumberRow.get( STOCK_LEVEL ) ) );
		if (null != cucumberRow.get( STOCK_AVAILABLE ))
			builder = builder.withStockAvailable( Integer.parseInt( cucumberRow.get( STOCK_AVAILABLE ) ) );
		if (null != cucumberRow.get( IMAGE_PATH )) builder = builder.withImagePath( cucumberRow.get( IMAGE_PATH ) );
		if (null != cucumberRow.get( MODEL_PATH )) builder = builder.withModelPath( cucumberRow.get( MODEL_PATH ) );
		if (null != cucumberRow.get( ACTIVE )) builder = builder.withActive( Boolean.parseBoolean( cucumberRow.get( ACTIVE ) ) );
		return builder.build();
	}
}
