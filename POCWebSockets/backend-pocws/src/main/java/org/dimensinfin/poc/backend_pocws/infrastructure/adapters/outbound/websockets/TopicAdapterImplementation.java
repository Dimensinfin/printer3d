package org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.websockets;

import org.springframework.messaging.handler.annotation.SendTo;

import org.dimensinfin.poc.backend_pocws.application.ports.TopicAdapter;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.converters.ConverterFactory;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part.PartEntity;
import org.dimensinfin.poc.generated.infrastructure.ports.inbound.domain.PartDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TopicAdapterImplementation implements TopicAdapter {
	@SendTo("/topic/parts")
	@Override
	public PartDto createPart( final PartEntity entity ) {
		log.info( "CreatePart topic" );
		return ConverterFactory.toPartDto( entity );
	}

	@SendTo("/topic/parts")
	public PartDto updatePart( final PartEntity entity ) {
		log.info( "UpdatePart topic" );
		return ConverterFactory.toPartDto( entity );
	}
}
