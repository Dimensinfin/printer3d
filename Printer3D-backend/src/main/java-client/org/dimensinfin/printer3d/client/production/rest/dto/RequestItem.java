package org.dimensinfin.printer3d.client.production.rest.dto;

public class RequestItem {
	private RequestItem() {}

	// - B U I L D E R
	public static class Builder {
		private final RequestItem onConstruction;

		public Builder() {
			this.onConstruction = new RequestItem();
		}

		public RequestItem build() {
			return this.onConstruction;
		}
	}
}
