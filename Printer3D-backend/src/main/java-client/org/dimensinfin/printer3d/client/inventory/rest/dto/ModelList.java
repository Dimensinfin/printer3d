package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModelList {
	private List<Model> models = new ArrayList<>();

	// - G E T T E R S   &   S E T T E R S
	public int getCount() {
		return this.models.size();
	}

	public List<Model> getModels() {
		return this.models;
	}

	// - B U I L D E R
	public static class Builder {
		private final ModelList onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new ModelList();
		}

		public ModelList build() {
			Objects.requireNonNull( this.onConstruction.models );
			return this.onConstruction;
		}

		public ModelList.Builder withPartList( final List<Model> models ) {
			this.onConstruction.models = Objects.requireNonNull( models );
			return this;
		}
	}
}
