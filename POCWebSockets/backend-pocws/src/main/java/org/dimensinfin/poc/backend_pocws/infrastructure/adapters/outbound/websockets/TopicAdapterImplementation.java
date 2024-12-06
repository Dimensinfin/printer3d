package org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.dimensinfin.poc.backend_pocws.application.ports.TopicAdapter;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.converters.ConverterFactory;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part.PartEntity;
import org.dimensinfin.poc.generated.infrastructure.ports.inbound.domain.PartDto;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TopicAdapterImplementation implements TopicAdapter {
	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	public TopicAdapterImplementation( @NotNull final SimpMessagingTemplate messagingTemplate ) {this.messagingTemplate = messagingTemplate;}

	@Override
	public PartDto createPart( final PartEntity entity ) {
		log.info( "CreatePart topic" );
		messagingTemplate.convertAndSend( "/topic/parts", entity );
		return ConverterFactory.toPartDto( entity );
	}

	public PartDto updatePart( final PartEntity entity ) {
		log.info( "UpdatePart topic" );
		return ConverterFactory.toPartDto( entity );
	}
}
