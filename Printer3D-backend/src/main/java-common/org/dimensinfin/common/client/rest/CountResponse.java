package org.dimensinfin.common.client.rest;

import java.util.Objects;

public class CountResponse {
	private int records;

	// - C O N S T R U C T O R S
	private CountResponse() {}

	// - G E T T E R S   &   S E T T E R S
	public int getRecords() {
		return this.records;
	}

	// - B U I L D E R
	public static class Builder {
		private CountResponse onConstruction;

		// - C O N S T R U C T O R S
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
