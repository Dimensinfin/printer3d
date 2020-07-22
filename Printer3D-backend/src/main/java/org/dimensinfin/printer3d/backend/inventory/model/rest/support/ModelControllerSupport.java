package org.dimensinfin.printer3d.backend.inventory.model.rest.support;

import java.sql.SQLException;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;

@Profile({ "local", "acceptance", "test" })
@RestController
//@CrossOrigin
@Validated
@RequestMapping("/api/v1")
@Service
public class ModelControllerSupport {
	private final ModelRepository modelRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public ModelControllerSupport( final @NotNull ModelRepository modelRepository ) {
		this.modelRepository = Objects.requireNonNull( modelRepository );
	}

	@GetMapping(path = "/inventory/models/delete/all",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CounterResponse> deleteAllModels() {
		return new ResponseEntity<>( this.deleteAllModelsService(), HttpStatus.OK );
	}

	protected CounterResponse deleteAllModelsService() {
		try {
			final long recordCount = this.modelRepository.count();
			this.modelRepository.deleteAll();
			return new CounterResponse.Builder()
					.withRecords( (int) recordCount )
					.build();
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new DimensinfinRuntimeException( Printer3DErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE( new SQLException( sqle ) ),
					"Detected exception while deleting all Models from the repository." );
		}
	}
}
