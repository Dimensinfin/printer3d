package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dimensinfin.printer3d.backend.inventory.part.persistence.Part;

public class PartList {
	private List<Part> parts = new ArrayList<>();

	// - G E T T E R S   &   S E T T E R S
	public int getCount() {
		return this.parts.size();
	}

	public List<Part> getParts() {
		return this.parts;
	}

	// - B U I L D E R
	public static class Builder {
		private final PartList onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new PartList();
		}

		public PartList build() {
			Objects.requireNonNull( this.onConstruction.parts );
			return this.onConstruction;
		}

		public PartList.Builder withPartList( final List<Part> parts ) {
			this.onConstruction.parts = Objects.requireNonNull( parts );
			return this;
		}
	}
}

