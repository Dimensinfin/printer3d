package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	@Column(name = "part_copies", nullable = false)
	private Integer partCopies = 1;
	@NotNull(message = "Job 'jobInstallmentDate' cannot be null.")
	@Column(name = "job_installment_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private OffsetDateTime jobInstallmentDate;
	@NotNull(message = "Job 'jobBuildDate' cannot be null.")
	@Column(name = "job_build_date", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	private OffsetDateTime jobBuildDate = OffsetDateTime.now();

	// - C O N S T R U C T O R S
	private JobEntity() {}

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
			Objects.requireNonNull( this.onConstruction.partCopies );
			Objects.requireNonNull( this.onConstruction.jobInstallmentDate );
			Objects.requireNonNull( this.onConstruction.jobBuildDate );
			return this.onConstruction;
		}

		public JobEntity.Builder withJobBuildDate( final OffsetDateTime buildDate ) {
			this.onConstruction.jobBuildDate = Objects.requireNonNull( buildDate );
			return this;
		}

		public JobEntity.Builder withJobInstallmentDate( final OffsetDateTime installmentDate ) {
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
	}
}
