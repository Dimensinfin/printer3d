package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Finishing {
	private String material = null;
	private List<String> colors = new ArrayList<>();

	// - C O N S T R U C T O R S
	private Finishing() { }

	// - G E T T E R S   &   S E T T E R S
	public List<String> getColors() {
		return this.colors;
	}

	public String getMaterial() {
		return this.material;
	}

	// - B U I L D E R
	public static class Builder {
		private Finishing onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Finishing();
		}

		public Finishing build() {
			Objects.requireNonNull( this.onConstruction.material );
			Objects.requireNonNull( this.onConstruction.colors );
			return this.onConstruction;
		}

		public Finishing.Builder withColors( final List<String> colors ) {
			this.onConstruction.colors = Objects.requireNonNull( colors );
			return this;
		}

		public Finishing.Builder withMaterial( final String material ) {
			this.onConstruction.material = Objects.requireNonNull( material );
			return this;
		}
	}
}

