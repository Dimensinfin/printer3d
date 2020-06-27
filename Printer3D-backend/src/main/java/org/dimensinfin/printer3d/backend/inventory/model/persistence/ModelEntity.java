package org.dimensinfin.printer3d.backend.inventory.model.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 * A model is a collection of Parts that are required to build a new item. There are simple items that can be sell only by building
 * a Part but also there are complex elements that are composed of more than one Part. Each of the Parts can have their own color and material.
 * The Model has then a list of the Part references that should be composed at the UI and than can be modified but only on the list of Part
 * composition.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.5.1
 */
@Entity
@Table(name = "models", schema = "printer3d")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ModelEntity {
	@Id
	@NotNull(message = "Model unique UUID 'id' is a mandatory field and cannot be null.")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	@NotNull(message = "Part 'label' is mandatory.")
	@Size(min = 3, max = 50)
	@Column(name = "label", nullable = false)
	private String label;
	@Type(type = "jsonb")
	@Column(name = "part_list", columnDefinition = "jsonb")
	private List<UUID> partIdList = new ArrayList<>();
	@NotNull(message = "Part 'price' value is mandatory.")
	@Column(name = "price", nullable = false)
	private Float price;
	@NotNull(message = "Part 'stockLevel' value is mandatory.")
	@Column(name = "stock_level", nullable = false)
	private int stockLevel = 1;
	@Size(max = 200)
	@Column(name = "image_path")
	private String imagePath;
	@Column(name = "active", nullable = false)
	private boolean active = true;

	// - C O N S T R U C T O R S
	private ModelEntity() {
	}

	// - G E T T E R S   &   S E T T E R S
	public UUID getId() {
		return this.id;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public String getLabel() {
		return this.label;
	}

	public List<UUID> getPartIdList() {
		return this.partIdList;
	}

	public Float getPrice() {
		return this.price;
	}
	public int getStockLevel() {
		return this.stockLevel;
	}

	public boolean isActive() {
		return this.active;
	}

	public ModelEntity addPart( final UUID partId ) {
		this.partIdList.add( partId );
		return this;
	}

	// - C O R E
	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( this.label )
				.append( this.partIdList )
				.append( this.price )
				.append( this.stockLevel )
				.append( this.imagePath )
				.append( this.active )
				.toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;

		if (!(o instanceof ModelEntity)) return false;

		final ModelEntity that = (ModelEntity) o;

		return new EqualsBuilder()
				.append( this.stockLevel, that.stockLevel )
				.append( this.active, that.active )
				.append( this.label, that.label )
				.append( this.partIdList, that.partIdList )
				.append( this.price, that.price )
				.append( this.imagePath, that.imagePath )
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", this.id )
				.append( "label", this.label )
				.append( "partList", this.partIdList )
				.append( "price", this.price )
				.append( "stockLevel", this.stockLevel )
				.append( "imagePath", this.imagePath )
				.append( "active", this.active )
				.toString();
	}

	public ModelEntity removePart( final UUID partId ) {
		this.partIdList.remove( partId );
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private final ModelEntity onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new ModelEntity();
		}

		public ModelEntity build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.price );
			Objects.requireNonNull( this.onConstruction.stockLevel );
			Objects.requireNonNull( this.onConstruction.active );
			return this.onConstruction;
		}

		public ModelEntity.Builder withActive( final Boolean active ) {
			this.onConstruction.active = Objects.requireNonNull( active );
			return this;
		}

		public ModelEntity.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public ModelEntity.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) this.onConstruction.imagePath = imagePath;
			return this;
		}

		public ModelEntity.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public ModelEntity.Builder withPartIdList( final List<UUID> partIdList ) {
			if (null != partIdList) this.onConstruction.partIdList = partIdList;
			return this;
		}

		public ModelEntity.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}

		public ModelEntity.Builder withStockLevel( final Integer stockLevel ) {
			this.onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}
	}
}
