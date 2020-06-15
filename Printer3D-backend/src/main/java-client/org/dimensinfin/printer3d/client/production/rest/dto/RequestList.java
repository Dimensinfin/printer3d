package org.dimensinfin.printer3d.client.production.rest.dto;

public class RequestList {
	private RequestList() {}

	// - B U I L D E R
	public static class Builder {
		private final RequestList onConstruction;

		public Builder() {
			this.onConstruction = new RequestList();
		}

		public RequestList build() {
			return this.onConstruction;
		}
	}
}
