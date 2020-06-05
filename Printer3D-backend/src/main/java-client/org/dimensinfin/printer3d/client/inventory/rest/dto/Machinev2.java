package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Machinev2 /*extends Machine*/ {
	protected UUID id;
	protected String label;
	protected String model;
	protected String characteristics;
	private BuildRecord buildRecord;

	// - C O N S T R U C T O R S
	private Machinev2() {}

	// - G E T T E R S   &   S E T T E R S
	public BuildRecord getBuildRecord() {
		return this.buildRecord;
	}

	public String getCharacteristics() {
		return this.characteristics;
	}

	public UUID getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public String getModel() {
		return this.model;
	}

	public int getRemainingTime() {
		if (null != this.buildRecord) return this.buildRecord.getRemainingTime();
		else return 0;
	}

	public boolean isRunning() {
		if (null != this.buildRecord) return this.buildRecord.isRunning();
		else return false;
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", this.id )
				.append( "label", this.label )
				.append( "model", this.model )
				.append( "characteristics", this.characteristics )
				.append( "buildRecord",(null == this.buildRecord)?"null": this.buildRecord.toString() )
				.toString();
	}

	// - B U I L D E R
	public static class Builder {
		private final Machinev2 onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Machinev2();
		}

		public Machinev2 build() {
			return this.onConstruction;
		}

		public Machinev2.Builder withBuildRecord( final BuildRecord buildRecord ) {
			this.onConstruction.buildRecord = buildRecord;
			return this;
		}

		public Machinev2.Builder withCharacteristics( final String characteristics ) {
			this.onConstruction.characteristics = Objects.requireNonNull( characteristics );
			return this;
		}

		public Machinev2.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		//		public Machinev2.Builder withInstallmentDate( final LocalDateTime installmentDate ) {
		//			this.onConstruction.jobInstallmentDate = installmentDate;
		//			return this;
		//		}
		//
		//		public Machinev2.Builder withInstances( final Integer instances ) {
		//			this.onConstruction.currentPartInstances = Objects.requireNonNull( instances );
		//			return this;
		//		}

		public Machinev2.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public Machinev2.Builder withModel( final String model ) {
			this.onConstruction.model = Objects.requireNonNull( model );
			return this;
		}

		//		public Machinev2.Builder withPart( final Part part ) {
		//			this.onConstruction.currentJobPart = part;
		//			return this;
		//		}
	}
}
