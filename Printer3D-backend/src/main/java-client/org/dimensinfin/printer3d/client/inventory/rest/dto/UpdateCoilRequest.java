package org.dimensinfin.printer3d.client.inventory.rest.dto;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;

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
@Immutable
public class UpdateCoilRequest {
	@NotNull(message = "Coil 'id' is a mandatory field used to locate the target and caanot be null.")
	@SerializedName("id")
	private UUID id;
	@NotNull(message = "Weight data cannot be null.")
	@SerializedName("weight")
	private Integer weight;
	private String label;
	private Boolean active;

	// - G E T T E R S   &   S E T T E R S
	public Boolean getActive() {
		return this.active;
	}

	public UUID getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public Integer getWeight() {
		return this.weight;
	}

	// - C O R E
	@Override
	public int hashCode() {
		return new HashCodeBuilder( 17, 37 )
				.append( this.weight )
				.append( this.label )
				.append( this.active )
				.toHashCode();
	}

	@Override
	public boolean equals( final Object o ) {
		if (this == o) return true;
		if (!(o instanceof UpdateCoilRequest)) return false;
		final UpdateCoilRequest that = (UpdateCoilRequest) o;
		return new EqualsBuilder()
				.append( this.weight, that.weight )
				.append( this.label, that.label )
				.append( this.active, that.active )
				.isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder( this, ToStringStyle.JSON_STYLE )
				.append( "id", this.id )
				.append( "weight", this.weight )
				.append( "color", this.label )
				.append( "active", this.active )
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

		public UpdateCoilRequest.Builder withActive( final Boolean active ) {
			if (null != active) this.onConstruction.active = active;
			return this;
		}

		public UpdateCoilRequest.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public UpdateCoilRequest.Builder withLabel( final String label ) {
			if (null != label) this.onConstruction.label = label;
			return this;
		}

		public UpdateCoilRequest.Builder withWeight( final Integer weight ) {
			this.onConstruction.weight = weight;
			return this;
		}
	}
}

