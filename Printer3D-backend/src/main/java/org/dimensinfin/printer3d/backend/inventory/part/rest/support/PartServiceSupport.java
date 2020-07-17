package org.dimensinfin.printer3d.backend.inventory.part.rest.support;

import java.sql.SQLException;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.common.exception.DimensinfinRuntimeException;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.Printer3DErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;

@Service
public class PartServiceSupport {
	private PartRepository partRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public PartServiceSupport( @NotNull final PartRepository partRepository ) {
		this.partRepository = Objects.requireNonNull( partRepository );
	}

	public CounterResponse deleteAllParts() {
		try {
			final long recordCount = this.partRepository.count();
			this.partRepository.deleteAll();
			return new CounterResponse.Builder()
					.withRecords( (int) recordCount )
					.build();
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new DimensinfinRuntimeException( Printer3DErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE( new SQLException( sqle ) ),
					"Detected exception while deleting all Parts from the repository." );
		}
	}
}
