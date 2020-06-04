package org.dimensinfin.printer3d.client.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MachineList {
	private List<Machine> machines = new ArrayList<>();

	// - C O N S T R U C T O R S
	private MachineList() {}

	// - G E T T E R S   &   S E T T E R S
	public List<Machine> getMachines() {
		return machines;
	}

	// - B U I L D E R
	public static class Builder {
		private MachineList onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new MachineList();
		}

		public MachineList build() {
			Objects.requireNonNull( this.onConstruction.machines );
			return this.onConstruction;
		}

		public MachineList.Builder withMachines( final List<Machine> machines ) {
			this.onConstruction.machines = Objects.requireNonNull( machines );
			return this;
		}
	}
}
