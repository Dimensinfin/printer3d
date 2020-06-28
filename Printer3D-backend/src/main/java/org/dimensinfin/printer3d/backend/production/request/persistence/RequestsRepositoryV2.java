package org.dimensinfin.printer3d.backend.production.request.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsRepositoryV2 extends JpaRepository<RequestEntityV2, UUID> {
}
