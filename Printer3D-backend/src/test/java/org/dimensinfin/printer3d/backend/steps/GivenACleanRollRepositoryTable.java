package org.dimensinfin.printer3d.backend.steps;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.roll.rest.support.RollFeignClientSupport;

import io.cucumber.java.en.Given;

public class GivenACleanRollRepositoryTable {
	private final RollFeignClientSupport rollFeignClientSupport;

	public GivenACleanRollRepositoryTable( final RollFeignClientSupport rollFeignClientSupport ) {
		this.rollFeignClientSupport = Objects.requireNonNull(rollFeignClientSupport);
	}

	@Given("a clean Roll repository table")
	public void a_clean_Roll_repository_table() {
		final Integer records = this.rollFeignClientSupport.clearRollRepositoryTable();
		Assertions.assertNotNull( records );
	}
}
