package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

/**
 * A model is a collection of Parts that are required to build a new item. There are simple items that can be sell only by building
 * a Part but also there are complex elements that are composed of more than one Part. Each of the Parts can have their own color and material.
 * The Model has then a list of the Part references that should be composed at the UI and than can be modified but only on the list of Part
 * composition.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.5.1
 */
public class Model {
	@NotNull(message = "Model unique UUID 'id' is a mandatory field and cannot be null.")
	@SerializedName("id")
	private UUID id;
	@NotNull(message = "Model 'label' is a mandatory.")
	@SerializedName("label")
	private String label;
	@SerializedName("partIdList")
	private List<UUID> partIdList = new ArrayList<>();
	@NotNull(message = "Model 'price' is a mandatory.")
	@SerializedName("price")
	private Float price;
	@NotNull(message = "Model 'stockLevel' is a mandatory.")
	@SerializedName("stockLevel")
	private int stockLevel = 1;
	@SerializedName("imagePath")
	private String imagePath;
	private boolean active = true;
	private transient List<Part> partList = new ArrayList<>();

	// - C O N S T R U C T O R S
	private Model() {}

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

	public List<Part> getPartList() {
		return this.partList;
	}

	public Float getPrice() {
		return this.price;
	}

	//	public int getStockAvailable() {
	//		return this.stockAvailable;
	//	}

	public int getStockLevel() {
		return this.stockLevel;
	}

	public boolean isActive() {
		return this.active;
	}

	public void addPart( final Part part ) {
		this.partList.add( part );
	}

	// - B U I L D E R
	public static class Builder {
		private final Model onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Model();
		}


		public Model build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.price );
			Objects.requireNonNull( this.onConstruction.stockLevel );
			Objects.requireNonNull( this.onConstruction.active );
			return this.onConstruction;
		}

		public Model.Builder withActive( final Boolean active ) {
			if (null != active) this.onConstruction.active = active;
			return this;
		}

		public Model.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public Model.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) this.onConstruction.imagePath = imagePath;
			return this;
		}

		public Model.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public Model.Builder withPartIdList( final List<UUID> partIdentifierList ) {
			this.onConstruction.partIdList = Objects.requireNonNull( partIdentifierList );
			return this;
		}

		public Model.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}

		//		public Model.Builder withStockAvailable( final Integer stockAvailable ) {
		//			if (null != stockAvailable) this.onConstruction.stockAvailable = stockAvailable;
		//			return this;
		//		}

		public Model.Builder withStockLevel( final Integer stockLevel ) {
			this.onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}
	}
}
