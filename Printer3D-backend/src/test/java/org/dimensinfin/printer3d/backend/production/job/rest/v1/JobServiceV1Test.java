package org.dimensinfin.printer3d.backend.production.job.rest.v1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;
import org.dimensinfin.printer3d.client.production.domain.Job;

public class JobServiceV1Test {

	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository );
		Assertions.assertNotNull( jobServiceV1 );
	}

	@Test
	public void getPendingJobs() {
		// Given
		final Part part = Mockito.mock( Part.class );
		final List<Part> partList = new ArrayList<>();
		partList.add( part );
		partList.add( part );
		// When
		Mockito.when( this.partRepository.findAll() ).thenReturn( partList );
		Mockito.when( part.getStockAvailable() ).thenReturn( 1 );
		Mockito.when( part.getStockLevel() ).thenReturn( 2 );
		// Test
		final JobServiceV1 jobServiceV1 = new JobServiceV1( this.partRepository );
		final List<Job> obtained = jobServiceV1.getPendingJobs();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertEquals( 2, obtained.size() );
	}
}
