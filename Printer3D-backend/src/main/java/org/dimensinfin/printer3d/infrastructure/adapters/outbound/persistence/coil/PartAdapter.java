package org.dimensinfin.printer3d.infrastructure.adapters.outbound.persistence.coil;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.application.ports.outbound.CoilPort;
import org.dimensinfin.printer3d.application.ports.outbound.PartPort;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public class PartAdapter implements PartPort {
	private final CoilPort coilPort;
	private final PartRepository partRepository;
	private final PartEntityToPartConverter partConverter = new PartEntityToPartConverter();

	public PartAdapter( final @NotNull CoilPort coilPort,
	                    final @NotNull PartRepository partRepository ) {
		this.coilPort = coilPort;
		this.partRepository = partRepository;
	}

	@Override
	public List<Part> findAll() {
		// Get only the coils that are active that are the ones to be matched from Parts
		final List<Coil> coils = this.coilPort.findAll()
				.stream()
				.filter( Coil::getActive )
				.collect( Collectors.toList() );
		return this.partRepository.findAll()
				.stream()
				.map( partEntity -> new PartEntityToPartConverter(
						!this.findColorCoil( coils, partEntity.getMaterial(), partEntity.getColor() ) )
						.convert( partEntity ) )
				.collect( Collectors.toList() );
	}

	/**
	 * Search the list of coils for a matching active coil with the required color and material.
	 *
	 * @param coils    The current active list of Coils.
	 * @param material the target Material.
	 * @param color    the target Color.
	 * @return true only if there is an active coil with the same material and color.
	 */
	private boolean findColorCoil( final List<Coil> coils, final String material, final String color ) {
		for (final Coil coil : coils)
			if ( (coil.getMaterial().equals( material )) && (coil.getColor().equals( color )) )
				return true;
		return false;
	}
}
