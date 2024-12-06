package org.dimensinfin.poc.backend_pocws.application.usecases;

import java.util.List;

import org.dimensinfin.poc.backend_pocws.application.ports.PartAdapter;
import org.dimensinfin.poc.backend_pocws.domain.Part;

import jakarta.validation.constraints.NotNull;

public class GetPartsUseCase {
	private final PartAdapter partAdapter;

	public GetPartsUseCase( @NotNull final PartAdapter partAdapter ) {this.partAdapter = partAdapter;}

	public List<Part> execute() {
		return this.partAdapter.getAll();
	}
}
