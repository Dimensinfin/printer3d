package org.dimensinfin.printer3d.client.production.rest.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public class PartRequest implements Serializable {
	private static final long serialVersionUID = 4781734125159009614L;
	@SerializedName("partId")
	private UUID partId;
	@SerializedName("quantity")
	private Integer quantity = 1;

	// - C O N S T R U C T O R S
	private PartRequest() {}

	// - G E T T E R S   &   S E T T E R S
	public UUID getPartId() {
		return this.partId;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	// - B U I L D E R
	public static class Builder {
		private final PartRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new PartRequest();
		}

		public PartRequest build() {
			Objects.requireNonNull( this.onConstruction.partId );
			Objects.requireNonNull( this.onConstruction.quantity );
			return this.onConstruction;
		}

		public PartRequest.Builder withPartId( final UUID partId ) {
			this.onConstruction.partId = Objects.requireNonNull( partId );
			return this;
		}

		public PartRequest.Builder withQuantity( final Integer quantity ) {
			this.onConstruction.quantity = Objects.requireNonNull( quantity );
			return this;
		}
	}
}
