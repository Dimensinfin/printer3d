package org.dimensinfin.printer3d.backend.support.accounting.rest;

import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.AMOUNT;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.WEEK;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.YEAR;

public class WeekAmountValidator implements Validator<WeekAmount> {
	@Override
	public boolean validate( final Map<String, String> rowData, final WeekAmount record ) {
		if (null != rowData.get( YEAR ))
			Assertions.assertEquals( Integer.parseInt( rowData.get( YEAR ) ), record.getYear() );
		if (null != rowData.get( WEEK ))
			Assertions.assertEquals( Integer.parseInt( rowData.get( WEEK ) ), record.getWeek() );
		if (null != rowData.get( AMOUNT ))
			Assertions.assertEquals( Float.parseFloat( rowData.get( AMOUNT ) ), record.getAmount() );
		return true;
	}
}
