package org.dimensinfin.printer3d.backend.inventory.machine.rest.support;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineEntity;
import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.machine.rest.v1.MachineServiceV1;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.rest.PartRestErrors;
import org.dimensinfin.printer3d.client.inventory.rest.dto.SetupRequest;

@Profile({ "local", "dev", "acceptance", "test" })
@RestController
@Validated
@RequestMapping("/api/v1")
@Service
@Transactional
public class MachineControllerSupport {
	private final MachineRepository machineRepository;
	private final PartRepository partRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public MachineControllerSupport( final MachineRepository machineRepository,
	                                 final PartRepository partRepository ) {
		this.machineRepository = Objects.requireNonNull( machineRepository );
		this.partRepository = partRepository;
	}

	@PutMapping(path = "/inventory/machines/setup",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Boolean> setupMachine( final @RequestBody @Valid @NotNull SetupRequest setupRequest ) {
		// Change the setup for the selected machine
		final List<MachineEntity> machines = this.machineRepository.findByLabel( setupRequest.getMachineLabel() );
		if (machines.isEmpty())
			throw new DimensinfinRuntimeException( MachineServiceV1.errorMACHINENOTFOUND( setupRequest.getMachineLabel() ) );
		for (final MachineEntity machine : machines) {
			LogWrapper.info( "Found Machine: " + machine.toString() );
			// Search for part if defined
			if (null != setupRequest.getPartId()) {
				final PartEntity partEntity = this.partRepository.findById( setupRequest.getPartId() ).orElseThrow( () ->
						new DimensinfinRuntimeException( PartRestErrors.errorPARTNOTFOUND( setupRequest.getPartId() ),
								"Part not found while updating the Machine setup for acceptance testing." )
				);
				final MachineEntity updatedMachine = this.machineRepository.save( new SupportMachineUpdater( machine ).update(
						setupRequest,
						partEntity.getBuildTime()
				) );
				LogWrapper.info( "Updated Machine: " + updatedMachine.toString() );
			} else {
				final MachineEntity updatedMachine = this.machineRepository.save( new SupportMachineUpdater( machine ).update(
						setupRequest,
						0
				) );
				LogWrapper.info( "Updated Machine: " + updatedMachine.toString() );
			}
		}
		return new ResponseEntity<>( true, HttpStatus.OK );
	}
}
