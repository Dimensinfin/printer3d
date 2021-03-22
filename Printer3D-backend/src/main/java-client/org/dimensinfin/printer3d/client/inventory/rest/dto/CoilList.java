package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;

public class CoilList {
	private List<Coil> coils = new ArrayList<>();

	// - C O N S T R U C T O R S
	private CoilList() {}

	// - G E T T E R S   &   S E T T E R S
	public List<Coil> getCoils() {
		return this.coils;
	}

	public Integer getCount() {
		return this.coils.size();
	}

	// - B U I L D E R
	public static class Builder {
		private final CoilList onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new CoilList();
		}

		public CoilList build() {
			Objects.requireNonNull( this.onConstruction.coils );
			return this.onConstruction;
		}

		public CoilList.Builder withCoilList( final List<Coil> coils ) {
			this.onConstruction.coils = Objects.requireNonNull( coils );
			return this;
		}
	}
}
