package org.dimensinfin.common.client.rest;

import java.util.Objects;

public class CountResponse {
	private int records;

	private CountResponse() {}

	public int getRecords() {
		return this.records;
	}

	// - B U I L D E R
	public static class Builder {
		private CountResponse onConstruction;

		public Builder() {
			this.onConstruction = new CountResponse();
		}

		public CountResponse build() {
			return this.onConstruction;
		}

		public Builder withRecords( final Integer records ) {
			this.onConstruction.records = Objects.requireNonNull( records );
			return this;
		}
	}
}
