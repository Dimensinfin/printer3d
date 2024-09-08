package org.dimensinfin.printer3d.application.ports.outbound;

import java.util.List;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public interface PartPort {
	List<Part> findAll();
}
