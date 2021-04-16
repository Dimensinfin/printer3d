package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;

public class UpdateGroupPartRequest {
	private String label;
	private String project;
	private String description;
	private Integer weight;
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

	public String getLabel() {
		return label;
	}

	public UpdateGroupPartRequest setLabel( final String label ) {
		this.label = label;
		return this;
	}

	public String getModelPath() {
		return modelPath;
	}

	public String getProject() {
		return project;
	}

	public Integer getWeight() {
		return weight;
	}

	// - B U I L D E R
	public static class Builder {
		private final UpdateGroupPartRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			onConstruction = new UpdateGroupPartRequest();
		}

		public UpdateGroupPartRequest build() {
			return onConstruction;
		}

		public UpdateGroupPartRequest.Builder withBuildTime( final Integer buildTime ) {
			if (null != buildTime) onConstruction.buildTime = Objects.requireNonNull( buildTime );
			return this;
		}

		public UpdateGroupPartRequest.Builder withDescription( final String description ) {
			if (null != description) onConstruction.description = description;
			return this;
		}

		public UpdateGroupPartRequest.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) onConstruction.imagePath = imagePath;
			return this;
		}

		public UpdateGroupPartRequest.Builder withLabel( final String label ) {
			if (null != label) onConstruction.label = label;
			return this;
		}

		public UpdateGroupPartRequest.Builder withModelPath( final String modelPath ) {
			if (null != modelPath) onConstruction.modelPath = modelPath;
			return this;
		}

		public UpdateGroupPartRequest.Builder withProject( final String project ) {
			if (null != project) onConstruction.project = project;
			return this;
		}

		public UpdateGroupPartRequest.Builder withWeight( final Integer weight ) {
			if (null != weight) onConstruction.weight = Objects.requireNonNull( weight );
			return this;
		}
	}
}
