package org.dimensinfin.printer3d.backend.acceptance.steps;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.inventory.coil.rest.CoilFeignClientSupport;

import io.cucumber.java.en.Given;

public class GivenACleanCoilsRepository {
	private final CoilFeignClientSupport coilFeignClientSupport;

// - C O N S T R U C T O R S
	public GivenACleanCoilsRepository( final CoilFeignClientSupport coilFeignClientSupport ) {
		this.coilFeignClientSupport = Objects.requireNonNull( coilFeignClientSupport );
	}

	@Given("a clean Coil repository table")
	public void a_clean_Coil_repository_table() {
		final Integer records = this.coilFeignClientSupport.clearCoilRepositoryTable();
		Assertions.assertNotNull( records );
	}

	@Given("a clean Coils repository")
	public void a_clean_Coils_repository() {
		final Integer records = this.coilFeignClientSupport.clearCoilRepositoryTable();
		Assertions.assertNotNull( records );
	}
}
