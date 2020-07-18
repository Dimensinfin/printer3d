package org.dimensinfin.printer3d.backend.steps;

import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;

import io.cucumber.java.en.When;

public class B3D10Machines extends StepSupport {
	public B3D10Machines( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

}
