package org.dimensinfin.printer3d.backend.inventory.roll.persistence;

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
@Table(name = "rolls", schema = "printer3d")
public class Roll {
	@Id
	@NotNull(message = "Roll unique UUID 'id' is a mandatory field and cannot be null.")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@Size(min = 1, max = 16)
	@NotNull(message = "Roll 'material' is mandatory.")
	@Column(name = "material", nullable = false)
	private String material;
	@Size(min = 1, max = 32)
	@NotNull(message = "Part 'color' is mandatory.")
	@Column(name = "color", nullable = false)
	private String color;
	@Column(name = "weight", nullable = false)
	private Integer weight = 1000;

	// - C O N S T R U C T O R S
	private Roll() {}

	// - G E T T E R S   &   S E T T E R S
	public String getColor() {
		return color;
	}

	public UUID getId() {
		return id;
	}

	public String getMaterial() {
		return material;
	}

	public Integer getWeight() {
		return weight;
	}

	// - C O R E
	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( this.material )
				.append( this.color )
				.append( this.weight )
				.toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof Roll)) return false;
		final Roll roll = (Roll) o;
		return new EqualsBuilder()
				.append( this.material, roll.material )
				.append( this.color, roll.color )
				.append( this.weight, roll.weight )
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", id )
				.append( "material", material )
				.append( "color", color )
				.append( "weight", weight )
				.toString();
	}

	// - B U I L D E R
	public static class Builder {
		private Roll onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Roll();
		}

		public Roll build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.material );
			Objects.requireNonNull( this.onConstruction.color );
			return this.onConstruction;
		}

		public Roll.Builder withColor( final String color ) {
			this.onConstruction.color = Objects.requireNonNull( color );
			return this;
		}

		public Roll.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public Roll.Builder withMaterial( final String material ) {
			this.onConstruction.material = Objects.requireNonNull( material );
			return this;
		}

		public Roll.Builder withWeight( final Integer weight ) {
			if (null != weight) this.onConstruction.weight = weight;
			return this;
		}
	}
}
