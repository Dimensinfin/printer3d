package org.dimensinfin.poc.backend_pocws.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.dimensinfin.poc.backend_pocws.application.ports.PartAdapter;
import org.dimensinfin.poc.backend_pocws.application.ports.TopicAdapter;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part.PartAdapterImplementation;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.websockets.TopicAdapterImplementation;

@Configuration
public class AdapterConfiguration {
	@Bean
	public PartAdapter getPartAdapter() {
		return new PartAdapterImplementation();
	}

	@Bean
	public TopicAdapter getTopicAdapter() {
		return new TopicAdapterImplementation();
	}
}
