package org.dimensinfin.printer3d.infrastructure.services;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.printer3d.application.ports.inbound.InventoryV2UseCases;
import org.dimensinfin.printer3d.application.ports.outbound.PartPort;
import org.dimensinfin.printer3d.application.usecases.GetPartsUseCase;

public class InventoryV1UseCasesImplementation implements InventoryV2UseCases {
	private final PartPort partPort;

	@Autowired
	public InventoryV1UseCasesImplementation( final @NotNull PartPort partPort ) {this.partPort = partPort;}

	public GetPartsUseCase getPartsUseCaseV2() {
		return new GetPartsUseCase( this.partPort );
	}
}
