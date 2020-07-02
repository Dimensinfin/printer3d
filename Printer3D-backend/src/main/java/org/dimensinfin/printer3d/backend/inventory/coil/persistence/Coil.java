package org.dimensinfin.printer3d.backend.inventory.coil.persistence;

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

import org.dimensinfin.logging.LogWrapper;

/**
 * To build a Part we require to use a plastic filament **Coil** on a 3D printer so we can print a thin plastic layer upon
 * another layer until we have the model complete. The **Coils** are the storage for the plastic. It comes in long mono filament plastic lines of
 * thousands of metres of length. Usually they are bought by weight.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.1.0
 */
@Entity
@Table(name = "coils", schema = "printer3d")
public class Coil {
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
	private Coil() {}

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
		if (!(o instanceof Coil)) return false;
		final Coil coil = (Coil) o;
		return new EqualsBuilder()
				.append( this.material, coil.material )
				.append( this.color, coil.color )
				.append( this.weight, coil.weight )
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

	/**
	 * Subtracts the grams of plastic from the selected Coil.
	 *
	 * @param weight the number og grams to remove from the coil estimated weight.
	 * @return the updated coil instance.
	 */
	public Coil subtractMaterial( final Integer weight ) {
		LogWrapper.info( "Selected coil: " + this.toString() );
		LogWrapper.info( "Subtracting plastic: " + weight );
		this.weight -= weight;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private Coil onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Coil();
		}

		public Coil build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.material );
			Objects.requireNonNull( this.onConstruction.color );
			return this.onConstruction;
		}

		public Coil.Builder withColor( final String color ) {
			this.onConstruction.color = Objects.requireNonNull( color );
			return this;
		}

		public Coil.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public Coil.Builder withMaterial( final String material ) {
			this.onConstruction.material = Objects.requireNonNull( material );
			return this;
		}

		public Coil.Builder withWeight( final Integer weight ) {
			if (null != weight) this.onConstruction.weight = weight;
			return this;
		}
	}
}
