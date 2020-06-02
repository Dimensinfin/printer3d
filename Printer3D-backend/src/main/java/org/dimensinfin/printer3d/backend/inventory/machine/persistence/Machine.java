package org.dimensinfin.printer3d.backend.inventory.machine.persistence;

import java.time.LocalDateTime;
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

@Entity
@Table(name = "machines", schema = "printer3d")
public class Machine {
	@Id
	@NotNull(message = "Machine unique UUID 'id' is a mandatory field and cannot be null.")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id = null;
	@NotNull(message = "Machine 'label' is mandatory.")
	@Size(max = 32)
	@Column(name = "label", updatable = true, nullable = false)
	private String label = null;
	@NotNull(message = "Machine 'model' is mandatory.")
	@Size(max = 64)
	@Column(name = "model", updatable = true, nullable = false)
	private String model = null;
	@Size(max = 300)
	@Column(name = "characteristics", updatable = true, nullable = false)
	private String characteristics = null;
	@Column(name = "current_job_part_id")
	private UUID currentJobPartId;
	@Column(name = "current_part_instances")
	private int currentPartInstances = 1;
	@Column(name = "job_installment_date", columnDefinition = "TIMESTAMP")
	private LocalDateTime jobInstallmentDate = null;

	// - G E T T E R S   &   S E T T E R S
	public UUID getCurrentJobPartId() {
		return this.currentJobPartId;
	}

	public int getCurrentPartInstances() {
		return this.currentPartInstances;
	}

	public LocalDateTime getJobInstallmentDate() {
		return this.jobInstallmentDate;
	}

	public String getLabel() {
		return this.label;
	}

	public void clearJob() {
		this.currentJobPartId = null;
		this.jobInstallmentDate = null;
		this.currentPartInstances = 1;
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
				.append( this.jobInstallmentDate )
				.toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof Machine)) return false;
		final Machine machine = (Machine) o;
		return new EqualsBuilder()
				.append( this.currentPartInstances, machine.currentPartInstances )
				.append( this.label, machine.label )
				.append( this.model, machine.model )
				.append( this.characteristics, machine.characteristics )
				.append( this.currentJobPartId, machine.currentJobPartId )
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
				.append( "jobInstallmentDate", this.jobInstallmentDate )
				.toString();
	}
}

