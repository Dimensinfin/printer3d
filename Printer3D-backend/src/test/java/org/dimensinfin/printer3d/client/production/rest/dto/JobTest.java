package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;

public class JobTest {
	@Test
	public void buildContract() {
		final Part part = Mockito.mock( Part.class );
		final Job job = new Job.Builder().withPart( part ).build();
		Assertions.assertNotNull( job );
	}

	@Test
	public void buildFailure() {
		final Part part = Mockito.mock( Part.class );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Job.Builder().withPart( null ).build();
		} );
		Assertions.assertThrows( NullPointerException.class, () -> {
			new Job.Builder().build();
		} );
	}

	@Test
	public void getterContract() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final Job job = new Job.Builder().withPart( part ).build();
		// When
		Mockito.when( part.getId() ).thenReturn( UUID.randomUUID() );
		// Assertions
		Assertions.assertNotNull( job.getId() );
		Assertions.assertEquals( part.getId().toString(), job.getPart().getId().toString() );
	}
}
