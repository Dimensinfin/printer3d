package org.dimensinfin.poc.poccucumber.acceptance.steps;

import javax.validation.constraints.NotNull;

import org.dimensinfin.poc.poccucumber.acceptance.support.POCWorld;

public abstract class StepSupport {
	private POCWorld world;

	// - C O N S T R U C T O R S
	public StepSupport( @NotNull final POCWorld world ) {
		this.world = world;
	}

	public POCWorld getWorld() {
		return this.world;
	}
}
