package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.inventory.machine.persistence.Machine;
import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.client.domain.MachineList;

import io.cucumber.java.en.Then;

public class P3D04MachinesSteps   extends StepSupport  {

	public P3D04MachinesSteps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}
	@Then("the machine {string} has the next state")
	public void the_machine_has_the_next_state(final String machineLabel, final List<Map<String, String>> dataTable ) {
		final ResponseEntity<MachineList> machinesResponse = this.printer3DWorld.getMachineListResponseEntity();
		Assertions.assertNotNull( machinesResponse );
		Assertions.assertNotNull( machinesResponse.getBody() );
		for (Machine machine : machinesResponse.getBody().getMachines()){
			if ( machine.getLabel().equalsIgnoreCase(  machineLabel)){

			}
		}
	}
}
