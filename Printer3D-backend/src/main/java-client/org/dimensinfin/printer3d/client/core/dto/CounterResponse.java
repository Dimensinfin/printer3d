package org.dimensinfin.printer3d.client.core.dto;

import java.util.Objects;

public class CounterResponse {
	private int records;

	// - C O N S T R U C T O R S
	private CounterResponse() {}

	// - G E T T E R S   &   S E T T E R S
	public int getRecords() {
		return this.records;
	}

	// - B U I L D E R
	public static class Builder {
		private CounterResponse onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new CounterResponse();
		}

		public CounterResponse build() {
			return this.onConstruction;
		}

		public Builder withRecords( final Integer records ) {
			this.onConstruction.records = Objects.requireNonNull( records );
			return this;
		}
	}
}
