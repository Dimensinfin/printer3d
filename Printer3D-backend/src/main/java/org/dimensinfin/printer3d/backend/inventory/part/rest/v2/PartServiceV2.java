package org.dimensinfin.printer3d.backend.inventory.part.rest.v2;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.coil.converter.CoilEntityToCoilConverter;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilEntity;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.6.0
 */
@Service
public class PartServiceV2 {
	private final PartRepository partRepository;
	private final CoilRepository coilRepository;
	private final CoilEntityToCoilConverter coilConverter;

	// - C O N S T R U C T O R S
	@Autowired
	public PartServiceV2( @NotNull final PartRepository partRepository,
	                      @NotNull final CoilRepository coilRepository,
	                      @NotNull final CoilEntityToCoilConverter coilConverter ) {
		this.partRepository = partRepository;
		this.coilRepository = coilRepository;
		this.coilConverter = coilConverter;
	}

	// - G E T T E R S   &   S E T T E R S
	public List<Part> getPartsV2() {
		final List<Coil> coils = this.coilRepository.findAll()
				.stream()
				.filter( CoilEntity::getActive )
				.map( coilEntity -> {
					LogWrapper.info( coilEntity.toString() );
					return this.coilConverter.convert( coilEntity );

				} )
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
			if ((coil.getMaterial().equals( material )) && (coil.getColor().equals( color )))
				return true;
		return false;
	}
}
