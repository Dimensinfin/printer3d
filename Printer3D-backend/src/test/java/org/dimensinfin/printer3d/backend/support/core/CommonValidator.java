package org.dimensinfin.printer3d.backend.support.core;

public class CommonValidator {
	private CommonValidator() {}

	// - B U I L D E R
	public static class Builder {
		private CommonValidator onConstruction;

		public Builder() {
			this.onConstruction = new CommonValidator();
		}

		public CommonValidator build() {
			return this.onConstruction;
		}
	}
}
