package org.dimensinfin.printer3d.backend.part.persistence;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "inventory", schema = "printer3d")
public class Part {
	@Id
	@NotNull(message = "Part unique UUID 'id' should not be null.")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@NotNull(message = "Part 'label' is mandatory.")
	@Size(min = 3, max = 50)
	@Column(name = "label", updatable = false, nullable = false)
	private String label;
	@Size(max = 500)
	@Column(name = "description", updatable = true, nullable = true)
	private String description;
	@NotNull(message = "Part 'cost' value is mandatory.")
	@Column(name = "cost", updatable = true, nullable = false)
	private Float cost;
	@NotNull(message = "Part 'price' value is mandatory.")
	@Column(name = "price", updatable = true, nullable = false)
	private Float price;
	@NotNull(message = "Part 'stockLevel' value is mandatory.")
	@Column(name = "stock_level", updatable = true, nullable = false)
	private Integer stockLevel;
	@Column(name = "active", updatable = true, nullable = false)
	private boolean active = true;

	// - G E T T E R S   &   S E T T E R S
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

	public Integer getStockLevel() {
		return this.stockLevel;
	}

	public Float getPrice() {
		return this.price;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				//				.append( this.id )
				.append( this.label )
				.append( this.description )
				.append( this.cost )
				.append( this.price )
				.append( this.stockLevel )
				.append( this.active )
				.toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof Part)) return false;
		final Part part = (Part) o;
		return new EqualsBuilder()
				//				.append( this.id, part.id )
				.append( this.label, part.label )
				.append( this.description, part.description )
				.append( this.cost, part.cost )
				.append( this.price, part.price )
				.append( this.stockLevel, part.stockLevel )
				.append( this.active, part.active )
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", this.id )
				.append( "label", this.label )
				.append( "description", this.description )
				.append( "cost", this.cost )
				.append( "price", this.price )
				.append( "stockLevel", this.stockLevel )
				.append( "active", this.active )
				.toString();
	}

	public Boolean isActive() {
		return this.active;
	}

	// - B U I L D E R
	public static class Builder {
		private final Part onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Part();
		}

		public Part build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.cost );
			Objects.requireNonNull( this.onConstruction.price );
			Objects.requireNonNull( this.onConstruction.stockLevel );
			return this.onConstruction;
		}

		public Part.Builder withActive( final Boolean active ) {
			if (null != active) this.onConstruction.active = active;
			return this;
		}

		public Part.Builder withCost( final Float cost ) {
			this.onConstruction.cost = Objects.requireNonNull( cost );
			return this;
		}

		public Part.Builder withDescription( final String description ) {
		if ( null != description)	this.onConstruction.description =  description ;
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
		public Part.Builder withStockLevel( final Integer stockLevel ) {
			this.onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}
	}
}
