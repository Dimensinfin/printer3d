package org.dimensinfin.printer3d.backend.part.persistence;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "inventory", schema = "printer3d")
public class Part {
	@Id
	@NotNull(message = "Part unique UUID 'id' should not be null.")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id = null;
	@NotNull(message = "Part 'label' is mandatory.")
	@Size(min = 3, max = 50)
	@Column(name = "label", updatable = false, nullable = false)
	private String label = null;
	@Size(max = 500)
	@Column(name = "description", updatable = true, nullable = true)
	private String description = null;
	@NotNull(message = "Part 'cost' value is mandatory.")
	@Column(name = "cost", updatable = true, nullable = false)
	private Float cost = null;
	@NotNull(message = "Part 'price' value is mandatory.")
	@Column(name = "price", updatable = true, nullable = false)
	private Float price = null;
	@Column(name = "active", updatable = true, nullable = false)
	private Boolean active = true;

	// - G E T T E R S   &   S E T T E R S
	public UUID getId() {
		return id;
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
