package org.dimensinfin.printer3d.backend.inventory.roll.rest.support;

import java.sql.SQLException;
import java.util.Objects;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.RollRepository;

@Profile({ "local", "acceptance", "test" })
@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class RollControllerSupport {
	private final RollRepository rollRepository;

	// - C O N S T R U C T O R S
	public RollControllerSupport( final RollRepository rollRepository ) {
		this.rollRepository = Objects.requireNonNull( rollRepository );
	}

	@GetMapping(path = "/inventory/rolls/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CountResponse> deleteAllRolls() {
		try {
			final long recordCount = this.rollRepository.count();
			this.rollRepository.deleteAll();
			return new ResponseEntity<>( new CountResponse.Builder()
					.withRecords( (int) recordCount )
					.build(), HttpStatus.OK );
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new RepositoryException( ErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE, new SQLException( sqle ) );
		}
	}
}
