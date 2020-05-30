package org.dimensinfin.printer3d.client.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;

public class CoilList {
	private Integer count = 0;
	private List<Coil> coils = new ArrayList<>();

	// - C O N S T R U C T O R S
	private CoilList() {}

	public Integer getCount() {
		return this.count;
	}

	public List<Coil> getCoils() {
		return this.coils;
	}

	// - B U I L D E R
	public static class Builder {
		private CoilList onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new CoilList();
		}
		public CoilList.Builder withCount( final Integer count ) {
			if (null != count) this.onConstruction.count = count;
			return this;
		}

		public CoilList.Builder withRollList( final List<Coil> coils ) {
			if (null != coils) this.onConstruction.coils = Objects.requireNonNull( coils );
			return this;
		}

		public CoilList build() {
			Objects.requireNonNull( this.onConstruction.count );
			Objects.requireNonNull( this.onConstruction.coils );
			return this.onConstruction;
		}
	}
}
