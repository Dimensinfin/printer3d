package org.dimensinfin.printer3d.backend.production.request.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsRepository extends JpaRepository<RequestEntity, UUID> {
}
