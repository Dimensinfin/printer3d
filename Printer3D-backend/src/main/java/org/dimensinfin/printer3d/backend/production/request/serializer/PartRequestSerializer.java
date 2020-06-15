package org.dimensinfin.printer3d.backend.production.request.serializer;

public class PartRequestSerializer {
	private PartRequestSerializer() {}

	// - B U I L D E R
	public static class Builder {
		private final PartRequestSerializer onConstruction;

		public Builder() {
			this.onConstruction = new PartRequestSerializer();
		}

		public PartRequestSerializer build() {
			return this.onConstruction;
		}
	}
}
