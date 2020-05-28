package org.dimensinfin.printer3d.backend.steps;

public class GivenANewUUIDNamedAsID {
	private GivenANewUUIDNamedAsID() {}

	// - B U I L D E R
	public static class Builder {
		private GivenANewUUIDNamedAsID onConstruction;

		public Builder() {
			this.onConstruction = new GivenANewUUIDNamedAsID();
		}

		public GivenANewUUIDNamedAsID build() {
			return this.onConstruction;
		}
	}
}
