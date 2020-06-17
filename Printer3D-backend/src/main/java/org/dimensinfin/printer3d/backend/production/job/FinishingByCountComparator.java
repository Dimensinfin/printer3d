package org.dimensinfin.printer3d.backend.production.job;

import java.util.Comparator;

import org.dimensinfin.printer3d.backend.production.domain.FinishingContainer;

public class FinishingByCountComparator implements Comparator<FinishingContainer> {

	@Override
	public int compare( final FinishingContainer finishing1, final FinishingContainer finishing2 ) {
		return (finishing2.getJobCount() > finishing1.getJobCount() ? 1 : -1);
	}
}
