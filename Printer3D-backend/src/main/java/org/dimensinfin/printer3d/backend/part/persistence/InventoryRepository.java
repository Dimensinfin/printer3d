package org.dimensinfin.printer3d.backend.part.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Part, UUID> {
}
