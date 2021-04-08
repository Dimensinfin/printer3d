package org.dimensinfin.printer3d.backend.inventory.coil.rest.v2;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.dimensinfin.core.utility.DimObjects;
import org.dimensinfin.printer3d.backend.inventory.coil.converter.CoilEntityToCoilConverter;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.14.0
 */
@Service
public class CoilServiceV2 {
	private final CoilRepository coilRepository;
	private final CoilEntityToCoilConverter coilConverter;

	// - C O N S T R U C T O R S
	@Autowired
	public CoilServiceV2( @NotNull final CoilRepository coilRepository,
	                      @NotNull final CoilEntityToCoilConverter coilConverter ) {
		this.coilRepository = DimObjects.requireNonNull( coilRepository,
				MessageFormat.format( "Injectable component {0} not available.", "CoilRepository" )
		);
		this.coilConverter = coilConverter;
	}

	// - G E T T E R S   &   S E T T E R S
	public List<Coil> getCoils() {
		return this.coilRepository.findAll()
				.stream()
				.map( this.coilConverter::convert )
				.collect( Collectors.toList() );
	}
}
