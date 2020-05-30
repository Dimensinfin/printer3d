package org.dimensinfin.printer3d.client.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Coil;

public class RollList {
	private Integer count = 0;
	private List<Coil> coils = new ArrayList<>();

	// - C O N S T R U C T O R S
	private RollList() {}

	public Integer getCount() {
		return this.count;
	}

	public List<Coil> getCoils() {
		return this.coils;
	}

	// - B U I L D E R
	public static class Builder {
		private RollList onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new RollList();
		}
		public RollList.Builder withCount( final Integer count ) {
			if (null != count) this.onConstruction.count = count;
			return this;
		}

		public RollList.Builder withRollList( final List<Coil> coils ) {
			if (null != coils) this.onConstruction.coils = Objects.requireNonNull( coils );
			return this;
		}

		public RollList build() {
			Objects.requireNonNull( this.onConstruction.count );
			Objects.requireNonNull( this.onConstruction.coils );
			return this.onConstruction;
		}
	}
}
