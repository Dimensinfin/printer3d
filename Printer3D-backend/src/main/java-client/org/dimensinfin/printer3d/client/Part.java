package org.dimensinfin.printer3d.client;

import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Immutable;

@Immutable
public class Part {
	@NotNull(message = "Part unique UUID 'id' should not be null.")
	private UUID id;
	@NotNull(message = "Part 'label' is mandatory.")
	private String label;
	private String description;
	private ColorCode colorCode;
	@NotNull(message = "Part 'cost' value is mandatory.")
	private Float cost;
	@NotNull(message = "Part 'price' value is mandatory.")
	private Float price;
	private Boolean active = true;

	// - C O N S T R U C T O R S
	private Part() { }

	// - G E T T E R S   &   S E T T E R S
	public Boolean isActive() {
		return this.active;
	}

	public Float getCost() {
		return this.cost;
	}

	public String getDescription() {
		return this.description;
	}

	public UUID getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public Float getPrice() {
		return this.price;
	}

	// - B U I L D E R
	public static class Builder {
		private Part onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Part();
		}

		public Part build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			return this.onConstruction;
		}

		public Part.Builder withCost( final Float cost ) {
			this.onConstruction.cost = Objects.requireNonNull( cost );
			return this;
		}

		public Part.Builder withDescription( final String description ) {
			this.onConstruction.description = Objects.requireNonNull( description );
			return this;
		}

		public Part.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public Part.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public Part.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}
	}
}

