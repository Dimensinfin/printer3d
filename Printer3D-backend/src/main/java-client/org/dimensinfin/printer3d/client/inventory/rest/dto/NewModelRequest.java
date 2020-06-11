package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;
import java.util.UUID;

public class NewModelRequest {
	private UUID id;
	private String label;
	private Float price;
	private int stockLevel = 1;
	private int stockAvailable = 0;
	private String imagePath;

	// - C O N S T R U C T O R S
	private NewModelRequest() {}

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

	public Float getPrice() {
		return this.price;
	}

	public int getStockAvailable() {
		return this.stockAvailable;
	}

	public int getStockLevel() {
		return this.stockLevel;
	}

	// - B U I L D E R
	public static class Builder {
		private final NewModelRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new NewModelRequest();
		}


		public NewModelRequest build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.price );
			Objects.requireNonNull( this.onConstruction.stockLevel );
			return this.onConstruction;
		}

		public NewModelRequest.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public NewModelRequest.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) this.onConstruction.imagePath = imagePath;
			return this;
		}

		public NewModelRequest.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public NewModelRequest.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}

		public NewModelRequest.Builder withStockAvailable( final Integer stockAvailable ) {
			if (null != stockAvailable) this.onConstruction.stockAvailable = stockAvailable;
			return this;
		}

		public NewModelRequest.Builder withStockLevel( final Integer stockLevel ) {
			this.onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}
	}
}
