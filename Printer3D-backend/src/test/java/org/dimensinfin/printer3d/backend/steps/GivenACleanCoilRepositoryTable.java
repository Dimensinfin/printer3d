package org.dimensinfin.printer3d.backend.steps;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.roll.rest.support.CoilFeignClientSupport;

import io.cucumber.java.en.Given;

public class GivenACleanCoilRepositoryTable {
	private final CoilFeignClientSupport coilFeignClientSupport;

	public GivenACleanCoilRepositoryTable( final CoilFeignClientSupport coilFeignClientSupport ) {
		this.coilFeignClientSupport = Objects.requireNonNull( coilFeignClientSupport );
	}

	@Given("a clean Coil repository table")
	public void a_clean_Coil_repository_table() {
		final Integer records = this.coilFeignClientSupport.clearCoilRepositoryTable();
		Assertions.assertNotNull( records );
	}
}
