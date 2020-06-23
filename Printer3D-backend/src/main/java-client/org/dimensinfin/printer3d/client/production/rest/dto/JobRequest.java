package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

public class JobRequest {
	//	@NotNull(message = "Job unique UUID 'id' is a mandatory field and cannot be null.")
	@SerializedName("id")
	private UUID id;
	//	@NotNull(message = "Job Part reference 'partId' cannot be null.")
	@SerializedName("partId")
	private UUID partId;
	@NotNull(message = "The number of copies cannot be null.")
	@SerializedName("partCount")
	private int partCount;

	// - C O N S T R U C T O R S
	private JobRequest() {}

	// - G E T T E R S   &   S E T T E R S
	public UUID getId() {
		return this.id;
	}

	public int getPartCount() {
		return this.partCount;
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

		public JobRequest.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public JobRequest.Builder withPartCopies( final Integer copies ) {
			if (null != copies) this.onConstruction.partCount = copies;
			return this;
		}

		public JobRequest.Builder withPartId( final UUID partId ) {
			this.onConstruction.partId = Objects.requireNonNull( partId );
			return this;
		}
	}
}
