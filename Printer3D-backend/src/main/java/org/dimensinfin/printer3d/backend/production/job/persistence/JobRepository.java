package org.dimensinfin.printer3d.backend.production.job.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
}
