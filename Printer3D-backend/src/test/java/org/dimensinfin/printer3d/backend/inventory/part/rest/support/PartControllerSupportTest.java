package org.dimensinfin.printer3d.backend.inventory.part.rest.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.common.client.rest.CountResponse;

public class PartControllerSupportTest {

	private PartServiceSupport partServiceSupport;

	@BeforeEach
	public void beforeEach() {
		this.partServiceSupport = Mockito.mock( PartServiceSupport.class );
	}

	@Test
	public void constructorContract() {
		final PartControllerSupport partControllerSupport = new PartControllerSupport( this.partServiceSupport );
		Assertions.assertNotNull( partControllerSupport );
	}

	@Test
	public void deleteAllParts() {
		// Given
		final Integer TEST_RECORD_COUNT = 4;
		final CountResponse countResponse = new CountResponse.Builder().withRecords( TEST_RECORD_COUNT ).build();
		// When
		Mockito.when( this.partServiceSupport.deleteAllParts() ).thenReturn( countResponse );
		// Test
		final PartControllerSupport partControllerSupport = new PartControllerSupport( this.partServiceSupport );
		final ResponseEntity<CountResponse> obtained = partControllerSupport.deleteAllParts();
		// Assertions
		Assertions.assertNotNull( obtained );
		Assertions.assertNotNull( obtained.getBody() );
		Assertions.assertEquals( TEST_RECORD_COUNT, obtained.getBody().getRecords() );
	}
}
