package org.dimensinfin.printer3d.infrastructure.config;

import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.dimensinfin.printer3d.application.ports.inbound.InventoryV2UseCases;
import org.dimensinfin.printer3d.application.ports.outbound.PartPort;
import org.dimensinfin.printer3d.infrastructure.services.InventoryV1UseCasesImplementation;

@Configuration
public class ApplicationConfig {
	@Bean
	public InventoryV2UseCases getInventoryV2UseCases( final @NotNull PartPort partPort ) {
		return new InventoryV1UseCasesImplementation( partPort );
	}
}
