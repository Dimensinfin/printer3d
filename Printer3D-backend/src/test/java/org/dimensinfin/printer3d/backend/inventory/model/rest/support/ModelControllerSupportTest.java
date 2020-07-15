package org.dimensinfin.printer3d.backend.inventory.model.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CounterResponse;
import org.dimensinfin.printer3d.backend.inventory.model.persistence.ModelRepository;

public class ModelControllerSupportTest {

	private ModelRepository modelRepository;

	@BeforeEach
	public void beforeEach() {
		this.modelRepository = Mockito.mock( ModelRepository.class );
	}

	@Test
	public void constructorContract() {
		final ModelControllerSupport modelControllerSupport = new ModelControllerSupport( this.modelRepository );
		Assertions.assertNotNull( modelControllerSupport );
	}

	@Test
	public void deleteAllModels() {
		// Given
		final int TEST_RECORD_COUNT = 4;
		final CounterResponse counterResponse = new CounterResponse.Builder().withRecords( TEST_RECORD_COUNT ).build();
		// When
		Mockito.when( this.modelRepository.count() ).thenReturn( (long) TEST_RECORD_COUNT );
		// Test
		final ModelControllerSupport modelControllerSupport = new ModelControllerSupport( this.modelRepository );
		final ResponseEntity<CounterResponse> obtained = modelControllerSupport.deleteAllModels();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( TEST_RECORD_COUNT, obtained.getBody().getRecords() );
	}

	@Test
	public void deleteAllModelsException() {
		// When
		Mockito.when( this.modelRepository.count() ).thenThrow( RuntimeException.class );
		// Exceptions
		Assertions.assertThrows( RepositoryException.class, () -> {
			final ModelControllerSupport modelControllerSupport = new ModelControllerSupport( this.modelRepository );
			modelControllerSupport.deleteAllModels();
		} );
	}
}
