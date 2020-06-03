package org.dimensinfin.printer3d.backend.inventory.machine.serializer;

public class MachineSerializer {
	private MachineSerializer() {}

	// - B U I L D E R
	public static class Builder {
		private MachineSerializer onConstruction;

		public Builder() {
			this.onConstruction = new MachineSerializer();
		}

		public MachineSerializer build() {
			return this.onConstruction;
		}
	}
}
