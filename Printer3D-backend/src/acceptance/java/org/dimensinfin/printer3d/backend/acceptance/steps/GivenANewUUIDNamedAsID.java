package org.dimensinfin.printer3d.backend.acceptance.steps;

import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;

import io.cucumber.java.en.Given;

public class GivenANewUUIDNamedAsID extends StepSupport {
// - C O N S T R U C T O R S
	public GivenANewUUIDNamedAsID( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Given("a new UUID named as ID")
	public void a_new_UUID_named_as_ID() {
		this.printer3DWorld.setId( UUID.randomUUID() );
	}
}
