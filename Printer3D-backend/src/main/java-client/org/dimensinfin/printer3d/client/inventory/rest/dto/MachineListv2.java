package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MachineListv2 {
	private List<Machinev2> machines = new ArrayList<Machinev2>();

	// - C O N S T R U C T O R S
	private MachineListv2() {
	}

	public List<Machinev2> getMachines() {
		return this.machines;
	}

	// - B U I L D E R
	public static class Builder {
		private final MachineListv2 onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new MachineListv2();
		}

		public MachineListv2 build() {
			return this.onConstruction;
		}

		public MachineListv2.Builder withMachines( final List<Machinev2> machines ) {
			this.onConstruction.machines = Objects.requireNonNull( (machines) );
			return this;
		}
	}
}

