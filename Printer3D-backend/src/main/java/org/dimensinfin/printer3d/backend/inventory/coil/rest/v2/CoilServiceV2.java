package org.dimensinfin.printer3d.backend.inventory.coil.rest.v2;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import org.dimensinfin.core.utility.DimObjects;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.14.0
 */
@Service
public class CoilServiceV2 {
	private final CoilRepository coilRepository;

	// - C O N S T R U C T O R S
	public CoilServiceV2( final CoilRepository coilRepository ) {
		this.coilRepository = DimObjects.requireNonNull( coilRepository,
				MessageFormat.format( "Injectable component {0} not available.", "CoilRepository" )
		);
	}

	// - G E T T E R S   &   S E T T E R S
	public List<Coil> getCoils() {
		return this.coilRepository.findAll();
	}
}
