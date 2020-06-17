package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public interface PartRepository extends JpaRepository<Part, UUID> {
}
