package org.dimensinfin.printer3d.backend.support.inventory.coil;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.core.CommonValidator;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ACTIVE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.COLOR;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.DESTRUCTION_TIME;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.MATERIAL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.TRADE_MARK;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.WEIGHT;

public class CoilValidator extends CommonValidator implements Validator<Coil> {
	private final Printer3DWorld printer3DWorld;

	// - C O N S T R U C T O R S
	public CoilValidator( final Printer3DWorld printer3DWorld ) {
		this.printer3DWorld = printer3DWorld;
	}

	//	@Override
	@Override
	public boolean validate( final Map<String, String> rowData, final Coil record ) {
		if (null != rowData.get( ID ))
			Assertions.assertEquals( this.cucumberDataMap( this.printer3DWorld, rowData.get( ID ) ), record.getId().toString() );
		Assertions.assertEquals( rowData.get( MATERIAL ), record.getMaterial() );
		Assertions.assertEquals( rowData.get( TRADE_MARK ), record.getTradeMark() );
		Assertions.assertEquals( rowData.get( COLOR ), record.getColor() );
		if (null != rowData.get( WEIGHT ))
			Assertions.assertEquals( Integer.parseInt( rowData.get( WEIGHT ) ), record.getWeight() );
		if (null != rowData.get( LABEL ))
			Assertions.assertEquals( rowData.get( LABEL ), record.getLabel() );
		if (null != rowData.get( ACTIVE ))
			Assertions.assertEquals( Boolean.parseBoolean( rowData.get( ACTIVE ) ), record.getActive() );
		if (null != rowData.get( DESTRUCTION_TIME ))
			Assertions.assertEquals(
					rowData.get( DESTRUCTION_TIME ),
					this.cucumberDataReplacer( rowData.get( DESTRUCTION_TIME ),
							(null != record.getDestructionTime()) ? record.getDestructionTime().toString() : null )
			);
		return true;
	}
}
