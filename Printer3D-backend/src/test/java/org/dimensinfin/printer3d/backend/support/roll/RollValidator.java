package org.dimensinfin.printer3d.backend.support.roll;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;

import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.MATERIAL;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.PART_ID;
import static org.dimensinfin.printer3d.backend.support.roll.RollMapConstants.COLOR;
import static org.dimensinfin.printer3d.backend.support.roll.RollMapConstants.WEIGHT;

public class RollValidator implements Validator<Roll> {
	@Override
	public boolean validate( final Map<String, String> rowData, final Roll record ) {
		Assertions.assertEquals( rowData.get( PART_ID ), record.getId().toString() );
		Assertions.assertEquals( rowData.get( MATERIAL ), record.getMaterial() );
		Assertions.assertEquals( rowData.get( COLOR ), record.getColor() );
		Assertions.assertEquals( Integer.parseInt( rowData.get( WEIGHT ) ), record.getWeight() );
		return true;
	}
}
