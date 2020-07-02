package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

/**
 * This is the structure used to send the job information from the frontend when requesting to start a new build job. This structure is like
 * the Job that is generated when the user requests the list of jobs pending build, but this uses a Part identifier while the other uses the
 * full Part data among other small differences. This is a dto structure only for input.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.7.1
 */
public class JobRequest {
	@NotNull(message = "Job unique UUID 'id' is a mandatory field and cannot be null.")
	@SerializedName("id")
	private UUID id;
	@NotNull(message = "Job Part reference 'partId' cannot be null.")
	@SerializedName("partId")
	private UUID partId;
	/**
	 * The number of copies of the same Part that are request for build on this single job.
	 * THis will affect to the build time and to the resources of plastic to be used during the build. It is left to the Machine operator to really
	 * do all this copies at the same time.
	 */
	@NotNull(message = "The number of copies cannot be null.")
	@SerializedName("copies")
	private int copies;

	// - C O N S T R U C T O R S
	private JobRequest() {}

	// - G E T T E R S   &   S E T T E R S
	public int getCopies() {
		return this.copies;
	}

	public UUID getId() {
		return this.id;
	}

	public UUID getPartId() {
		return this.partId;
	}

	// - B U I L D E R
	public static class Builder {
		private final JobRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new JobRequest();
		}

		public JobRequest build() {
			return this.onConstruction;
		}

		public JobRequest.Builder withCopies( final Integer copies ) {
			if (null != copies) this.onConstruction.copies = copies;
			return this;
		}

		public JobRequest.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public JobRequest.Builder withPartId( final UUID partId ) {
			this.onConstruction.partId = Objects.requireNonNull( partId );
			return this;
		}
	}
}
