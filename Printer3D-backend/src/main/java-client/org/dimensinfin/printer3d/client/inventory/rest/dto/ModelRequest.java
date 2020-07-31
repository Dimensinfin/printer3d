package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

public class ModelRequest {
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
	@SerializedName("active")
	private boolean active = true;

	// - C O N S T R U C T O R S
	private ModelRequest() {}

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

	public boolean isActive() {
		return this.active;
	}

	public int getStockLevel() {
		return this.stockLevel;
	}

	// - B U I L D E R
	public static class Builder {
		private final ModelRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new ModelRequest();
		}


		public ModelRequest build() {
			Objects.requireNonNull( this.onConstruction.id );
			Objects.requireNonNull( this.onConstruction.label );
			Objects.requireNonNull( this.onConstruction.price );
			return this.onConstruction;
		}

		public ModelRequest.Builder withActive( final Boolean active ) {
			if (null != active) this.onConstruction.active = active;
			return this;
		}

		public ModelRequest.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public ModelRequest.Builder withImagePath( final String imagePath ) {
			if (null != imagePath) this.onConstruction.imagePath = imagePath;
			return this;
		}

		public ModelRequest.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public ModelRequest.Builder withPartIdList( final List<UUID> partIdList ) {
			if (null != partIdList) this.onConstruction.partIdList = partIdList;
			return this;
		}

		public ModelRequest.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}

		public ModelRequest.Builder withStockLevel( final Integer stockLevel ) {
			this.onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}
	}
}
