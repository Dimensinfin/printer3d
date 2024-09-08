package org.dimensinfin.printer3d.application.usecases;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.application.ports.outbound.PartPort;
import org.dimensinfin.printer3d.backend.inventory.part.converter.PartEntityToPartConverter;
import org.dimensinfin.printer3d.backend.support.TestInstanceGenerator;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

class GetPartsUseCaseTest {
	private PartPort partPort;

	@BeforeEach
	public void beforeEach() {
		this.partPort = Mockito.mock( PartPort.class );
	}

	@Test
	void execute() {
		// Given
		final Part part = new PartEntityToPartConverter().convert( new TestInstanceGenerator().getPartEntity() );
		final List<Part> parts = new ArrayList<>();
		parts.add( part );
		parts.add( part );
		parts.add( part );
		parts.add( part );
		parts.add( part );
		// When
		Mockito.when( this.partPort.findAll() ).thenReturn( parts );
		final List<Part> sut = new GetPartsUseCase( this.partPort ).execute();
		// Then
		Assertions.assertEquals( 5, sut.size() );
	}
}