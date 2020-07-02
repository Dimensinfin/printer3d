package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.Objects;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;

public class Job {
//	@NotNull(message = "Job unique UUID 'id' is a mandatory field and cannot be null.")
	@SerializedName("id")
	private UUID id;
//	@NotNull(message = "Job Part reference 'partId' cannot be null.")
	@SerializedName("part")
	private Part part;
//	/**
//	 * The number of copies of the same Part that are request for build on this single job.
//	 * THis will affect to the build time and to the resources of plastic to be used during the build. It is left to the Machine operator to really
//	 * do all this copies at the same time.
//	 */
//	@NotNull(message = "The number of copies cannot be null.")
//	@SerializedName("copies")
//	private Integer copies = 1;
	/**
	 * This if the job priority set to be used. There are different sources for jobs, requests or stock level. So to indicate the user the
	 * most important jobs then we need this additional field. The default priority is 3 but it is not being used. Jobs generated from
	 * leveling have a priority 2 and jobs from Requests that need missing Parts have a priority of 1.
	 */
	@SerializedName("priority")
	private int priority = 3;

	// - C O N S T R U C T O R S
	private Job() {}

	// - G E T T E R S   &   S E T T E R S
//	public Integer getCopies() {
//		return this.copies;
//	}

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

//		public Job.Builder withCopies( final Integer copies ) {
//			if (null != copies) this.onConstruction.copies = Objects.requireNonNull( copies );
//			return this;
//		}

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
