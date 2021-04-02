package org.dimensinfin.printer3d.backend.support.accounting.rest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.threeten.extra.YearWeek;

import org.dimensinfin.acceptance.support.Validator;
import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.AMOUNT;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.WEEK;
import static org.dimensinfin.printer3d.backend.support.core.AcceptanceFieldMapConstants.YEAR;

public class WeekAmountValidator implements Validator<WeekAmount> {
	@Override
	public boolean validate( final Map<String, String> rowData, final WeekAmount record ) {
		if (null != rowData.get( YEAR ))
			if (rowData.get( YEAR ).toLowerCase().contains( "<year>" )) {
				Assertions.assertEquals( LocalDate.now().getYear(), record.getYear() );
			} else
				Assertions.assertEquals( Integer.parseInt( rowData.get( YEAR ) ), record.getYear() );
		if (null != rowData.get( WEEK )) {
			if (rowData.get( WEEK ).toLowerCase().contains( "<week>" )) {
				final int week = YearWeek.from( Instant.now().atZone( ZoneId.systemDefault() ) ).getWeek();
				Assertions.assertEquals( week, record.getWeek() );
			}
			if (rowData.get( WEEK ).toLowerCase().contains( "<week-1>" )) {
				final int week = YearWeek.from( Instant.now().atZone( ZoneId.systemDefault() ) ).getWeek();
				Assertions.assertEquals( week - 1, record.getWeek() );
			}
			//			Assertions.assertEquals( Integer.parseInt( rowData.get( WEEK ) ), record.getWeek() );
		}
		if (null != rowData.get( AMOUNT ))
			Assertions.assertEquals( Float.parseFloat( rowData.get( AMOUNT ) ), record.getAmount() );
		return true;
	}
}
