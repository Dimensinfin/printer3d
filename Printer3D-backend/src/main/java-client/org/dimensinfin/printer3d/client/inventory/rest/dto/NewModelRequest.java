package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

public class NewModelRequest {
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

	public List<UUID> getPartIdList() {
		return this.partIdList;
	}

	public Float getPrice() {
		return this.price;
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

		public NewModelRequest.Builder withPartIdList( final List<UUID> partIdList ) {
			if (null != partIdList) this.onConstruction.partIdList = partIdList;
			return this;
		}

		public NewModelRequest.Builder withPrice( final Float price ) {
			this.onConstruction.price = Objects.requireNonNull( price );
			return this;
		}

		public NewModelRequest.Builder withStockLevel( final Integer stockLevel ) {
			this.onConstruction.stockLevel = Objects.requireNonNull( stockLevel );
			return this;
		}
	}
}
