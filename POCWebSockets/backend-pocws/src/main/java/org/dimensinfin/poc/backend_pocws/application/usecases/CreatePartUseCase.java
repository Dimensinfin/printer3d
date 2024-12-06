package org.dimensinfin.poc.backend_pocws.application.usecases;

import org.dimensinfin.poc.backend_pocws.application.ports.PartAdapter;
import org.dimensinfin.poc.backend_pocws.domain.Part;

import jakarta.validation.constraints.NotNull;

public class CreatePartUseCase {
	private final PartAdapter partAdapter;

	public CreatePartUseCase( @NotNull final PartAdapter partAdapter ) {this.partAdapter = partAdapter;}

	public Part execute( final Part part ) {
		return this.partAdapter.save( part );
	}
}
