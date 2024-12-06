package org.dimensinfin.poc.backend_pocws.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.dimensinfin.poc.backend_pocws.application.ports.PartAdapter;
import org.dimensinfin.poc.backend_pocws.application.ports.TopicAdapter;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part.PartAdapterImplementation;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.websockets.TopicAdapterImplementation;

import jakarta.validation.constraints.NotNull;

@Configuration
public class AdapterConfig {
	@Bean
	public PartAdapter getPartAdapter( final TopicAdapter topicAdapter ) {
		return new PartAdapterImplementation( topicAdapter );
	}

	@Bean
	public TopicAdapter getTopicAdapter( @NotNull final SimpMessagingTemplate messagingTemplate ) {
		return new TopicAdapterImplementation( messagingTemplate );
	}
}
