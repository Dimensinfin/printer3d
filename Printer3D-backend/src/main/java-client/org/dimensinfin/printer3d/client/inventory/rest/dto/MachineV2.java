package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MachineV2 {
	protected UUID id;
	protected String label;
	protected String model;
	protected String characteristics;
	private BuildRecord buildRecord;

	// - C O N S T R U C T O R S
	private MachineV2() {}

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
		return this.buildRecord.getRemainingTime();
	}

	public boolean isRunning() {
		return this.buildRecord.isRunning();
	}

	// - C O R E
	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", this.id )
				.append( "label", this.label )
				.append( "model", this.model )
				.append( "characteristics", this.characteristics )
				.append( "buildRecord", (null == this.buildRecord) ? "null" : this.buildRecord.toString() )
				.toString();
	}

	// - B U I L D E R
	public static class Builder {
		private final MachineV2 onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new MachineV2();
		}

		public MachineV2 build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.model );
			Objects.requireNonNull( this.onConstruction.buildRecord );
			return this.onConstruction;
		}

		public MachineV2.Builder withBuildRecord( final BuildRecord buildRecord ) {
			this.onConstruction.buildRecord = Objects.requireNonNull( buildRecord );
			return this;
		}

		public MachineV2.Builder withCharacteristics( final String characteristics ) {
			if (null != characteristics) this.onConstruction.characteristics = characteristics;
			return this;
		}

		public MachineV2.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public MachineV2.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public MachineV2.Builder withModel( final String model ) {
			this.onConstruction.model = Objects.requireNonNull( model );
			return this;
		}
	}
}
