package org.dimensinfin.printer3d.backend.inventory.roll.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RollRepository extends JpaRepository<Roll, UUID> {
}
