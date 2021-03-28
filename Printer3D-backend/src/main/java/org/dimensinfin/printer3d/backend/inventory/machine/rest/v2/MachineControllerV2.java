package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

@RestController
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
	public ResponseEntity<List<MachineV2>> getMachines() {
		return new ResponseEntity<>( this.machineServiceV2.getMachines(), HttpStatus.OK );
	}

	@PostMapping(path = "/inventory/machines/{machineId}/startbuild",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<MachineV2> startBuild( final @PathVariable @NotNull UUID machineId,
	                                             final @RequestBody @Valid @NotNull JobRequest jobRequest ) {
		return new ResponseEntity<>( this.machineServiceV2.startBuild( machineId, jobRequest ), HttpStatus.OK );
	}
}
