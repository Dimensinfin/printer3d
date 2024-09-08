package org.dimensinfin.printer3d.infrastructure.adapters.outbound.persistence.coil;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.application.ports.outbound.CoilPort;
import org.dimensinfin.printer3d.backend.inventory.coil.converter.CoilEntityToCoilConverter;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

public class CoilAdapter implements CoilPort {
	private final CoilRepository coilRepository;
	private final CoilEntityToCoilConverter coilConverter = new CoilEntityToCoilConverter();

	public CoilAdapter( @NotNull final CoilRepository coilRepository ) {this.coilRepository = coilRepository;}

	/**
	 * Gets all the <code>CoilEntity</code> stored at the repository and converts them to the business <code>Coil</code> model.
	 *
	 * @return a list of all the coil entities converted to <code>Coil</code> models.
	 */
	@Override
	public List<Coil> findAll() {
		return this.coilRepository.findAll()
				.stream()
				.map( coilEntity ->
						this.coilConverter.convert( coilEntity )
				)
				.collect( Collectors.toList() );
	}
}
