package org.dimensinfin.poc.poccucumber.application.usecases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import org.dimensinfin.poc.poccucumber.domain.Coil;
import org.dimensinfin.poc.poccucumber.domain.CoilEntity;
import org.dimensinfin.poc.poccucumber.infrastructure.adapters.inbound.rest.ConverterFactory;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.14.0
 */
@Service
public class CoilServiceV2 {
	private final Map<UUID, CoilEntity> coils = new HashMap<>();

	public List<Coil> getCoils() {
		return this.coils.values().stream()
				.map( ConverterFactory::toCoil )
				.collect( Collectors.toUnmodifiableList() );
	}

	public CoilEntity createCoil( final @Valid Coil coilDto ) {
		final UUID newId = UUID.randomUUID();
		final CoilEntity entity = ConverterFactory.toCoilEntity( coilDto );
		this.coils.put( newId, entity );
		return entity;
	}
}
