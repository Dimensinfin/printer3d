package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.annotations.SerializedName;

import org.dimensinfin.printer3d.backend.exception.LogWrapperLocal;

public class Request {
	@NotNull(message = "Request unique UUID 'id' is a mandatory field and cannot be null.")
	@SerializedName("id")
	private UUID id;
	@NotNull(message = "Request 'label' is mandatory.")
	@Size(min = 3, max = 50)
	@SerializedName("label")
	private String label;
	/**
	 * To avoid using dates on the frontend this date is set automatically when the record is persisted. This field is only used for output the
	 * persisted request date that is present at the repository record.
	 */
	@SerializedName("requestDate")
	private String requestDate;
	@SerializedName("state")
	private RequestState state = RequestState.OPEN;
	@NotNull(message = "The list of Parts is required on the request.")
	@SerializedName("partList")
	private List<PartRequest> partList = new ArrayList<PartRequest>();

	// - C O N S T R U C T O R S
	private Request() {}

	// - G E T T E R S   &   S E T T E R S
	public UUID getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public List<PartRequest> getPartList() {
		return this.partList;
	}

	public String getRequestDate() {
		return this.requestDate;
	}

	public RequestState getState() {
		return this.state;
	}

	// - B U I L D E R
	public static class Builder {
		private final Request onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new Request();
		}

		public Request build() {
			return this.onConstruction;
		}

		public Request.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public Request.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public Request.Builder withPartList( final List<PartRequest> partList ) {
			this.onConstruction.partList = Objects.requireNonNull( partList );
			return this;
		}

		public Request.Builder withRequestDate( final String requestDate ) {
			LogWrapperLocal.info( "RequestDate: " + requestDate );
			this.onConstruction.requestDate = Objects.requireNonNull( requestDate );
			return this;
		}

		public Request.Builder withState( final RequestState state ) {
			this.onConstruction.state = Objects.requireNonNull( state );
			return this;
		}
	}
}
