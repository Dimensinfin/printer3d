package org.dimensinfin.printer3d.client.domain;

import java.util.ArrayList;
import java.util.List;

public class FinishingsResponse {
	private List<Finishing> materials = new ArrayList<Finishing>();

	// - C O N S T R U C T O R S
	private FinishingsResponse() {}

	// - G E T T E R S   &   S E T T E R S
	public List<Finishing> getMaterials() {
		return this.materials;
	}

	public FinishingsResponse addFinishing( final Finishing finishing ) {
		this.materials.add( finishing );
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private FinishingsResponse onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new FinishingsResponse();
		}

		public FinishingsResponse build() {
			return this.onConstruction;
		}
	}
}
