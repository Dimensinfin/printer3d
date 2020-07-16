package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.annotations.SerializedName;

import org.dimensinfin.printer3d.backend.exception.LogWrapperLocal;

/**
 * This is the data structure that is received/sent over the HTTP interface. When the user creates or changes the Request the service will
 * receive a copy of this structure and then return an updated and persisted copy.
 * Version 2 requests are able to contain any type of element and not only Parts. The contents definitions then changes to allow this different
 * way to store the request contents information. Persisted records of version 2 are not compatible with persisted records of version 1 so
 * records of version 1 are expected to be closed and not to be returned on the request.
 * If anyway the system still has V1 records they are converted to the V2 format (part identifiers are wrapped again) and then processed and
 * reported to the frontend.
 *
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.8.0
 */
public class RequestV2 {
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
	/**
	 * This field has changed from version 1 to be flexible on the type of elements it contains. Version 1 records can be transformed to version
	 * 2 on the flow so the client will be able to reuse old records until they are closed. Anyway there are two Request repositories that need
	 * independent management until one is removed.
	 */
	@NotNull(message = "The list of Contents is required on the request.")
	@SerializedName("contents")
	private List<RequestItem> contents = new ArrayList<>();
	@SerializedName("amount")
	private float amount;

	// - C O N S T R U C T O R S
	private RequestV2() {}

	// - G E T T E R S   &   S E T T E R S
	public float getAmount() {
		return this.amount;
	}

	public List<RequestItem> getContents() {
		return this.contents;
	}

	public UUID getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

	public String getRequestDate() {
		return this.requestDate;
	}

	public RequestState getState() {
		return this.state;
	}

	// - B U I L D E R
	public static class Builder {
		private final RequestV2 onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new RequestV2();
		}

		public RequestV2 build() {
			return this.onConstruction;
		}

		public RequestV2.Builder withAmount( final Float amount ) {
			this.onConstruction.amount = Objects.requireNonNull( amount );
			return this;
		}

		public RequestV2.Builder withContents( final List<RequestItem> contents ) {
			this.onConstruction.contents = Objects.requireNonNull( contents );
			return this;
		}

		public RequestV2.Builder withId( final UUID id ) {
			this.onConstruction.id = Objects.requireNonNull( id );
			return this;
		}

		public RequestV2.Builder withLabel( final String label ) {
			this.onConstruction.label = Objects.requireNonNull( label );
			return this;
		}

		public RequestV2.Builder withRequestDate( final String requestDate ) {
			LogWrapperLocal.info( "RequestDate: " + requestDate );
			this.onConstruction.requestDate = Objects.requireNonNull( requestDate );
			return this;
		}

		public RequestV2.Builder withState( final RequestState state ) {
			this.onConstruction.state = Objects.requireNonNull( state );
			return this;
		}
	}
}
