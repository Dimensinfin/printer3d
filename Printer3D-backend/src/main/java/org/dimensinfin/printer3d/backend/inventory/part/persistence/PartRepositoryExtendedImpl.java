package org.dimensinfin.printer3d.backend.inventory.part.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class PartRepositoryExtendedImpl extends SimpleJpaRepository<PartEntity, UUID> implements PartRepositoryExtended {
	// - C O N S T R U C T O R S
	public PartRepositoryExtendedImpl( final JpaEntityInformation<PartEntity, ?> entityInformation, final EntityManager entityManager ) {
		super( entityInformation, entityManager );
	}

	public PartRepositoryExtendedImpl( final Class<PartEntity> domainClass, final EntityManager em ) {
		super( domainClass, em );
	}

	public void decrementStock( final UUID recordId, final int quantity ) {
		final Optional<PartEntity> partOpt = this.findById( recordId );
		partOpt.ifPresent( partEntity -> {
			partEntity.decrementStock( quantity );
			this.save( partEntity );
		} );
	}

	@Override
	public List<PartEntity> findByLabel( final String label ) {
		return this.findAll()
				.stream()
				.filter( entity -> entity.getLabel().equalsIgnoreCase( label ) )
				.collect( Collectors.toList() );
	}
}
