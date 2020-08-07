package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Immutable
public class Machine {
	private UUID id;
	private String label;
	private String model;
	private String characteristics;
	private Part currentJobPart;
	private int currentPartInstances = 1;
	private Instant jobInstallmentDate;

	// - G E T T E R S   &   S E T T E R S
	public String getCharacteristics() {
		return this.characteristics;
	}

	public Part getCurrentJobPart() {
		return this.currentJobPart;
	}

	public int getCurrentPartInstances() {
		return this.currentPartInstances;
	}

	public UUID getId() {
		return this.id;
	}

	public Instant getJobInstallmentDate() {
		return this.jobInstallmentDate;
	}

	public String getLabel() {
		return this.label;
	}

	public String getModel() {
		return this.model;
	}

	// - C O R E
	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( this.id )
				.append( this.label )
				.append( this.model )
				.append( this.characteristics )
				.append( this.currentJobPart )
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
				.append( this.id, machine.id )
				.append( this.label, machine.label )
				.append( this.model, machine.model )
				.append( this.characteristics, machine.characteristics )
				.append( this.currentJobPart, machine.currentJobPart )
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
				.append( "currentJobPart", (null != this.currentJobPart) ? this.currentJobPart.toString() : "null" )
				.append( "currentPartInstances", this.currentPartInstances )
				.append( "jobInstallmentDate", (null != this.jobInstallmentDate) ? this.jobInstallmentDate.toString() : "null" )
				.toString();
	}

	// - B U I L D E R
	public static class Builder {
		private final Machine onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Machine();
		}

		public Machine build() {
			return this.onConstruction;
		}

		public Machine.Builder withCharacteristics( final String characteristics ) {
			if (null != characteristics) this.onConstruction.characteristics = characteristics;
			return this;
		}

		public Machine.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public Machine.Builder withInstallmentDate( final Instant installmentDate ) {
			this.onConstruction.jobInstallmentDate = installmentDate;
			return this;
		}

		public Machine.Builder withInstances( final Integer instances ) {
			this.onConstruction.currentPartInstances = Objects.requireNonNull( instances );
			return this;
		}

		public Machine.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public Machine.Builder withModel( final String model ) {
			this.onConstruction.model = Objects.requireNonNull( model );
			return this;
		}

		public Machine.Builder withPart( final Part part ) {
			this.onConstruction.currentJobPart = part;
			return this;
		}
	}
}
