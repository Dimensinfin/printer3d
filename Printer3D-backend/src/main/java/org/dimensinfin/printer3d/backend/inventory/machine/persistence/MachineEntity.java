package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This is the entity that describes a 3D printer model and its configuration. The information about Machines is hardcoded at
 * initialization time and the machine records can only update the current build **Part** job. Each Machine has a slot to record the current
 * build action this machine is running. This data will keep track of the remaining time to complete the job and then update the stock records.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.4.0
 */
@Entity
@Table(name = "machines", schema = "printer3d")
public class MachineEntity {
	@Id
	@NotNull(message = "Machine unique UUID 'id' is a mandatory field and cannot be null.")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@NotNull(message = "Machine 'label' is mandatory.")
	@Size(max = 32)
	@Column(name = "label", updatable = true, nullable = false)
	private String label;
	@NotNull(message = "Machine 'model' is mandatory.")
	@Size(max = 64)
	@Column(name = "model", updatable = true, nullable = false)
	private String model;
	@Size(max = 300)
	@Column(name = "characteristics", updatable = true, nullable = false)
	private String characteristics;
	@Column(name = "current_job_part_id")
	private UUID currentJobPartId;
	@Column(name = "current_part_instances")
	private int currentPartInstances = 1;
	@Column(name = "job_part_build_time")
	private int currentJobPartBuildTime = 0;
	@Column(name = "job_installment_date", columnDefinition = "TIMESTAMP")
	private Instant jobInstallmentDate;

	// - G E T T E R S   &   S E T T E R S
	public String getCharacteristics() {
		return this.characteristics;
	}

	public int getCurrentJobPartBuildTime() {
		return this.currentJobPartBuildTime;
	}

	public MachineEntity setCurrentJobPartBuildTime( final int currentJobPartBuildTime ) {
		this.currentJobPartBuildTime = currentJobPartBuildTime;
		return this;
	}

	public UUID getCurrentJobPartId() {
		return this.currentJobPartId;
	}

	public MachineEntity setCurrentJobPartId( final UUID currentJobPartId ) {
		this.currentJobPartId = currentJobPartId;
		return this;
	}

	public int getCurrentPartInstances() {
		return this.currentPartInstances;
	}

	public MachineEntity setCurrentPartInstances( final int currentPartInstances ) {
		this.currentPartInstances = currentPartInstances;
		return this;
	}

	public UUID getId() {
		return this.id;
	}

	public Instant getJobInstallmentDate() {
		return this.jobInstallmentDate;
	}

	public MachineEntity setJobInstallmentDate( final Instant jobInstallmentDate ) {
		this.jobInstallmentDate = jobInstallmentDate;
		return this;
	}

	public String getLabel() {
		return this.label;
	}

	public String getModel() {
		return this.model;
	}

	public MachineEntity clearJob() {
		this.currentJobPartId = null;
		this.jobInstallmentDate = null;
		this.currentPartInstances = 1;
		this.currentJobPartBuildTime = 0;
		return this;
	}

	// - C O R E
	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( this.label )
				.append( this.model )
				.append( this.characteristics )
				.append( this.currentJobPartId )
				.append( this.currentPartInstances )
				.append( this.currentJobPartBuildTime )
				.append( this.jobInstallmentDate )
				.toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof MachineEntity)) return false;
		final MachineEntity machine = (MachineEntity) o;
		return new EqualsBuilder()
				.append( this.label, machine.label )
				.append( this.model, machine.model )
				.append( this.characteristics, machine.characteristics )
				.append( this.currentJobPartId, machine.currentJobPartId )
				.append( this.currentPartInstances, machine.currentPartInstances )
				.append( this.currentJobPartBuildTime, machine.currentJobPartBuildTime )
				.append( this.jobInstallmentDate, machine.jobInstallmentDate )
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", this.id )
				.append( "label", this.label )
				.append( "model", this.model )
				.append( "characteristics", this.characteristics )
				.append( "currentJobPartId", this.currentJobPartId )
				.append( "currentPartInstances", this.currentPartInstances )
				.append( "currentJobPartBuildTime", this.currentJobPartBuildTime )
				.append( "jobInstallmentDate", this.jobInstallmentDate )
				.toString();
	}

	// - B U I L D E R
	public static class Builder {
		private final MachineEntity onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new MachineEntity();
		}

		public MachineEntity build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.model );
			return this.onConstruction;
		}
		public MachineEntity.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public MachineEntity.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public MachineEntity.Builder withModel( final String model ) {
			this.onConstruction.model = Objects.requireNonNull( model );
			return this;
		}
	}
}
