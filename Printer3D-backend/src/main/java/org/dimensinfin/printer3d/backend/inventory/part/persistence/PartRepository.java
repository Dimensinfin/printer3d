package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<PartEntity, UUID> {
}
