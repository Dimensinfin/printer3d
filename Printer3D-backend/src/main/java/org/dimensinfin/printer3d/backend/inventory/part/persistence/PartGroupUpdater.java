package org.dimensinfin.printer3d.backend.inventory.part.persistence;

public class PartGroupUpdater {
	private PartGroupUpdater() {}

	// - B U I L D E R
	public static class Builder {
		private final PartGroupUpdater onConstruction;

		public Builder() {
			this.onConstruction = new PartGroupUpdater();
		}

		public PartGroupUpdater build() {
			return this.onConstruction;
		}
	}
}
