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
import org.dimensinfin.printer3d.backend.inventory.roll.persistence.CoilRepository;

@Profile({ "local", "acceptance", "test" })
@RestController
@CrossOrigin
@Validated
@RequestMapping("/api/v1")
public class CoilControllerSupport {
	private final CoilRepository coilRepository;

	// - C O N S T R U C T O R S
	public CoilControllerSupport( final CoilRepository coilRepository ) {
		this.coilRepository = Objects.requireNonNull( coilRepository );
	}

	@GetMapping(path = "/inventory/rolls/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CountResponse> deleteAllRolls() {
		try {
			final long recordCount = this.coilRepository.count();
			this.coilRepository.deleteAll();
			return new ResponseEntity<>( new CountResponse.Builder()
					.withRecords( (int) recordCount )
					.build(), HttpStatus.OK );
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new RepositoryException( ErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE, new SQLException( sqle ) );
		}
	}
}
