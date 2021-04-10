package org.dimensinfin.printer3d.backend.core.scheduler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.coil.rest.v2.CoilServiceV2;

public class RemoveEmptyCoilsJobTest {
	@Test
	public void constructorContract() {
		final CoilServiceV2 coilServiceV2 = Mockito.mock( CoilServiceV2.class );
		final RemoveEmptyCoilsJob removeEmptyCoilsJob = new RemoveEmptyCoilsJob( coilServiceV2 );
		Assertions.assertNotNull( removeEmptyCoilsJob );
	}

	@Test
	public void process() {
		// Given
		final CoilServiceV2 coilServiceV2 = Mockito.mock( CoilServiceV2.class );
		// When
		Mockito.when( coilServiceV2.removeExpiredCoils() ).thenReturn( 2 );
		// Test
		final RemoveEmptyCoilsJob removeEmptyCoilsJob = new RemoveEmptyCoilsJob( coilServiceV2 );
		removeEmptyCoilsJob.process();
		// Assertions
		Assertions.assertNotNull( removeEmptyCoilsJob );
	}
}