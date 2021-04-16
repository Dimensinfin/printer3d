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

/**
 * This is the definition of an item that can be sold independently. The Part is the view side from the 3D printer stand view.
 * Parts entities have some aspects. The principal aspect is the point where it collects the information required by the Machine to build an
 * element. Parts then define the materials required for the construction and the model to use to give the material the appearance required. Parts
 * define the time slot use of the 3D printer and have some cost and price attached. The Part will also store the stock information so for active
 * parts this information is used to calculate the jobs pending to stabilize a stock level.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.6.0
 */
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
	@NotNull(message = "Part 'project' is mandatory but has default value.")
	@Size(min = 3, max = 50)
	@Column(name = "project", nullable = false)
	private String project = "<DEFAULT>";
	@Size(max = 500)
	@Column(name = "description")
	private String description;
	@NotNull(message = "Part 'material' is mandatory.")
	@Size(max = 16)
	@Column(name = "material", nullable = false)
	private String material;
	@NotNull(message = "Part 'color' is mandatory.")
	@Size(max = 32)
	@Column(name = "color", nullable = false)
	private String color;
	@NotNull(message = "Part 'weight' is mandatory.")
	@Column(name = "weight", nullable = false)
	private Integer weight = 1;
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
	@Column(name = "stock_available", nullable = false)
	private int stockAvailable = 0;
	@Size(max = 1000)
	@Column(name = "image_path")
	private String imagePath;
	@Size(max = 1000)
	@Column(name = "model_path")
	private String modelPath;
	@Column(name = "active", nullable = false)
	private boolean active = true;

	// - C O N S T R U C T O R S
	private PartEntity() {}

	// - G E T T E R S   &   S E T T E R S
	public Integer getBuildTime() {
		return buildTime;
	}

	public PartEntity setBuildTime( final Integer buildTime ) {
		this.buildTime = buildTime;
		return this;
	}

	public String getColor() {
		return color;
	}

	public Float getCost() {
		return cost;
	}

	public PartEntity setCost( final Float cost ) {
		this.cost = cost;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public PartEntity setDescription( final String description ) {
		this.description = description;
		return this;
	}

	public UUID getId() {
		return id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public PartEntity setImagePath( final String imagePath ) {
		this.imagePath = imagePath;
		return this;
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

	public PartEntity setModelPath( final String modelPath ) {
		this.modelPath = modelPath;
		return this;
	}

	public Float getPrice() {
		return price;
	}

	public PartEntity setPrice( final Float price ) {
		this.price = price;
		return this;
	}

	public String getProject() {
		return project;
	}

	public PartEntity setProject( final String project ) {
		this.project = project;
		return this;
	}

	public int getStockAvailable() {
		return stockAvailable;
	}

	public PartEntity setStockAvailable( final int stockAvailable ) {
		this.stockAvailable = stockAvailable;
		return this;
	}

	public Integer getStockLevel() {
		return stockLevel;
	}

	public PartEntity setStockLevel( final Integer stockLevel ) {
		this.stockLevel = stockLevel;
		return this;
	}

	public Integer getWeight() {
		return weight;
	}

	public PartEntity setWeight( final Integer weight ) {
		this.weight = weight;
		return this;
	}

	public boolean isActive() {
		return active;
	}

	public PartEntity setActive( final boolean active ) {
		this.active = active;
		return this;
	}

	public int decrementStock( final Integer quantity ) {
		stockAvailable -= quantity;
		return stockAvailable;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( label )
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
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof PartEntity)) return false;
		final PartEntity part = (PartEntity) o;
		return new EqualsBuilder()
				.append( stockAvailable, part.stockAvailable )
				.append( active, part.active )
				.append( label, part.label )
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

	public PartEntity incrementStock( final int increment ) {
		stockAvailable += increment;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private final PartEntity onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			onConstruction = new PartEntity();
		}

		public PartEntity build() {
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

		public PartEntity.Builder withActive( final Boolean active ) {
			if (null != active) onConstruction.active = active;
			return this;
		}

		public PartEntity.Builder withBuildTime( final Integer buildTime ) {
			onConstruction.buildTime = Objects.requireNonNull( buildTime );
			return this;
		}

		public PartEntity.Builder withColor( final String color ) {
			onConstruction.color = Objects.requireNonNull( color );
			return this;
		}

		public PartEntity.Builder withCost( final Float cost ) {
			onConstruction.cost = Objects.requireNonNull( cost );
			return this;
		}

		public PartEntity.Builder withDescription( final String description ) {
			if (null != description) onConstruction.description = description;
			return this;
		}

		public PartEntity.Builder withId( final UUID id ) {
			onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public PartEntity.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) onConstruction.imagePath = imagePath;
			return this;
		}

		public PartEntity.Builder withLabel( final String label ) {
			onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public PartEntity.Builder withMaterial( final String material ) {
			onConstruction.material = Objects.requireNonNull( material );
			return this;
		}

		public PartEntity.Builder withModelPath( final String modelPath ) {
			if (null != modelPath) onConstruction.modelPath = modelPath;
			return this;
		}

		public PartEntity.Builder withPrice( final Float price ) {
			onConstruction.price = Objects.requireNonNull( price );
			return this;
		}

		public PartEntity.Builder withStockAvailable( final Integer stockAvailable ) {
			if (null != stockAvailable) onConstruction.stockAvailable = stockAvailable;
			return this;
		}

		public PartEntity.Builder withStockLevel( final Integer stockLevel ) {
			onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}

		public PartEntity.Builder withWeight( final Integer weight ) {
			if (null != weight) onConstruction.weight = Objects.requireNonNull( weight );
			return this;
		}
	}
}
