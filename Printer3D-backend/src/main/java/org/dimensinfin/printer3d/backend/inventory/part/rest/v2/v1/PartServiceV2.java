package org.dimensinfin.printer3d.backend.inventory.part.rest.v2.v1;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.inventory.part.rest.v1.PartServiceV1;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.6.0
 */
@Service
public class PartServiceV2 extends PartServiceV1 {
	private final CoilRepository coilRepository;

	// - C O N S T R U C T O R S
	@Autowired
	public PartServiceV2( @NotNull final PartRepository partRepository,
	                      @NotNull final PartEntityToPartConverter partConverter,
	                      @NotNull final CoilRepository coilRepository ) {
		super( partRepository, partConverter );
		this.coilRepository = coilRepository;
	}

	// - G E T T E R S   &   S E T T E R S
	public List<Part> getParts() {
		final List<Coil> coils = this.coilRepository.findAll()
				.stream()
				.filter( coil -> coil.getActive() )
				.collect( Collectors.toList() );
		return this.partRepository.findAll()
				.stream()
				.filter( part -> this.findColorCoil( coils, part.getMaterial(), part.getColor() ) )
				.map( this.partConverter::convert )
				.collect( Collectors.toList() );
	}

	/**
	 * Search the list of coils for a matching active coil with the required color and material.
	 *
	 * @param coils    The current active list of Coils.
	 * @param material the target Material.
	 * @param color    the target Color.
	 * @return true only if there is an active coil wuth the same material and color.
	 */
	private boolean findColorCoil( final List<Coil> coils, final String material, final String color ) {
		for (final Coil coil : coils)
			if ((coil.getMaterial().equals( material )) && (coil.getColor().equals( color )))
				return true;
		return false;
	}
}
