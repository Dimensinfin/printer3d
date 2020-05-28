package org.dimensinfin.printer3d.backend.support.roll;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;

import static org.dimensinfin.printer3d.backend.support.roll.RollMapConstants.COLOR;
import static org.dimensinfin.printer3d.backend.support.roll.RollMapConstants.MATERIAL;
import static org.dimensinfin.printer3d.backend.support.roll.RollMapConstants.ROLL_ID;
import static org.dimensinfin.printer3d.backend.support.roll.RollMapConstants.WEIGHT;

public class CucumberTableToRollConverter extends CucumberTableConverter<Roll> {
	@Override
	public Roll convert( final Map<String, String> cucumberRow ) {
		Roll.Builder builder = new Roll.Builder();
		if (null != cucumberRow.get( ROLL_ID )) builder = builder.withId( UUID.fromString( cucumberRow.get( ROLL_ID ) ) );
		if (null != cucumberRow.get( MATERIAL )) builder = builder.withMaterial( cucumberRow.get( MATERIAL ) );
		if (null != cucumberRow.get( COLOR )) builder = builder.withColor( cucumberRow.get( COLOR ) );
		if (null != cucumberRow.get( WEIGHT )) builder = builder.withWeight( Integer.parseInt( cucumberRow.get( WEIGHT ) ) );
		return builder.build();
	}
}
