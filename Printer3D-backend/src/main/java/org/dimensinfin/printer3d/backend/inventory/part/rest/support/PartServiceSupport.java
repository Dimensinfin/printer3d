package org.dimensinfin.printer3d.backend.inventory.part.rest.support;

import java.sql.SQLException;
import java.util.Objects;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.dimensinfin.common.client.rest.CountResponse;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.core.exception.RepositoryException;
import org.dimensinfin.printer3d.backend.exception.ErrorInfo;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.InventoryRepository;

@Service
public class PartServiceSupport {
	private InventoryRepository inventoryRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public PartServiceSupport( @NotNull final InventoryRepository inventoryRepository ) {
		this.inventoryRepository = Objects.requireNonNull( inventoryRepository );
	}

	public CountResponse deleteAllParts() {
		try {
			final long recordCount = this.inventoryRepository.count();
			this.inventoryRepository.deleteAll();
			return new CountResponse.Builder()
					.withRecords( (int) recordCount )
					.build();
		} catch (final RuntimeException sqle) {
			LogWrapper.error( sqle );
			throw new RepositoryException( ErrorInfo.INVENTORY_STORE_REPOSITORY_FAILURE, new SQLException( sqle ) );
		}
	}
}
