package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<MachineEntity, UUID> {
	List<MachineEntity> findByLabel( final String label);
}
