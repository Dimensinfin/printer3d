package org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dimensinfin.poc.backend_pocws.application.ports.PartAdapter;
import org.dimensinfin.poc.backend_pocws.domain.Part;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.converters.ConverterFactory;

public class PartAdapterImplementation implements PartAdapter {
	private final Map<UUID, PartEntity> parts = new HashMap<>();

	@Override
	public List<Part> getAll() {
		return this.parts.values().stream()
				.map( ConverterFactory::toPart )
				.toList();
	}

	@Override
	public Part save( final Part part ) {
		final PartEntity entity = ConverterFactory.toPartEntity( part );
		this.parts.put( entity.getId(), entity );
		return ConverterFactory.toPart( this.parts.get( entity.getId() ) );
	}
}
