package org.dimensinfin.printer3d.client.part.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dimensinfin.printer3d.backend.part.persistence.Part;

public class PartList {
	private Integer count;
	private List<Part> parts = new ArrayList<>();

	// - G E T T E R S   &   S E T T E R S
	public Integer getCount() {
		return this.count;
	}

	public List<Part> getParts() {
		return this.parts;
	}

	// - B U I L D E R
	public static class Builder {
		private PartList onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new PartList();
		}

		public PartList build() {
			return this.onConstruction;
		}

		public PartList.Builder withCount( final Integer count ) {
			if (null != count) this.onConstruction.count = count;
			return this;
		}

		public PartList.Builder withPartList( final List<Part> parts ) {
			this.onConstruction.parts = Objects.requireNonNull( parts );
			return this;
		}
	}
}

