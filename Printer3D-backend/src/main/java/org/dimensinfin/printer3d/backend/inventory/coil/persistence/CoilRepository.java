package org.dimensinfin.printer3d.backend.inventory.coil.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoilRepository extends JpaRepository<Coil, UUID> {
}
