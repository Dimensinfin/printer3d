package org.dimensinfin.printer3d.application.usecases;

import java.util.List;

import org.dimensinfin.printer3d.application.ports.outbound.PartPort;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public class GetPartsUseCase {
	private final PartPort partPort;

	public GetPartsUseCase( final PartPort partPort) {
		this.partPort = partPort;
	}

	/**
	 * Get the list of <code>Parts</code> that are defined at the <code>PartEntity</code> repository.
	 *
	 * @return updated list of <code>Parts</code> with the <code>unavailable</code> flag updated.
	 */
	public List<Part> execute() {
		return this.partPort.findAll();
	}
}
