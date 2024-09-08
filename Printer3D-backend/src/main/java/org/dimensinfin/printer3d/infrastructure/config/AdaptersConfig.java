package org.dimensinfin.printer3d.infrastructure.config;

import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.dimensinfin.printer3d.application.ports.outbound.CoilPort;
import org.dimensinfin.printer3d.application.ports.outbound.PartPort;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.infrastructure.adapters.outbound.persistence.coil.CoilAdapter;
import org.dimensinfin.printer3d.infrastructure.adapters.outbound.persistence.part.PartAdapter;

@Configuration
public class AdaptersConfig {
	@Bean
	public CoilPort getCoilPort( final @NotNull CoilRepository coilRepository ) {
		return new CoilAdapter( coilRepository );
	}

	@Bean
	public PartPort getPartPort( final @NotNull CoilPort coilPort,
	                             final @NotNull PartRepository partRepository ) {
		return new PartAdapter( coilPort, partRepository );
	}
}
