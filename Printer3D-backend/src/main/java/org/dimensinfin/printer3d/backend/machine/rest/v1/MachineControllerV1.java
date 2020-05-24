package org.dimensinfin.printer3d.backend.machine.rest.v1;

public class MachineControllerV1 {
	private MachineControllerV1() {}

	// - B U I L D E R
	public static class Builder {
		private MachineControllerV1 onConstruction;

		public Builder() {
			this.onConstruction = new MachineControllerV1();
		}

		public MachineControllerV1 build() {
			return this.onConstruction;
		}
	}
}
