package org.dimensinfin.printer3d.backend.inventory.finishing.rest.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.domain.Finishing;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;

@Service
public class FinishingsServiceV1 {
	private final CoilRepository coilRepository;

	// - C O N S T R U C T O R S
	public FinishingsServiceV1( final CoilRepository coilRepository ) {
		this.coilRepository = Objects.requireNonNull( coilRepository );
	}

	// - G E T T E R S   &   S E T T E R S

	/**
	 * Needs to read all the active rolls and with all those records generate a list of materials. For each of the materials the service should
	 * record the distinct colors and then place all this data on a response.
	 * Colors by each type should be alphabetically ordered and the types should also be ordered alphabetically.
	 *
	 * @return the Finishings response with the list of color by each available material type.
	 */
	public FinishingsResponse getFinishings() {
		final Map<String, Set<String>> finishingsOnConstruction = new HashMap<>();
		this.coilRepository.findAll( Sort.by( Sort.Direction.ASC, "material" ) )
				.stream()
				.forEach( coil -> {
					// Check if there is a material.
					final String material = coil.getMaterial();
					if (!finishingsOnConstruction.containsKey( material ))
						finishingsOnConstruction.put( material, new HashSet<>() );
					final Set<String> hit = finishingsOnConstruction.get( material );
					// Add the color to this material set
					hit.add( coil.getColor() );
				} );
		return this.composeFittingsResponse( finishingsOnConstruction );
	}

	private FinishingsResponse composeFittingsResponse( final Map<String, Set<String>> finishingsOnConstruction ) {
		final FinishingsResponse finishingsResponse = new FinishingsResponse.Builder().build();
		for (String key : finishingsOnConstruction.keySet()) {
			final Set<String> colors = finishingsOnConstruction.get( key );
			final ArrayList<String> col = new ArrayList<>( colors );
			Collections.sort( col );
			final Finishing material = new Finishing.Builder()
					.withMaterial( key )
					.withColors( col )
					.build();
			finishingsResponse.addFinishing( material );
		}
		return finishingsResponse;
	}
}
