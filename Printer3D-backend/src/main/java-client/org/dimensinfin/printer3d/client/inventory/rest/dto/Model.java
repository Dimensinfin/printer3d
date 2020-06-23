package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Model {
	private UUID id;
	private String label;
	private List<UUID> partIdentifierList = new ArrayList<>();
	private transient List<Part> partList = new ArrayList<>();
	private Float price;
	private int stockLevel = 1;
//	private int stockAvailable = 0;
	private String imagePath;
	private boolean active = true;

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

	public List<UUID> getPartIdentifierList() {
		return this.partIdentifierList;
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

		public Model.Builder withPartIdentifierList( final List<UUID> partIdentifierList ) {
			this.onConstruction.partIdentifierList = Objects.requireNonNull( partIdentifierList );
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
