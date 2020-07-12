package org.dimensinfin.printer3d.backend.inventory.machine.rest.support;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryConflictException;
import org.dimensinfin.printer3d.backend.exception.LogWrapperLocal;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.SetupRequest;

@Profile({ "local", "acceptance", "test" })
@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
@Service
@Transactional
public class MachineControllerSupport {
	private final MachineRepository machineRepository;

	// - C O N S T R U C T O R S
	public MachineControllerSupport( final MachineRepository machineRepository ) {
		this.machineRepository = Objects.requireNonNull( machineRepository );
	}

	@PutMapping(path = "/inventory/machines/setup",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Boolean> setupMachine( final @RequestBody @Valid @NotNull SetupRequest setupRequest ) {
		// Change the setup for the selected machine
		final List<MachineEntity> machines = this.machineRepository.findByLabel( setupRequest.getMachineLabel() );
		for (MachineEntity machine : machines)
			LogWrapperLocal.info( "Found Machine: " + machine.toString() );
		if (machines.isEmpty())
			throw new RepositoryConflictException( Printer3DErrorInfo.MACHINE_NOT_FOUND( setupRequest.getMachineLabel() ) );
		for (MachineEntity machine : machines)
			this.machineRepository.save( new SupportMachineUpdater( machine ).update( setupRequest ) );
		return new ResponseEntity<>( true, HttpStatus.OK );
	}
}
