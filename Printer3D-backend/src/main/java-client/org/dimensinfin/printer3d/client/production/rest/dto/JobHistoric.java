package org.dimensinfin.printer3d.client.production.rest.dto;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

public class JobHistoric {
	@NotNull(message = "Machine unique UUID 'id' is a mandatory field and cannot be null.")
	@SerializedName("id")
	private UUID id;
	@NotNull(message = "Job Part reference 'jobPartId' cannot be null.")
	@SerializedName("jobPartId")
	private UUID jobPartId;
	@NotNull(message = "Job Part 'buildTime' is mandatory.")
	@SerializedName("buildTime")
	private Integer buildTime;
	@NotNull(message = "Job Part 'cost' value is mandatory.")
	@SerializedName("cost")
	private Float cost;
	@NotNull(message = "Job Part 'price' value is mandatory.")
	@SerializedName("price")
	private Float price;
	@Column(name = "part_copies", nullable = false)
	@SerializedName("partCopies")
	private Integer partCopies = 1;
	@NotNull(message = "Job 'jobInstallmentDate' cannot be null.")
	@SerializedName("jobInstallmentDate")
	private OffsetDateTime jobInstallmentDate;
	@NotNull(message = "Job 'jobBuildDate' cannot be null.")
	@SerializedName("jobBuildDate")
	private OffsetDateTime jobBuildDate = OffsetDateTime.now();

	// - C O N S T R U C T O R S
	private JobHistoric() {}

	// - G E T T E R S   &   S E T T E R S
	public Integer getBuildTime() {
		return this.buildTime;
	}

	public Float getCost() {
		return this.cost;
	}

	public UUID getId() {
		return this.id;
	}

	public OffsetDateTime getJobBuildDate() {
		return this.jobBuildDate;
	}

	public OffsetDateTime getJobInstallmentDate() {
		return this.jobInstallmentDate;
	}

	public UUID getJobPartId() {
		return this.jobPartId;
	}

	public Integer getPartCopies() {
		return this.partCopies;
	}

	public Float getPrice() {
		return this.price;
	}

	// - B U I L D E R
	public static class Builder {
		private final JobHistoric onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new JobHistoric();
		}

		public JobHistoric build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.jobPartId );
			Objects.requireNonNull( this.onConstruction.buildTime );
			Objects.requireNonNull( this.onConstruction.cost );
			Objects.requireNonNull( this.onConstruction.price );
			Objects.requireNonNull( this.onConstruction.partCopies );
			Objects.requireNonNull( this.onConstruction.jobInstallmentDate );
			Objects.requireNonNull( this.onConstruction.jobBuildDate );
			return this.onConstruction;
		}

		public JobHistoric.Builder withBuildTime( final Integer buildTime ) {
			this.onConstruction.buildTime = Objects.requireNonNull( buildTime );
			return this;
		}

		public JobHistoric.Builder withCost( final Float cost ) {
			this.onConstruction.cost = Objects.requireNonNull( cost );
			return this;
		}

		public JobHistoric.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public JobHistoric.Builder withJobBuildDate( final OffsetDateTime buildDate ) {
			this.onConstruction.jobBuildDate = Objects.requireNonNull( buildDate );
			return this;
		}

		public JobHistoric.Builder withJobInstallmentDate( final OffsetDateTime installmentDate ) {
			this.onConstruction.jobInstallmentDate = Objects.requireNonNull( installmentDate );
			return this;
		}

		public JobHistoric.Builder withPartCopies( final Integer copies ) {
			if (null != copies) this.onConstruction.partCopies = copies;
			return this;
		}

		public JobHistoric.Builder withPartId( final UUID partId ) {
			this.onConstruction.jobPartId = Objects.requireNonNull( partId );
			return this;
		}

		public JobHistoric.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}
	}
}
