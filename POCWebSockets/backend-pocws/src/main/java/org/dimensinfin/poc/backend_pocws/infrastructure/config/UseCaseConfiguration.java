package org.dimensinfin.poc.backend_pocws.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.dimensinfin.poc.backend_pocws.application.ports.PartAdapter;
import org.dimensinfin.poc.backend_pocws.application.usecases.GetPartsUseCase;

@Configuration
public class UseCaseConfiguration {
	@Bean
	public GetPartsUseCase getPartsUseCase( final PartAdapter partAdapter ) {
		return new GetPartsUseCase( partAdapter );
	}
}
