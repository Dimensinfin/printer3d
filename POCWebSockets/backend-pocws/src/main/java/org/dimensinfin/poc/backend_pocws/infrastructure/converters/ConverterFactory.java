package org.dimensinfin.poc.backend_pocws.infrastructure.converters;

import java.util.UUID;

import org.dimensinfin.poc.backend_pocws.domain.Part;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part.PartEntity;
import org.dimensinfin.poc.generated.infrastructure.ports.inbound.domain.PartDto;

public class ConverterFactory {
	public static Part toPart( final PartDto partDto ) {
		return Part.builder()
				.id( partDto.getId() )
				.name( partDto.getName() )
				.weight( partDto.getWeight() )
				.build();
	}

	public static Part toPart( final PartEntity partEntity ) {
		return Part.builder()
				.id( UUID.fromString( partEntity.getId() ) )
				.name( partEntity.getName() )
				.weight( partEntity.getWeight() )
				.build();
	}

	public static PartDto toPartDto( final Part part ) {
		return PartDto.builder()
				.id( part.getId() )
				.name( part.getName() )
				.weight( part.getWeight() )
				.build();
	}
}
