package org.dimensinfin.printer3d.backend.production.request.converter;

public class RequestEntityToRequestConverter {
	private RequestEntityToRequestConverter() {}

	// - B U I L D E R
	public static class Builder {
		private final RequestEntityToRequestConverter onConstruction;

		public Builder() {
			this.onConstruction = new RequestEntityToRequestConverter();
		}

		public RequestEntityToRequestConverter build() {
			return this.onConstruction;
		}
	}
}
