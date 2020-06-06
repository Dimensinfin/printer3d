package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.Objects;
import java.util.UUID;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;

public class Job {
	private UUID id;
	private Part part;

	// - C O N S T R U C T O R S
	private Job() {}

	// - G E T T E R S   &   S E T T E R S
	public UUID getId() {
		return this.id;
	}

	public Part getPart() {
		return this.part;
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
	}
}
