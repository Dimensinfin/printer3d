package org.dimensinfin.printer3d.backend.support.inventory.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ACTIVE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.ID;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.IMAGE_PATH;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.LABEL;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PART_ID_LIST;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.PRICE;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.STOCK_LEVEL;

public class ModelValidator implements Validator<Model> {
	@Override
	public boolean validate( final Map<String, String> rowData, final Model record ) {
		if (null != rowData.get( ID )) Assertions.assertEquals( rowData.get( ID ), record.getId().toString() );
		if (null != rowData.get( LABEL )) Assertions.assertEquals( rowData.get( LABEL ), record.getLabel() );
		if (null != rowData.get( PRICE )) Assertions.assertEquals( Float.parseFloat( rowData.get( PRICE ) ), record.getPrice() );
		if (null != rowData.get( STOCK_LEVEL )) Assertions.assertEquals( Integer.parseInt( rowData.get( STOCK_LEVEL ) ), record.getStockLevel() );
		if (null != rowData.get( IMAGE_PATH )) Assertions.assertEquals( rowData.get( IMAGE_PATH ), record.getImagePath() );
		if (null != rowData.get( ACTIVE )) Assertions.assertEquals( Boolean.parseBoolean( rowData.get( ACTIVE ) ), record.isActive() );

		if ( null != rowData.get( PART_ID_LIST )){
			final List<UUID> uuidList = this.decodeCucumberUUIDList( rowData.get( PART_ID_LIST ) );
			final List<UUID> partIdList =record.getPartIdentifierList();
			Collections.sort(uuidList);
			Collections.sort(partIdList);
			boolean isEqual = uuidList.equals(partIdList);
			Assertions.assertTrue( isEqual );
		}
		return true;
	}
	private List<UUID> decodeCucumberUUIDList( final String uuidList ) {
		final List<UUID> results = new ArrayList<>();
		final String[] uuids = uuidList.split( "," );
		for (int i = 0; i < uuids.length; i++)
			results.add( UUID.fromString( uuids[i].trim() ) );
		return results;
	}
}
