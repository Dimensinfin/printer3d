package org.dimensinfin.printer3d.backend.support.inventory.coil;

import java.util.Map;
import java.util.UUID;

import org.dimensinfin.acceptance.support.converter.CucumberTableConverter;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ACTIVE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.COLOR;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.COLOR_SET;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.MATERIAL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.WEIGHT;

public class CucumberTableToCoilConverter extends CucumberTableConverter<Coil> {
	protected final Printer3DWorld printer3DWorld;

	// - C O N S T R U C T O R S
	public CucumberTableToCoilConverter( final Printer3DWorld printer3DWorld ) {
		this.printer3DWorld = printer3DWorld;
	}

	@Override
	public Coil convert( final Map<String, String> cucumberRow ) {
		Coil.Builder builder = new Coil.Builder();
		if (null != cucumberRow.get( ID )) builder = builder.withId(
				UUID.fromString( this.cucumberDataMap( this.printer3DWorld,
						cucumberRow.get( ID ) ) )
		);
		if (null != cucumberRow.get( MATERIAL )) builder = builder.withMaterial( cucumberRow.get( MATERIAL ) );
		if (null != cucumberRow.get( COLOR )) builder = builder.withColor( cucumberRow.get( COLOR ) );
		if (null != cucumberRow.get( COLOR_SET )) builder = builder.withColorSet( cucumberRow.get( COLOR_SET ) );
		if (null != cucumberRow.get( WEIGHT )) builder = builder.withWeight( Integer.parseInt( cucumberRow.get( WEIGHT ) ) );
		if (null != cucumberRow.get( ACTIVE )) builder = builder.withActive( Boolean.parseBoolean( cucumberRow.get( ACTIVE ) ) );
		return builder.build();
	}
}
