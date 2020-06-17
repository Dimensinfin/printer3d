package org.dimensinfin.printer3d.backend.production.job;

import java.util.Comparator;

import org.dimensinfin.printer3d.backend.production.domain.FinishingContainer;

public class FinishingByCountComparator implements Comparator<FinishingContainer> {
	/**
	 * Return the comparison for the number of jobs but being the Finishing with the most number of jobs the first.
	 * @param finishing1
	 * @param finishing2
	 * @return
	 */
	@Override
	public int compare( final FinishingContainer finishing1, final FinishingContainer finishing2 ) {
		return Integer.compare( finishing2.getJobCount(), finishing1.getJobCount() );
	}
}
