package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.UUID;

public interface PartRepositoryExtended extends PartRepository {
	void decrementStock( final UUID recordId, final int quantity);
}
