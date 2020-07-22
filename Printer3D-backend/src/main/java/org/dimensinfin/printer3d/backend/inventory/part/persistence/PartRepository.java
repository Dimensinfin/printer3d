package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<PartEntity, UUID> {
	List<PartEntity> findByLabel( final String label );
}
