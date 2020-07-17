package org.dimensinfin.printer3d.backend.accounting.request;

import java.util.Comparator;

import org.dimensinfin.printer3d.client.accounting.rest.dto.WeekAmount;

public class WeekComparator implements Comparator<WeekAmount> {
	/**
	 * Return the weeks in chronological order using also year.
	 */
	@Override
	public int compare( final WeekAmount week1, final WeekAmount week2 ) {
		return Integer.compare( week1.getYear() * 100 + week1.getWeek(), week2.getYear() * 100 + week2.getWeek() );
	}
}
