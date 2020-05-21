package org.dimensinfin.printer3d.backend.steps;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.part.rest.support.PartFeignClientSupport;

import io.cucumber.java.en.Given;

public class GivenACleanInventoryRepository extends StepSupport{
	private PartFeignClientSupport partFeignClientSupport;
	public GivenACleanInventoryRepository( @NotNull final Printer3DWorld printer3DWorld ,
	                                       @NotNull final        PartFeignClientSupport partFeignClientSupport    ) {
		super( printer3DWorld );
		this.partFeignClientSupport = Objects.requireNonNull(partFeignClientSupport);
	}

	@Given("a clean Inventory repository")
	public void a_clean_Inventory_repository() {
		final Integer records = this.partFeignClientSupport.deleteAllParts();
		Assertions.assertNotNull( records );
	}
}
