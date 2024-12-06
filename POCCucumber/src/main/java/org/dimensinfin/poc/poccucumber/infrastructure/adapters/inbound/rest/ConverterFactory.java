package org.dimensinfin.poc.poccucumber.infrastructure.adapters.inbound.rest;

import java.util.UUID;

import org.dimensinfin.poc.poccucumber.domain.Coil;
import org.dimensinfin.poc.poccucumber.domain.CoilEntity;

public class ConverterFactory {
	public static CoilEntity toCoilEntity( final Coil CoilDto ) {
		return CoilEntity.builder()
				.id( UUID.randomUUID() )
				.name( CoilDto.getName() )
				.build();
	}

	public static Coil toCoil( final CoilEntity CoilEntity ) {
		return Coil.builder()
				.name( CoilEntity.getName() )
				.build();
	}
}
