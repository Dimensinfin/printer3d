package org.dimensinfin.poc.poccucumber.acceptance.steps;

import javax.validation.constraints.NotNull;

import org.dimensinfin.poc.poccucumber.acceptance.support.POCWorld;

import lombok.Getter;

public abstract class StepSupport {
	@Getter
	private POCWorld POCWorld;

	// - C O N S T R U C T O R S
	public StepSupport( @NotNull final POCWorld POCWorld ) {
		this.POCWorld = POCWorld;
	}
}
