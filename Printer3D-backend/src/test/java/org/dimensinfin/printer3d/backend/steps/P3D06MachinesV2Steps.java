package org.dimensinfin.printer3d.backend.steps;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.printer3d.backend.support.Printer3DWorld;
import org.dimensinfin.printer3d.backend.support.inventory.machine.BuildRecordValidator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;

import io.cucumber.java.en.Then;

public class P3D06MachinesV2Steps extends StepSupport{
	public P3D06MachinesV2Steps( final @NotNull Printer3DWorld printer3DWorld ) {
		super( printer3DWorld );
	}

	@Then("the machine {string} has the next build information")
	public void the_machine_has_the_next_build_information(final String machineLabel, final List<Map<String, String>> dataTable) {
		final ResponseEntity<MachineListV2> machinesResponse = this.printer3DWorld.getMachineListv2ResponseEntity();
		Assertions.assertNotNull( machinesResponse );
		Assertions.assertNotNull( machinesResponse.getBody() );
		for (MachineV2 machine : machinesResponse.getBody().getMachines()) {
			if (machine.getLabel().equalsIgnoreCase( machineLabel )) {
				Assertions.assertTrue( new BuildRecordValidator().validate( dataTable.get( 0 ), machine.getBuildRecord() ) );
			}
		}
	}
}
