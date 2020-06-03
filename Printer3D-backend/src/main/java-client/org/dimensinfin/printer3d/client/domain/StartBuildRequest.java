package org.dimensinfin.printer3d.client.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class StartBuildRequest {
	private UUID machineId;
	private UUID partId;

	// - C O N S T R U C T O R S
	private StartBuildRequest() {}

	public UUID getMachineId() {
		return this.machineId;
	}

	public UUID getPartId() {
		return this.partId;
	}

	// - B U I L D E R
	public static class Builder {
		private StartBuildRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new StartBuildRequest();
		}

		public StartBuildRequest build() {
			return this.onConstruction;
		}

		public StartBuildRequest.Builder withMachineId( final UUID machineId ) {
			this.onConstruction.machineId = Objects.requireNonNull( machineId );
			return this;
		}

		public StartBuildRequest.Builder withPartId( final UUID partId ) {
			this.onConstruction.partId = Objects.requireNonNull( partId );
			return this;
		}
	}
}
