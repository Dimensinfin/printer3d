package org.dimensinfin.printer3d.backend.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.machine.CucumberTableToSetupRequestConverter;
import org.dimensinfin.printer3d.backend.support.inventory.machine.rest.MachineFeignClientSupport;
import org.dimensinfin.printer3d.client.inventory.rest.SetupRequest;

import io.cucumber.java.en.Given;

public class GivenTheNextSetupForMachine extends StepSupport {
	private final MachineFeignClientSupport machineFeignClientSupport;

	// - C O N S T R U C T O R S
	public GivenTheNextSetupForMachine( final @NotNull Printer3DWorld printer3DWorld,
	                                    final @NotNull MachineFeignClientSupport machineFeignClientSupport ) {
		super( printer3DWorld );
		this.machineFeignClientSupport = Objects.requireNonNull( machineFeignClientSupport );
	}

	@Given("the next setup for Machine {string}")
	public void the_next_setup_for_Machine( final String machineLabel, final List<Map<String, String>> dataTable ) throws IOException {
		final Map<String, String> row = dataTable.get( 0 );
		final SetupRequest request = new CucumberTableToSetupRequestConverter( machineLabel ).convert( row );
		Assertions.assertNotNull( this.machineFeignClientSupport.setupMachine( request ) );
	}
}
