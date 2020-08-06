package org.dimensinfin.printer3d.backend.inventory.machine.domain;

import java.util.Comparator;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;

public class CoilWeightComparator implements Comparator<Coil> {
	/**
	 * Return the weeks in chronological order using also year.
	 */
	@Override
	public int compare( final Coil coil1, final Coil coil2 ) {
		return Integer.compare( coil1.getWeight(), coil2.getWeight() );
	}
}
