package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.Objects;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public class Job {
	@SerializedName("id")
	private UUID id;
	@SerializedName("part")
	private Part part;
	@SerializedName("priority")
	private int priority = 3;

	// - C O N S T R U C T O R S
	private Job() {}

	// - G E T T E R S   &   S E T T E R S
	public UUID getId() {
		return this.id;
	}

	public Part getPart() {
		return this.part;
	}

	public int getPriority() {
		return this.priority;
	}

	// - B U I L D E R
	public static class Builder {
		private final Job onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Job();
		}

		public Job build() {
			this.onConstruction.id = UUID.randomUUID();
			Objects.requireNonNull( this.onConstruction.part );
			return this.onConstruction;
		}

		public Job.Builder withPart( final Part part ) {
			this.onConstruction.part = Objects.requireNonNull( part );
			return this;
		}

		public Job.Builder withPriority( final Integer priority ) {
			this.onConstruction.priority = Objects.requireNonNull( priority );
			return this;
		}
	}
}
