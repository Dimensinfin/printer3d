package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;
import java.util.UUID;

public class UpdateModelCompositionRequest {
	private UUID modelId;
	private UUID partId;

	// - C O N S T R U C T O R S
	private UpdateModelCompositionRequest() {}

	// - G E T T E R S   &   S E T T E R S
	public UUID getModelId() {
		return this.modelId;
	}

	public UUID getPartId() {
		return this.partId;
	}

	// - B U I L D E R
	public static class Builder {
		private final UpdateModelCompositionRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new UpdateModelCompositionRequest();
		}

		public UpdateModelCompositionRequest build() {
			return this.onConstruction;
		}

		public UpdateModelCompositionRequest.Builder withModelId( final UUID modelId ) {
			this.onConstruction.modelId = Objects.requireNonNull( modelId );
			return this;
		}

		public UpdateModelCompositionRequest.Builder withPartId( final UUID partId ) {
			this.onConstruction.partId = Objects.requireNonNull( partId );
			return this;
		}
	}
}
