package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MachineListV2 {
	private List<MachineV2> machines = new ArrayList<>();

	// - C O N S T R U C T O R S
	private MachineListV2() {
	}

	public List<MachineV2> getMachines() {
		return this.machines;
	}

	// - B U I L D E R
	public static class Builder {
		private final MachineListV2 onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new MachineListV2();
		}

		public MachineListV2 build() {
			return this.onConstruction;
		}

		public MachineListV2.Builder withMachines( final List<MachineV2> machines ) {
			this.onConstruction.machines = Objects.requireNonNull( (machines) );
			return this;
		}
	}
}

