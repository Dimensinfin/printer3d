package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;

@RestController
//@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class MachineControllerV1 {
	private final MachineServiceV1 machineServiceV1;

	// - C O N S T R U C T O R S
	public MachineControllerV1( final MachineServiceV1 machineServiceV1 ) {
		this.machineServiceV1 = Objects.requireNonNull( machineServiceV1 );
	}

	// - G E T T E R S   &   S E T T E R S
	@PutMapping(path = "/inventory/machines/{machineId}/cancelbuild",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Machine> cancelBuild( final @PathVariable @NotNull UUID machineId ) {
		return new ResponseEntity<>( this.machineServiceV1.cancelBuild( machineId ), HttpStatus.OK );
	}

	@PutMapping(path = "/inventory/machines/{machineId}/completebuild",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Machine> completeBuild( final @PathVariable @NotNull UUID machineId ) {
		return new ResponseEntity<>( this.machineServiceV1.completeBuild( machineId ), HttpStatus.OK );
	}
}
