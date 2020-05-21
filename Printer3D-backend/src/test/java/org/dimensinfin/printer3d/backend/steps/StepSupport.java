package org.dimensinfin.printer3d.backend.steps;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;

public abstract class StepSupport {
	protected Printer3DWorld printer3DWorld;

	// - C O N S T R U C T O R S
	public StepSupport( final Printer3DWorld printer3DWorld ) {
		this.printer3DWorld = printer3DWorld;
	}
}
