package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.machine.BuildRecordValidator;
import org.dimensinfin.printer3d.backend.support.inventory.machine.CucumberTableToJobRequestConverter;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class B3D10Machines extends StepSupport {
	// - C O N S T R U C T O R S
	public B3D10Machines( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("the machine {string} has the next build information")
	public void the_machine_has_the_next_build_information( final String machineLabel, final List<Map<String, String>> dataTable ) {
		final ResponseEntity<List<MachineV2>> machinesResponse = this.printer3DWorld.getListMachineV2ResponseEntity();
		Assertions.assertNotNull( machinesResponse );
		Assertions.assertNotNull( machinesResponse.getBody() );
		for (MachineV2 machine : machinesResponse.getBody()) {
			if (machine.getLabel().equalsIgnoreCase( machineLabel )) {
				Assertions.assertTrue( new BuildRecordValidator().validate( dataTable.get( 0 ), machine.getBuildRecord() ) );
			}
		}
	}

	@Given("the next Job Request request")
	public void the_next_Job_Request_request( final List<Map<String, String>> dataTable ) {
		final JobRequest jobRequest = new CucumberTableToJobRequestConverter().convert( dataTable.get( 0 ) );
		Assertions.assertNotNull( jobRequest );
		this.printer3DWorld.setJobRequest( jobRequest );
	}

}
