package org.dimensinfin.printer3d.backend.inventory.coil.rest.support;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.core.scheduler.RemoveEmptyCoilsJob;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.core.dto.CounterResponse;

@Profile({ "dev", "acceptance" })
@RestController
@Validated
@RequestMapping("/api/v1")
public class CoilControllerSupport {
	private final CoilRepository coilRepository;
	private final RemoveEmptyCoilsJob removeEmptyCoilsJob;

	// - C O N S T R U C T O R S
	public CoilControllerSupport( @NotNull final CoilRepository coilRepository,
	                              @NotNull final RemoveEmptyCoilsJob removeEmptyCoilsJob ) {
		this.coilRepository = Objects.requireNonNull( coilRepository );
		this.removeEmptyCoilsJob = removeEmptyCoilsJob;
	}

	@GetMapping(path = "/inventory/coils/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CounterResponse> deleteAllCoils() {
		try {
			final long recordCount = this.coilRepository.count();
			this.coilRepository.deleteAll();
			return new ResponseEntity<>( new CounterResponse.Builder()
					.withRecords( (int) recordCount )
					.build(), HttpStatus.OK );
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorINVENTORYSTOREREPOSITORYFAILURE( new SQLException( sqle ) ),
					"Detected exception while deleting all coils on repository." );
		}
	}

	@GetMapping(path = "/inventory/coils/reset/destruction",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CounterResponse> resetCoilScheduler() {
		try {
			final List<CoilEntity> updatedCoils = new ArrayList<>();
			final Instant nowTime = Instant.now();
			this.coilRepository.findAll()
					.stream()
					.filter( coilEntity -> null != coilEntity.getDestructionTime() )
					.forEach( coilEntity -> {
						updatedCoils.add( coilEntity );
						coilEntity.setDestructionTime( nowTime );
						this.coilRepository.save( coilEntity );
					} );
			// Call the service to remove the coils
			this.removeEmptyCoilsJob.process();
			return new ResponseEntity<>( new CounterResponse.Builder()
					.withRecords( updatedCoils.size() )
					.build(), HttpStatus.OK );
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new DimensinfinRuntimeException( Printer3DErrorInfo.errorINVENTORYSTOREREPOSITORYFAILURE( new SQLException( sqle ) ),
					"Detected exception while updating the destruction schedule time on Coils." );
		}
	}
}
