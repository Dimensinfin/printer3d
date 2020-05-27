package org.dimensinfin.printer3d.backend.support.part.converter;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.backend.part.persistence.Part;

import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.ACTIVE;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.BUILD_TIME;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.COLOR_CODE;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.COST;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.MATERIAL;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.MODEL_PATH;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.PRICE;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.STOCK_AVAILABLE;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.STOCK_LEVEL;

public class CucumberTableToPartConverter extends CucumberTableConverter<Part> {
	@Override
	public Part convert( final Map<String, String> cucumberRow ) {
		Part.Builder builder = new Part.Builder();
		if (null != cucumberRow.get( ID )) builder = builder.withId( UUID.fromString( cucumberRow.get( ID ) ) );
		if (null != cucumberRow.get( LABEL )) builder = builder.withLabel( cucumberRow.get( LABEL ) );
		if (null != cucumberRow.get( DESCRIPTION )) builder = builder.withDescription( cucumberRow.get( DESCRIPTION ) );
		if (null != cucumberRow.get( MATERIAL )) builder = builder.withMaterial(  cucumberRow.get( MATERIAL )  );
		if (null != cucumberRow.get( COLOR_CODE )) builder = builder.withColorCode(  cucumberRow.get( COLOR_CODE )  );
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
