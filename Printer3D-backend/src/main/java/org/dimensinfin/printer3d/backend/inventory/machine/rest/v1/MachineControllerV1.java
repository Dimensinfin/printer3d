package org.dimensinfin.printer3d.backend.inventory.machine.rest.v1;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.domain.MachineList;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class MachineControllerV1 {
private	final MachineServiceV1 machineServiceV1;

	public MachineControllerV1( final MachineServiceV1 machineServiceV1 ) {
		this.machineServiceV1 = Objects.requireNonNull(machineServiceV1);
	}

	@GetMapping(path = "/inventory/machines",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<MachineList> getMachines( ) {
		return new ResponseEntity<>( this.machineServiceV1.getMachines(  ), HttpStatus.OK );
	}
}
