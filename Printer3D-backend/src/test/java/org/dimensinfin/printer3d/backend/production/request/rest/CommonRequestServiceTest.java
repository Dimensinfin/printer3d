package org.dimensinfin.printer3d.backend.production.request.rest;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartEntity;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.backend.production.request.persistence.RequestsRepositoryV2;

public class CommonRequestServiceTest {
	private PartRepository partRepository;
	private ModelRepository modelRepository;
	private RequestsRepositoryV2 requestsRepositoryV2;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
		this.modelRepository = Mockito.mock( ModelRepository.class );
		this.requestsRepositoryV2 = Mockito.mock( RequestsRepositoryV2.class );
	}

	@Test
	public void constructorContract() {
		final CommonRequestService commonRequestService = new CommonRequestService( this.partRepository, this.modelRepository,
				this.requestsRepositoryV2 );
		Assertions.assertNotNull( commonRequestService );
	}

	@Test
	public void decrementStock() {
		// Given
		final UUID recordId = UUID.randomUUID();
		final Integer quantity = 10;
		final PartEntity part = Mockito.mock( PartEntity.class );
		// When
		Mockito.when( this.partRepository.findById( Mockito.any( UUID.class ) ) ).thenReturn( Optional.of( part ) );
		// Test
		final CommonRequestService commonRequestService = new CommonRequestService( this.partRepository, this.modelRepository,
				this.requestsRepositoryV2 );
		commonRequestService.decrementStock( recordId, quantity );
		Mockito.verify( this.partRepository, Mockito.times( 1 ) ).save( Mockito.any( PartEntity.class ) );
	}
}