package org.dimensinfin.printer3d.backend.steps;

import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.production.request.rest.RequestFeignClientSupport;

import io.cucumber.java.en.Given;

public class GivenACleanRequestsRepository extends StepSupport {
	private final RequestFeignClientSupport requestFeignClientSupport;

	// - C O N S T R U C T O R S
	public GivenACleanRequestsRepository( final @NotNull Printer3DWorld printer3DWorld,
	                                      final @NotNull RequestFeignClientSupport requestFeignClientSupport ) {
		super( printer3DWorld );
		this.requestFeignClientSupport = Objects.requireNonNull( requestFeignClientSupport );
	}

	@Given("a clean Requests repository")
	public void a_clean_Requests_repository() {
		final Integer records = this.requestFeignClientSupport.deleteAllRequests();
		Assertions.assertNotNull( records );
	}
}

