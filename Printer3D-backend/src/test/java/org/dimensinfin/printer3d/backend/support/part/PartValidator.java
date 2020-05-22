package org.dimensinfin.printer3d.backend.support.part;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.backend.part.persistence.Part;

import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.ACTIVE;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.COST;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.DESCRIPTION;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.part.PartMapConstants.PRICE;

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
