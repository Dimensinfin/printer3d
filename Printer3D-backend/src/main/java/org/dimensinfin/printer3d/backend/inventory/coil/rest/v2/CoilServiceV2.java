package org.dimensinfin.printer3d.backend.inventory.coil.rest.v2;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.dimensinfin.core.utility.DimObjects;
import org.dimensinfin.logging.LogWrapper;
import org.dimensinfin.printer3d.backend.inventory.coil.converter.CoilEntityToCoilConverter;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.CoilRepository;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Coil;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.14.0
 */
@Service
public class CoilServiceV2 {
	private static final String REMOVE_COIL_MESSAGE = "Removing Coil after expiration: {0} - {1}. Expiration time: {2}";
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

	public int removeExpiredCoils() {
		final AtomicInteger counter = new AtomicInteger( 0 );
		this.coilRepository.findAll()
				.stream()
				.filter( coilEntity -> null != coilEntity.getDestructionTime() )
				.forEach( coilEntity -> {
					if (Instant.now().isAfter( coilEntity.getDestructionTime() )) {
						LogWrapper.info( MessageFormat.format( REMOVE_COIL_MESSAGE,
								coilEntity.getId(),
								coilEntity.getMaterial() + "/" + coilEntity.getColor(),
								coilEntity.getDestructionTime().toString() )
						);
						this.coilRepository.delete( coilEntity );
						counter.incrementAndGet();
					}
				} );
		return counter.get();
	}
}
