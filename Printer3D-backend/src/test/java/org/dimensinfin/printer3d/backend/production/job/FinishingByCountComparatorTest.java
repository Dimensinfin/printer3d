package org.dimensinfin.printer3d.backend.production.job;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.production.domain.FinishingContainer;
import org.dimensinfin.printer3d.client.production.rest.dto.Job;

public class FinishingByCountComparatorTest {

	@Test
	public void compareEmpty() {
		// Given
		final FinishingContainer c1 = new FinishingContainer.Builder().build();
		final FinishingContainer c2 = new FinishingContainer.Builder().build();
		// Assertions
		Assertions.assertEquals( 0, new FinishingByCountComparator().compare( c1, c2 ) );
	}
	@Test
	public void compareEqual() {
		// Given
		final Job job = Mockito.mock( Job.class);
		final FinishingContainer c1 = new FinishingContainer.Builder().build();
		c1.addJob( job );
		final FinishingContainer c2 = new FinishingContainer.Builder().build();
		c2.addJob( job );
		// Assertions
		Assertions.assertEquals( 0, new FinishingByCountComparator().compare( c1, c2 ) );
	}
	@Test
	public void compareMore() {
		// Given
		final Job job = Mockito.mock( Job.class);
		final FinishingContainer c1 = new FinishingContainer.Builder().build();
		c1.addJob( job );
		c1.addJob( job );
		final FinishingContainer c2 = new FinishingContainer.Builder().build();
		c2.addJob( job );
		// Assertions
		Assertions.assertEquals( -1, new FinishingByCountComparator().compare( c1, c2 ) );
	}
	@Test
	public void compareLess() {
		// Given
		final Job job = Mockito.mock( Job.class);
		final FinishingContainer c1 = new FinishingContainer.Builder().build();
		c1.addJob( job );
		final FinishingContainer c2 = new FinishingContainer.Builder().build();
		c2.addJob( job );
		c2.addJob( job );
		// Assertions
		Assertions.assertEquals( 1, new FinishingByCountComparator().compare( c1, c2 ) );
	}
}
