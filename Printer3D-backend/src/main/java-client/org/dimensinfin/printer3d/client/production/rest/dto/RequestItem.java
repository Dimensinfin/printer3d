package org.dimensinfin.printer3d.client.production.rest.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;

/**
 * Now a Request item can be of different types. So there is a need to use an isolation class that will contain the neutral data and be ale to
 * locate the detail record form the right repository. So the list of Parts now is transformed to a list of items.
 * A request item then has the type of item and the quantity of the same items.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.8.0
 */
public class RequestItem implements Serializable {
	private static final long serialVersionUID = -4342163404948952440L;
	@NotNull(message = "Request content unique id 'itemId' is a mandatory field and cannot be null.")
	@SerializedName("itemId")
	private UUID itemId;
	//	@NotNull(message = "Request content 'type' should not be null not UNIDENTIFIED.")
	@SerializedName("type")
	private RequestContentType type = RequestContentType.UNIDENTIFIED;
	@NotNull(message = "Request content 'quantity' should not be undefined. Default will be 1.")
	@SerializedName("quantity")
	private int quantity;
	@SerializedName("missing")
	private int missing;

	// - C O N S T R U C T O R S
	private RequestItem() {}

	// - G E T T E R S   &   S E T T E R S
	public UUID getItemId() {
		return this.itemId;
	}

	public int getMissing() {
		return this.missing;
	}

	public RequestItem setMissing( final int missing ) {
		this.missing = missing;
		return this;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public RequestContentType getType() {
		return this.type;
	}

	// - C O R E
	@Override
	public String toString() {
		return new ToStringBuilder( this , ToStringStyle.JSON_STYLE)
				.append( "itemId", this.itemId )
				.append( "type", this.type )
				.append( "quantity", this.quantity )
				.append( "missing", this.missing )
				.toString();
	}

	// - B U I L D E R
	public static class Builder {
		private final RequestItem onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new RequestItem();
		}

		public RequestItem build() {
			Objects.requireNonNull( this.onConstruction.itemId );
			if (this.onConstruction.type == RequestContentType.UNIDENTIFIED)
				throw new DimensinfinRuntimeException( DimensinfinRuntimeException.errorRUNTIMEINTERNALERROR(
						"Request content type found UNIDENTIFIED while constructing a RequestItem."
				) );
			return this.onConstruction;
		}

		public RequestItem.Builder withItemId( final UUID itemId ) {
			this.onConstruction.itemId = Objects.requireNonNull( itemId );
			return this;
		}

		public RequestItem.Builder withQuantity( final Integer quantity ) {
			this.onConstruction.quantity = Objects.requireNonNull( quantity );
			return this;
		}

		public RequestItem.Builder withType( final RequestContentType type ) {
			this.onConstruction.type = Objects.requireNonNull( type );
			return this;
		}
	}
}
