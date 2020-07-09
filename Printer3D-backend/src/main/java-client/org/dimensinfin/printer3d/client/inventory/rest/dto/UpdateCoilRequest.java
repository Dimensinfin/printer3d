package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Contains the Coil fields that come from the GUI with the new Coil data.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.10.0
 */
public class UpdateCoilRequest {
	@SerializedName("id")
	private UUID id = null;
	@SerializedName("weight")
	private Integer weight;

	// - G E T T E R S   &   S E T T E R S
	public UUID getId() {
		return this.id;
	}

	public Integer getWeight() {
		return this.weight;
	}

	// - C O R E
	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( this.weight )
				.toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof UpdateCoilRequest)) return false;
		final UpdateCoilRequest that = (UpdateCoilRequest) o;
		return new EqualsBuilder()
				.append( this.weight, that.weight )
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", id )
				.append( "weight", weight )
				.toString();
	}

	// - B U I L D E R
	public static class Builder {
		private final UpdateCoilRequest onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new UpdateCoilRequest();
		}

		public UpdateCoilRequest build() {
			return this.onConstruction;
		}

		public UpdateCoilRequest.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public UpdateCoilRequest.Builder withWeight( final Integer weight ) {
			this.onConstruction.weight = Objects.requireNonNull( weight );
			return this;
		}
	}
}

