package org.dimensinfin.printer3d.backend.production.job.persistence;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The information required to report the 3D machine owner the necessity to build a particular **Part**. It should indicate the
 * correct model and **Finishing**. There is a job for every single Part. If the machine is able to build more than one identical part at the same
 * time this should be controlled on the frontend user interface.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.6.0
 */
@Entity
@Table(name = "jobs", schema = "printer3d")
public class JobEntity {
	@Id
	@NotNull(message = "Machine unique UUID 'id' is a mandatory field and cannot be null.")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@NotNull(message = "Job Part reference 'jobPartId' cannot be null.")
	@Column(name = "job_part_id", nullable = false)
	private UUID jobPartId;
	@NotNull(message = "Job Part 'buildTime' is mandatory.")
	@Column(name = "build_time", nullable = false)
	private Integer buildTime;
	@NotNull(message = "Job Part 'cost' value is mandatory.")
	@Column(name = "part_cost", nullable = false)
	private Float cost;
	@NotNull(message = "Job Part 'price' value is mandatory.")
	@Column(name = "part_price", nullable = false)
	private Float price;
	@Column(name = "part_copies", nullable = false)
	private Integer partCopies = 1;
	@NotNull(message = "Job 'jobInstallmentDate' cannot be null.")
	@Column(name = "job_installment_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private Instant jobInstallmentDate;
	@NotNull(message = "Job 'jobBuildDate' cannot be null.")
	@Column(name = "job_build_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private Instant jobBuildDate = Instant.now();

	// - C O N S T R U C T O R S
	private JobEntity() {}

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

	public Instant getJobBuildDate() {
		return this.jobBuildDate;
	}

	public Instant getJobInstallmentDate() {
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
		private final JobEntity onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new JobEntity();
		}

		public JobEntity build() {
			this.onConstruction.id = UUID.randomUUID();
			Objects.requireNonNull( this.onConstruction.jobPartId );
			Objects.requireNonNull( this.onConstruction.buildTime );
			Objects.requireNonNull( this.onConstruction.cost );
			Objects.requireNonNull( this.onConstruction.price );
			Objects.requireNonNull( this.onConstruction.partCopies );
			Objects.requireNonNull( this.onConstruction.jobInstallmentDate );
			Objects.requireNonNull( this.onConstruction.jobBuildDate );
			return this.onConstruction;
		}

		public JobEntity.Builder withBuildTime( final Integer buildTime ) {
			this.onConstruction.buildTime = Objects.requireNonNull( buildTime );
			return this;
		}

		public JobEntity.Builder withCost( final Float cost ) {
			this.onConstruction.cost = Objects.requireNonNull( cost );
			return this;
		}

		public JobEntity.Builder withJobBuildDate( final Instant buildDate ) {
			this.onConstruction.jobBuildDate = Objects.requireNonNull( buildDate );
			return this;
		}

		public JobEntity.Builder withJobInstallmentDate( final Instant installmentDate ) {
			this.onConstruction.jobInstallmentDate = Objects.requireNonNull( installmentDate );
			return this;
		}

		public JobEntity.Builder withPartCopies( final Integer copies ) {
			if (null != copies) this.onConstruction.partCopies = copies;
			return this;
		}

		public JobEntity.Builder withPartId( final UUID partId ) {
			this.onConstruction.jobPartId = Objects.requireNonNull( partId );
			return this;
		}

		public JobEntity.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}
	}
}
