package org.dimensinfin.printer3d.backend.steps;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.model.rest.ModelFeignClientSupport;

import io.cucumber.java.en.Given;

public class GivenACleanModelRepository extends StepSupport {
	private final ModelFeignClientSupport modelFeignClientSupport;

	// - C O N S T R U C T O R S
	public GivenACleanModelRepository( final @NotNull Printer3DWorld printer3DWorld,
	                                   final @NotNull ModelFeignClientSupport modelFeignClientSupport ) {
		super( printer3DWorld );
		this.modelFeignClientSupport = Objects.requireNonNull( modelFeignClientSupport );
	}

	@Given("a clean Model repository")
	public void a_clean_Model_repository() {
		final Integer records = this.modelFeignClientSupport.deleteAllModels();
		Assertions.assertNotNull( records );
	}

}
