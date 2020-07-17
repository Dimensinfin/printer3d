package org.dimensinfin.printer3d.backend.datamanagement.support.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PartListData {
	private List<PartData> parts = new ArrayList<>();

	// - G E T T E R S   &   S E T T E R S
	public int getCount() {
		return this.parts.size();
	}

	public List<PartData> getParts() {
		return this.parts;
	}

	// - B U I L D E R
	public static class Builder {
		private final PartListData onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new PartListData();
		}

		public PartListData build() {
			Objects.requireNonNull( this.onConstruction.parts );
			return this.onConstruction;
		}

		public PartListData.Builder withPartList( final List<PartData> parts ) {
			this.onConstruction.parts = Objects.requireNonNull( parts );
			return this;
		}
	}
}
