package org.dimensinfin.printer3d.backend.support.inventory.coil;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.core.CommonValidator;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ACTIVE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.COLOR;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.MATERIAL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.WEIGHT;

public class CoilValidator extends CommonValidator implements Validator<Coil> {
	private final Printer3DWorld printer3DWorld;

	// - C O N S T R U C T O R S
	public CoilValidator( final Printer3DWorld printer3DWorld ) {
		this.printer3DWorld = printer3DWorld;
	}

	@Override
	public boolean validate( final Map<String, String> rowData, final Coil record ) {
		if (null != rowData.get( ID ))
			Assertions.assertEquals( this.cucumberDataMap( this.printer3DWorld, rowData.get( ID ) ), record.getId().toString() );
		Assertions.assertEquals( rowData.get( MATERIAL ), record.getMaterial() );
		if (null != rowData.get( COLOR ))
			Assertions.assertEquals( rowData.get( COLOR ), record.getColor() );
		if (null != rowData.get( LABEL ))
			Assertions.assertEquals( rowData.get( LABEL ), record.getLabel() );
		Assertions.assertEquals( Integer.parseInt( rowData.get( WEIGHT ) ), record.getWeight() );
		if (null != rowData.get( ACTIVE ))
			Assertions.assertEquals( Boolean.parseBoolean( rowData.get( ACTIVE ) ), record.getActive() );
		return true;
	}
}
