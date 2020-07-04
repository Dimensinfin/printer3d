package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;

public class UpdateGroupPartRequest {
	private String description;
	private int weight;
	private Integer buildTime;
	private String imagePath;
	private String modelPath;

	// - G E T T E R S   &   S E T T E R S
	public Integer getBuildTime() {
		return buildTime;
	}

	public String getDescription() {
		return description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getModelPath() {
		return modelPath;
	}

	public Integer getWeight() {
		return weight;
	}

	// - B U I L D E R
	public static class Builder {
		private final UpdateGroupPartRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new UpdateGroupPartRequest();
		}

		public UpdateGroupPartRequest build() {
			return this.onConstruction;
		}

		public UpdateGroupPartRequest.Builder withBuildTime( final Integer buildTime ) {
			this.onConstruction.buildTime = Objects.requireNonNull( buildTime );
			return this;
		}

		public UpdateGroupPartRequest.Builder withDescription( final String description ) {
			if (null != description) this.onConstruction.description = description;
			return this;
		}

		public UpdateGroupPartRequest.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) this.onConstruction.imagePath = imagePath;
			return this;
		}

		public UpdateGroupPartRequest.Builder withModelPath( final String modelPath ) {
			if (null != modelPath) this.onConstruction.modelPath = modelPath;
			return this;
		}

		public UpdateGroupPartRequest.Builder withWeight( final Integer weight ) {
			if (null != weight) this.onConstruction.weight = Objects.requireNonNull( weight );
			return this;
		}
	}
}
