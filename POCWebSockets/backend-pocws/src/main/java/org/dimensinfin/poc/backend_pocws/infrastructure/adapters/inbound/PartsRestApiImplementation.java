package org.dimensinfin.poc.backend_pocws.infrastructure.adapters.inbound;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.poc.backend_pocws.application.usecases.GetPartsUseCase;
import org.dimensinfin.poc.backend_pocws.infrastructure.converters.ConverterFactory;
import org.dimensinfin.poc.generated.infrastructure.ports.inbound.api.PartRestApi;
import org.dimensinfin.poc.generated.infrastructure.ports.inbound.domain.PartDto;

import jakarta.validation.constraints.NotNull;

@RestController
public class PartsRestApiImplementation implements PartRestApi {
	private final GetPartsUseCase getPartsUseCase;

	public PartsRestApiImplementation( @NotNull final GetPartsUseCase getPartsUseCase ) {this.getPartsUseCase = getPartsUseCase;}

	@Override
	public ResponseEntity<PartDto> createPart() {
		return null;
	}

	@Override
	public ResponseEntity<List<PartDto>> listParts() {
		return ResponseEntity.ok( this.getPartsUseCase.execute().stream()
				.map( ConverterFactory::toPartDto )
				.toList()
		);
	}
}
