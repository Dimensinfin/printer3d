package org.dimensinfin.printer3d.application.ports.outbound;

import java.util.List;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

public interface CoilPort {
	List<Coil> findAll();
}
