package org.dimensinfin.poc.backend_pocws.application.ports;

import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part.PartEntity;
import org.dimensinfin.poc.generated.infrastructure.ports.inbound.domain.PartDto;

public interface TopicAdapter {
	PartDto createPart( final PartEntity entity );
}
