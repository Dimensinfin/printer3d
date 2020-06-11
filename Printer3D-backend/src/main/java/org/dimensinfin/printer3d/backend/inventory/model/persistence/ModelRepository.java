package org.dimensinfin.printer3d.backend.inventory.model.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository  extends JpaRepository<ModelEntity, UUID> {
}
