package org.dimensinfin.printer3d.backend.inventory.machine.rest.v2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.dimensinfin.printer3d.backend.inventory.machine.persistence.MachineRepository;
import org.dimensinfin.printer3d.backend.inventory.part.persistence.PartRepository;

public class MachineServiceV2Test {

	private MachineRepository machineRepository;
	private PartRepository partRepository;

	@BeforeEach
	public void beforeEach() {
		this.machineRepository = Mockito.mock( MachineRepository.class );
		this.partRepository = Mockito.mock( PartRepository.class );
	}

	@Test
	public void constructorContract() {
		final MachineServiceV2 machineServiceV2 = new MachineServiceV2( this.machineRepository, this.partRepository );
		Assertions.assertNotNull( machineServiceV2 );
	}

	@Test
	public void getMachines() {
	}
}
