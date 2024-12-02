package org.dimensinfin.poc.backend_pocws.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.dimensinfin.poc.backend_pocws.application.ports.PartAdapter;
import org.dimensinfin.poc.backend_pocws.infrastructure.adapters.outbound.part.PartAdapterImplementation;

@Configuration
public class AdapterConfiguration {
	@Bean
	public PartAdapter getPartAdapter() {
		return new PartAdapterImplementation();
	}
}
