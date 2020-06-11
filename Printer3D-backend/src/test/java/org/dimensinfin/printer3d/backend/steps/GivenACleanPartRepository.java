package org.dimensinfin.printer3d.backend.steps;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.part.rest.PartFeignClientSupport;

import io.cucumber.java.en.Given;

public class GivenACleanPartRepository extends StepSupport{
	private PartFeignClientSupport partFeignClientSupport;
	public GivenACleanPartRepository( @NotNull final Printer3DWorld printer3DWorld ,
	                                  @NotNull final        PartFeignClientSupport partFeignClientSupport    ) {
		super( printer3DWorld );
		this.partFeignClientSupport = Objects.requireNonNull(partFeignClientSupport);
	}

	@Given("a clean Inventory repository")
	public void a_clean_Inventory_repository() {
		final Integer records = this.partFeignClientSupport.deleteAllParts();
		Assertions.assertNotNull( records );
	}
	@Given("a clean Part repository")
	public void a_clean_Part_repository() {
		final Integer records = this.partFeignClientSupport.deleteAllParts();
		Assertions.assertNotNull( records );
	}
}
