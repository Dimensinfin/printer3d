package org.dimensinfin.printer3d.backend.inventory.machine.domain;

import java.util.Comparator;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;

public class CoilWeightComparator implements Comparator<CoilEntity> {
	/**
	 * Return the weeks in chronological order using also year.
	 */
	@Override
	public int compare( final CoilEntity coil1, final CoilEntity coil2 ) {
		return Integer.compare( coil1.getWeight(), coil2.getWeight() );
	}
}
