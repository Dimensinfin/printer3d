package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.concurrent.Immutable;
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
@Immutable
public class Part {
	@NotNull(message = "Part unique UUID 'id' is a mandatory field and cannot be null.")
	private UUID id;
	@NotNull(message = "Part 'label' is mandatory.")
	@Size(min = 3, max = 50)
	private String label;
	@Size(min = 3, max = 50)
	private String project;
	@Size(max = 500)
	private String description;
	@NotNull(message = "Part 'material' is mandatory.")
	@Size(max = 16)
	private String material;
	@NotNull(message = "Part 'color' is mandatory.")
	@Size(max = 32)
	private String color;
	private int weight = 1;
	@NotNull(message = "Part 'buildTime' is mandatory.")
	private Integer buildTime;
	@NotNull(message = "Part 'cost' value is mandatory.")
	private Float cost;
	@NotNull(message = "Part 'price' value is mandatory.")
	private Float price;
	private Integer stockLevel = 0;
	private int stockAvailable = 0;
	@Size(max = 1000)
	private String imagePath;
	@Size(max = 1000)
	private String modelPath;
	private boolean active = true;
	private boolean unavailable = false;

	// - C O N S T R U C T O R S
	private Part() {}

	// - G E T T E R S   &   S E T T E R S
	public Integer getBuildTime() {
		return buildTime;
	}

	public String getColor() {
		return color;
	}

	public Float getCost() {
		return cost;
	}

	public Part setCost( final Float cost ) {
		this.cost = cost;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public UUID getId() {
		return id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getLabel() {
		return label;
	}

	public String getMaterial() {
		return material;
	}

	public String getModelPath() {
		return modelPath;
	}

	public Float getPrice() {
		return price;
	}

	public Part setPrice( final Float price ) {
		this.price = price;
		return this;
	}

	public String getProject() {
		return project;
	}

	public int getStockAvailable() {
		return stockAvailable;
	}

	public Part setStockAvailable( final int stockAvailable ) {
		this.stockAvailable = stockAvailable;
		return this;
	}

	public Integer getStockLevel() {
		return stockLevel;
	}

	public Part setStockLevel( final Integer stockLevel ) {
		this.stockLevel = stockLevel;
		return this;
	}

	public int getWeight() {
		return weight;
	}

	public boolean isActive() {
		return active;
	}

	public Part setActive( final boolean active ) {
		this.active = active;
		return this;
	}

	public boolean isUnavailable() {
		return unavailable;
	}

	public Part setUnavailable( final boolean unavailable ) {
		this.unavailable = unavailable;
		return this;
	}

	// - C O R E
	@Override
	public final int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( label )
				.append( project )
				.append( description )
				.append( material )
				.append( color )
				.append( weight )
				.append( buildTime )
				.append( cost )
				.append( price )
				.append( stockLevel )
				.append( stockAvailable )
				.append( imagePath )
				.append( modelPath )
				.append( active )
				.toHashCode();
	}

	@Override
	public final boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof Part)) return false;
		final Part part = (Part) o;
		return new EqualsBuilder()
				.append( stockAvailable, part.stockAvailable )
				.append( active, part.active )
				.append( label, part.label )
				.append( project, part.project )
				.append( description, part.description )
				.append( material, part.material )
				.append( color, part.color )
				.append( weight, part.weight )
				.append( buildTime, part.buildTime )
				.append( cost, part.cost )
				.append( price, part.price )
				.append( stockLevel, part.stockLevel )
				.append( imagePath, part.imagePath )
				.append( modelPath, part.modelPath )
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", id )
				.append( "label", label )
				.append( "project", project )
				.append( "description", description )
				.append( "material", material )
				.append( "color", color )
				.append( "weight", weight )
				.append( "buildTime", buildTime )
				.append( "cost", cost )
				.append( "price", price )
				.append( "stockLevel", stockLevel )
				.append( "stockAvailable", stockAvailable )
				.append( "imagePath", imagePath )
				.append( "modelPath", modelPath )
				.append( "active", active )
				.toString();
	}

	public Part incrementStock( final int increment ) {
		stockAvailable += increment;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private final Part onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			onConstruction = new Part();
		}

		public Part build() {
			Objects.requireNonNull( onConstruction.id );
			Objects.requireNonNull( onConstruction.label );
			Objects.requireNonNull( onConstruction.material );
			Objects.requireNonNull( onConstruction.color );
			Objects.requireNonNull( onConstruction.buildTime );
			Objects.requireNonNull( onConstruction.cost );
			Objects.requireNonNull( onConstruction.price );
			Objects.requireNonNull( onConstruction.stockLevel );
			return onConstruction;
		}

		public Part.Builder withActive( final Boolean active ) {
			if (null != active) onConstruction.active = active;
			return this;
		}

		public Part.Builder withBuildTime( final Integer buildTime ) {
			onConstruction.buildTime = Objects.requireNonNull( buildTime );
			return this;
		}

		public Part.Builder withColor( final String color ) {
			onConstruction.color = Objects.requireNonNull( color );
			return this;
		}

		public Part.Builder withCost( final Float cost ) {
			onConstruction.cost = Objects.requireNonNull( cost );
			return this;
		}

		public Part.Builder withDescription( final String description ) {
			if (null != description) onConstruction.description = description;
			return this;
		}

		public Part.Builder withId( final UUID id ) {
			onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public Part.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) onConstruction.imagePath = imagePath;
			return this;
		}

		public Part.Builder withLabel( final String label ) {
			onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public Part.Builder withMaterial( final String material ) {
			onConstruction.material = Objects.requireNonNull( material );
			return this;
		}

		public Part.Builder withModelPath( final String modelPath ) {
			if (null != modelPath) onConstruction.modelPath = modelPath;
			return this;
		}

		public Part.Builder withPrice( final Float price ) {
			onConstruction.price = Objects.requireNonNull( price );
			return this;
		}

		public Part.Builder withProject( final String project ) {
			if (null != project) onConstruction.project = project;
			return this;
		}

		public Part.Builder withStockAvailable( final Integer stockAvailable ) {
			if (null != stockAvailable) onConstruction.stockAvailable = stockAvailable;
			return this;
		}

		public Part.Builder withStockLevel( final Integer stockLevel ) {
			if (null != stockLevel) onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}

		public Part.Builder withUnavailable( final Boolean unavailable ) {
			if (null != unavailable) onConstruction.unavailable = unavailable;
			return this;
		}

		public Part.Builder withWeight( final Integer weight ) {
			if (null != weight) onConstruction.weight = Objects.requireNonNull( weight );
			return this;
		}
	}
}
