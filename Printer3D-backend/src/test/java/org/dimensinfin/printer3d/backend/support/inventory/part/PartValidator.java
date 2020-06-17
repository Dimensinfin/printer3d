package org.dimensinfin.printer3d.backend.support.inventory.part;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ACTIVE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.COST;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PRICE;

public class PartValidator implements Validator<Part> {
	public boolean validate( final Map<String, String> rowData, final Part record ) {
		Assertions.assertEquals( rowData.get( ID ), record.getId().toString() );
		Assertions.assertEquals( rowData.get( LABEL ), record.getLabel() );
		Assertions.assertEquals( rowData.get( DESCRIPTION ), record.getDescription() );
		Assertions.assertEquals( Float.parseFloat( rowData.get( COST ) ), record.getCost() );
		Assertions.assertEquals( Float.parseFloat( rowData.get( PRICE ) ), record.getPrice() );
		Assertions.assertEquals( Boolean.parseBoolean( rowData.get( ACTIVE ) ), record.isActive() );
		return true;
	}

}
