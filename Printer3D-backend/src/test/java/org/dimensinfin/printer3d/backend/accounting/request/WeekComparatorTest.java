package org.dimensinfin.printer3d.backend.accounting.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

public class WeekComparatorTest {

	@Test
	public void compare() {
		final WeekAmount amount1 = new WeekAmount.Builder().withYear( 2020 ).withWeek( 10 ).withAmount( 100.0F ).build();
		final WeekAmount amount2 = new WeekAmount.Builder().withYear( 2020 ).withWeek( 11 ).withAmount( 110.0F ).build();
		Assertions.assertEquals( -1, new WeekComparator().compare( amount1, amount2 ) );
		Assertions.assertEquals( 1, new WeekComparator().compare( amount2, amount1 ) );
		Assertions.assertEquals( 0, new WeekComparator().compare( amount2, amount2 ) );
	}
}
