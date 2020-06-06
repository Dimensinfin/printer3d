package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineListV2;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v2")
public class MachineControllerV2 {
	private final MachineServiceV2 machineServiceV2;

	// - C O N S T R U C T O R S
	public MachineControllerV2( final MachineServiceV2 machineServiceV2 ) {
		this.machineServiceV2 = Objects.requireNonNull( machineServiceV2 );
	}

	// - G E T T E R S   &   S E T T E R S
	@GetMapping(path = "/inventory/machines",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<MachineListV2> getMachines() {
		final MachineListV2 data = this.machineServiceV2.getMachines();
		LogWrapper.info( data.toString() );
		return new ResponseEntity<>(data, HttpStatus.OK );
	}
}
