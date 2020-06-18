package org.dimensinfin.printer3d.backend.inventory.part.persistence;

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
@Table(name = "parts", schema = "printer3d")
public class PartEntity {
	@Id
	@NotNull(message = "Part unique UUID 'id' is a mandatory field and cannot be null.")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@NotNull(message = "Part 'label' is mandatory.")
	@Size(min = 3, max = 50)
	@Column(name = "label", updatable = false, nullable = false)
	private String label;
	@Size(max = 500)
	@Column(name = "description")
	private String description;
	@NotNull(message = "Part 'material' is mandatory.")
	@Column(name = "material", nullable = false)
	private String material;
	@NotNull(message = "Part 'color' is mandatory.")
	@Column(name = "color", nullable = false)
	private String color;
	@NotNull(message = "Part 'buildTime' is mandatory.")
	@Column(name = "build_time", nullable = false)
	private Integer buildTime;
	@NotNull(message = "Part 'cost' value is mandatory.")
	@Column(name = "cost", nullable = false)
	private Float cost;
	@NotNull(message = "Part 'price' value is mandatory.")
	@Column(name = "price", nullable = false)
	private Float price;
	@NotNull(message = "Part 'stockLevel' value is mandatory.")
	@Column(name = "stock_level", nullable = false)
	private Integer stockLevel;
	@NotNull(message = "Part 'stockAvailable' value is mandatory.")
	@Column(name = "stock_available", nullable = false)
	private int stockAvailable = 0;
	@Size(max = 100)
	@Column(name = "image_path")
	private String imagePath;
	@Size(max = 100)
	@Column(name = "model_path")
	private String modelPath;
	@Column(name = "active", nullable = false)
	private boolean active = true;

	// - C O N S T R U C T O R S
	private PartEntity() {}

	// - G E T T E R S   &   S E T T E R S
	public Integer getBuildTime() {
		return this.buildTime;
	}

	public String getColor() {
		return this.color;
	}

	public Float getCost() {
		return this.cost;
	}

	public PartEntity setCost( final Float cost ) {
		this.cost = cost;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public UUID getId() {
		return this.id;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public String getLabel() {
		return this.label;
	}

	public String getMaterial() {
		return material;
	}

	public String getModelPath() {
		return this.modelPath;
	}

	public Float getPrice() {
		return this.price;
	}

	public PartEntity setPrice( final Float price ) {
		this.price = price;
		return this;
	}

	public int getStockAvailable() {
		return this.stockAvailable;
	}

	public PartEntity setStockAvailable( final int stockAvailable ) {
		this.stockAvailable = stockAvailable;
		return this;
	}

	public Integer getStockLevel() {
		return this.stockLevel;
	}

	public PartEntity setStockLevel( final Integer stockLevel ) {
		this.stockLevel = stockLevel;
		return this;
	}

	public boolean isActive() {
		return this.active;
	}

	public PartEntity setActive( final boolean active ) {
		this.active = active;
		return this;
	}

	public int decrementStock( final Integer quantity ) {
		this.stockAvailable -= quantity;
		return this.stockAvailable;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( this.label )
				.append( this.description )
				.append( this.material )
				.append( this.color )
				.append( this.buildTime )
				.append( this.cost )
				.append( this.price )
				.append( this.stockLevel )
				.append( this.stockAvailable )
				.append( this.imagePath )
				.append( this.modelPath )
				.append( this.active )
				.toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof PartEntity)) return false;
		final PartEntity part = (PartEntity) o;
		return new EqualsBuilder()
				.append( this.stockAvailable, part.stockAvailable )
				.append( this.active, part.active )
				.append( this.label, part.label )
				.append( this.description, part.description )
				.append( this.material, part.material )
				.append( this.color, part.color )
				.append( this.buildTime, part.buildTime )
				.append( this.cost, part.cost )
				.append( this.price, part.price )
				.append( this.stockLevel, part.stockLevel )
				.append( this.imagePath, part.imagePath )
				.append( this.modelPath, part.modelPath )
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", this.id )
				.append( "label", this.label )
				.append( "description", this.description )
				.append( "material", this.material )
				.append( "color", this.color )
				.append( "buildTime", this.buildTime )
				.append( "cost", this.cost )
				.append( "price", this.price )
				.append( "stockLevel", this.stockLevel )
				.append( "stockAvailable", this.stockAvailable )
				.append( "imagePath", this.imagePath )
				.append( "modelPath", this.modelPath )
				.append( "active", this.active )
				.toString();
	}

	public PartEntity incrementStock( final int increment ) {
		this.stockAvailable += increment;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private final PartEntity onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new PartEntity();
		}

		public PartEntity build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.material );
			Objects.requireNonNull( this.onConstruction.color );
			Objects.requireNonNull( this.onConstruction.buildTime );
			Objects.requireNonNull( this.onConstruction.cost );
			Objects.requireNonNull( this.onConstruction.price );
			Objects.requireNonNull( this.onConstruction.stockLevel );
			return this.onConstruction;
		}

		public PartEntity.Builder withActive( final Boolean active ) {
			if (null != active) this.onConstruction.active = active;
			return this;
		}

		public PartEntity.Builder withBuildTime( final Integer buildTime ) {
			this.onConstruction.buildTime = Objects.requireNonNull( buildTime );
			return this;
		}

		public PartEntity.Builder withColor( final String color ) {
			this.onConstruction.color = Objects.requireNonNull( color );
			return this;
		}

		public PartEntity.Builder withCost( final Float cost ) {
			this.onConstruction.cost = Objects.requireNonNull( cost );
			return this;
		}

		public PartEntity.Builder withDescription( final String description ) {
			if (null != description) this.onConstruction.description = description;
			return this;
		}

		public PartEntity.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public PartEntity.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) this.onConstruction.imagePath = imagePath;
			return this;
		}

		public PartEntity.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public PartEntity.Builder withMaterial( final String material ) {
			this.onConstruction.material = Objects.requireNonNull( material );
			return this;
		}

		public PartEntity.Builder withModelPath( final String modelPath ) {
			if (null != modelPath) this.onConstruction.modelPath = modelPath;
			return this;
		}

		public PartEntity.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}

		public PartEntity.Builder withStockAvailable( final Integer stockAvailable ) {
			if (null != stockAvailable) this.onConstruction.stockAvailable = stockAvailable;
			return this;
		}

		public PartEntity.Builder withStockLevel( final Integer stockLevel ) {
			this.onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}
	}
}
