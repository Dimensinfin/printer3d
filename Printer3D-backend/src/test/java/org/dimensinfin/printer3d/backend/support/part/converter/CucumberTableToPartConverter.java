package org.dimensinfin.printer3d.backend.support.part.converter;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.backend.part.persistence.Part;

import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.COST;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.PRICE;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.STOCK_LEVEL;

public class CucumberTableToPartConverter extends CucumberTableConverter<Part> {
	@Override
	public Part convert( final Map<String, String> cucumberRow ) {
		Part.Builder builder = new Part.Builder();
		if (null != cucumberRow.get( ID )) builder = builder.withId( UUID.fromString( cucumberRow.get( ID ) ) );
		if (null != cucumberRow.get( LABEL )) builder = builder.withLabel( cucumberRow.get( LABEL ) );
		//		if (null != cucumberRow.get( COLOR_CODE )) builder = builder.withColorCode( cucumberRow.get( COLOR_CODE ) );
		if (null != cucumberRow.get( COST )) builder = builder.withCost( Float.parseFloat( cucumberRow.get( COST ) ) );
		if (null != cucumberRow.get( PRICE )) builder = builder.withPrice( Float.parseFloat( cucumberRow.get( PRICE ) ) );
		if (null != cucumberRow.get( STOCK_LEVEL )) builder = builder.withStockLevel( Integer.parseInt( cucumberRow.get( STOCK_LEVEL ) ) );
		return builder.build();
	}
}
