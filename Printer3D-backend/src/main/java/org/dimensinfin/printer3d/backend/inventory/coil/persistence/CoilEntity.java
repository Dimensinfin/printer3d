package org.dimensinfin.printer3d.backend.inventory.coil.persistence;

import java.time.Instant;
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

import org.dimensinfin.core.utility.DimObjects;
import org.dimensinfin.logging.LogWrapper;

/**
 * To build a Part we require to use a plastic filament **Coil** on a 3D printer so we can print a thin plastic layer upon
 * another layer until we have the model complete. The **Coils** are the storage for the plastic. It comes in long mono filament plastic lines of
 * thousands of metres of length. Usually they are bought by weight.
 *
 * v0.14.0
 * Added new columns to the Coils entity to allow the edition of the color label without loosing the color association.
 * Also added a flag to discard empty or rejected coils that are no longer usable.
 * Access to the model is now done through a view instead accessing the real table stucture.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.1.0
 */
@Entity
@Table(name = "coils", schema = "printer3d")
public class CoilEntity {
	@Id
	@NotNull(message = "CoilEntity unique UUID 'id' is a mandatory field and cannot be null.")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@Size(min = 1, max = 16)
	@NotNull(message = "CoilEntity 'material' is mandatory.")
	@Column(name = "material", nullable = false)
	private String material;
	@Size(min = 1, max = 32)
	@NotNull(message = "CoilEntity 'tradeMark' is mandatory.")
	@Column(name = "trade_mark", nullable = false)
	private String tradeMark;
	@Size(min = 1, max = 32)
	@NotNull(message = "CoilEntity 'color' is mandatory.")
	@Column(name = "color", nullable = false)
	private String color;
	@Column(name = "weight", nullable = false)
	private Integer weight = 750;
	@Size(min = 1, max = 50)
	@Column(name = "label", nullable = false)
	private String label;
	@Column(name = "active", nullable = false)
	private Boolean active = true;
	@Column(name = "destruction_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private Instant destructionTime;

	// - C O N S T R U C T O R S
	protected CoilEntity() {}

	// - G E T T E R S   &   S E T T E R S
	public Boolean getActive() {
		return this.active;
	}

	public CoilEntity setActive( final Boolean active ) {
		this.active = active;
		return this;
	}

	public String getColor() {
		return this.color;
	}

	public Instant getDestructionTime() {
		return this.destructionTime;
	}

	public CoilEntity setDestructionTime( final Instant destructionTime ) {
		this.destructionTime = destructionTime;
		return this;
	}

	public UUID getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public CoilEntity setLabel( final String label ) {
		this.label = label;
		return this;
	}

	public String getMaterial() {
		return this.material;
	}

	public String getTradeMark() {
		return this.tradeMark;
	}

	public CoilEntity setTradeMark( final String tradeMark ) {
		this.tradeMark = tradeMark;
		return this;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public CoilEntity setWeight( final Integer weight ) {
		this.weight = weight;
		return this;
	}

	// - C O R E
	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( this.material )
				.append( this.tradeMark )
				.append( this.label )
				.append( this.color )
				.append( this.weight )
				.append( this.active )
				.toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof CoilEntity)) return false;
		final CoilEntity coil = (CoilEntity) o;
		return new EqualsBuilder()
				.append( this.material, coil.material )
				.append( this.tradeMark, coil.tradeMark )
				.append( this.label, coil.label )
				.append( this.color, coil.color )
				.append( this.weight, coil.weight )
				.append( this.active, coil.active )
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", this.id )
				.append( "material", this.material )
				.append( "tradeMark", this.tradeMark )
				.append( "color", this.color )
				.append( "weight", this.weight )
				.append( "label", this.label )
				.append( "active", this.active )
				.toString();
	}

	/**
	 * Subtracts the grams of plastic from the selected Coil.
	 *
	 * @param weight the number og grams to remove from the coil estimated weight.
	 * @return the updated coil instance.
	 */
	public CoilEntity subtractMaterial( final Integer weight ) {
		LogWrapper.info( "Selected coil: " + this.toString() );
		LogWrapper.info( "Subtracting plastic: " + weight );
		this.weight -= weight;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private final CoilEntity onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new CoilEntity();
		}

		public CoilEntity build() {
			DimObjects.requireNonNull( this.onConstruction.id );
			DimObjects.requireNonNull( this.onConstruction.material );
			DimObjects.requireNonNull( this.onConstruction.tradeMark );
			DimObjects.requireNonNull( this.onConstruction.color );
			if (null == this.onConstruction.label) this.onConstruction.label = this.onConstruction.color;
			return this.onConstruction;
		}

		public CoilEntity.Builder withActive( final boolean active ) {
			this.onConstruction.active = active;
			return this;
		}

		public CoilEntity.Builder withColor( final String color ) {
			this.onConstruction.color = DimObjects.requireNonNull( color );
			return this;
		}

		public CoilEntity.Builder withId( final UUID id ) {
			this.onConstruction.id = DimObjects.requireNonNull( id );
			return this;
		}

		public CoilEntity.Builder withLabel( final String label ) {
			if (null != label) this.onConstruction.label = label;
			return this;
		}

		public CoilEntity.Builder withMaterial( final String material ) {
			this.onConstruction.material = DimObjects.requireNonNull( material );
			return this;
		}

		public CoilEntity.Builder withTradeMark( final String trademark ) {
			this.onConstruction.tradeMark = DimObjects.requireNonNull( trademark );
			return this;
		}

		public CoilEntity.Builder withWeight( final Integer weight ) {
			if (null != weight) this.onConstruction.weight = weight;
			return this;
		}
	}
}
