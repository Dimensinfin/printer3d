package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This is the definition of an item that can be sold independently. The Part is the view side from the 3D printer stand view.
 * Parts entities have some aspects. The principal aspect is the point where it collects the information required by the Machine to build an
 * element. Parts then define the materials required for the construction and the model to use to give the material the appearance required. Parts
 * define the time slot use of the 3D printer and have some cost and price attached. The Part will also store the stock information so for active
 * parts this information is used to calculate the jobs pending to stabilize a stock level.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.1.0
 */
public class Part {
	@NotNull(message = "Part unique UUID 'id' is a mandatory field and cannot be null.")
	private UUID id;
	@NotNull(message = "Part 'label' is mandatory.")
	@Size(min = 3, max = 50)
	private String label;
	@Size(max = 500)
	private String description;
	@NotNull(message = "Part 'material' is mandatory.")
	private String material;
	@NotNull(message = "Part 'color' is mandatory.")
	private String color;
	@NotNull(message = "Part 'buildTime' is mandatory.")
	private Integer buildTime;
	@NotNull(message = "Part 'cost' value is mandatory.")
	private Float cost;
	@NotNull(message = "Part 'price' value is mandatory.")
	private Float price;
	@NotNull(message = "Part 'stockLevel' value is mandatory.")
	private Integer stockLevel;
	private int stockAvailable = 0;
	@Size(max = 100)
	private String imagePath;
	@Size(max = 100)
	private String modelPath;
	private boolean active = true;

	// - C O N S T R U C T O R S
	private Part() {}

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

	public Part setCost( final Float cost ) {
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

	public Part setPrice( final Float price ) {
		this.price = price;
		return this;
	}

	public int getStockAvailable() {
		return this.stockAvailable;
	}

	public Part setStockAvailable( final int stockAvailable ) {
		this.stockAvailable = stockAvailable;
		return this;
	}

	public Integer getStockLevel() {
		return this.stockLevel;
	}

	public Part setStockLevel( final Integer stockLevel ) {
		this.stockLevel = stockLevel;
		return this;
	}

	public boolean isActive() {
		return this.active;
	}

	public Part setActive( final boolean active ) {
		this.active = active;
		return this;
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
		if (!(o instanceof Part)) return false;
		final Part part = (Part) o;
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

	public Part incrementStock( final int increment ) {
		this.stockAvailable += increment;
		return this;
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
			Objects.requireNonNull( this.onConstruction.material );
			Objects.requireNonNull( this.onConstruction.color );
			Objects.requireNonNull( this.onConstruction.buildTime );
			Objects.requireNonNull( this.onConstruction.cost );
			Objects.requireNonNull( this.onConstruction.price );
			Objects.requireNonNull( this.onConstruction.stockLevel );
			return this.onConstruction;
		}

		public Part.Builder withActive( final Boolean active ) {
			if (null != active) this.onConstruction.active = active;
			return this;
		}

		public Part.Builder withBuildTime( final Integer buildTime ) {
			this.onConstruction.buildTime = Objects.requireNonNull( buildTime );
			return this;
		}

		public Part.Builder withColor( final String color ) {
			this.onConstruction.color = Objects.requireNonNull( color );
			return this;
		}

		public Part.Builder withCost( final Float cost ) {
			this.onConstruction.cost = Objects.requireNonNull( cost );
			return this;
		}

		public Part.Builder withDescription( final String description ) {
			if (null != description) this.onConstruction.description = description;
			return this;
		}

		public Part.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public Part.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) this.onConstruction.imagePath = imagePath;
			return this;
		}

		public Part.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public Part.Builder withMaterial( final String material ) {
			this.onConstruction.material = Objects.requireNonNull( material );
			return this;
		}

		public Part.Builder withModelPath( final String modelPath ) {
			if (null != modelPath) this.onConstruction.modelPath = modelPath;
			return this;
		}

		public Part.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}

		public Part.Builder withStockAvailable( final Integer stockAvailable ) {
			if (null != stockAvailable) this.onConstruction.stockAvailable = stockAvailable;
			return this;
		}

		public Part.Builder withStockLevel( final Integer stockLevel ) {
			this.onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}
	}
}
