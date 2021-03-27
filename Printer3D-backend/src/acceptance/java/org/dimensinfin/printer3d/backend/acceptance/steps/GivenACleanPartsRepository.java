package org.dimensinfin.printer3d.backend.acceptance.steps;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.part.rest.PartFeignClientSupport;

import io.cucumber.java.en.Given;

public class GivenACleanPartsRepository extends StepSupport {
	private final PartFeignClientSupport partFeignClientSupport;

	// - C O N S T R U C T O R S
	public GivenACleanPartsRepository( @NotNull final Printer3DWorld printer3DWorld,
	                                   @NotNull final PartFeignClientSupport partFeignClientSupport ) {
		super( printer3DWorld );
		this.partFeignClientSupport = Objects.requireNonNull( partFeignClientSupport );
	}

	@Given("a clean Inventory repository")
	public void a_clean_Inventory_repository() {
		final Integer records = this.partFeignClientSupport.deleteAllParts();
		Assertions.assertNotNull( records );
	}

	@Given("a clean Parts repository")
	public void a_clean_Part_repository() {
		final Integer records = this.partFeignClientSupport.deleteAllParts();
		Assertions.assertNotNull( records );
	}
}
