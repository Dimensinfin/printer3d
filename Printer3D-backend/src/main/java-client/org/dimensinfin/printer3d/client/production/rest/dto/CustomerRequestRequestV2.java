package org.dimensinfin.printer3d.client.production.rest.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.annotations.SerializedName;

import org.dimensinfin.core.exception.DimensinfinRuntimeException;
import org.dimensinfin.core.utility.DimObjects;
import org.dimensinfin.printer3d.backend.production.request.rest.RequestRestErrors;

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
public class CustomerRequestRequestV2 {
	@NotNull(message = "Request unique UUID 'id' is a mandatory field and cannot be null.")
	@SerializedName("id")
	private UUID id;
	@NotNull(message = "Request 'label' is mandatory.")
	@Size(min = 3, max = 50)
	@SerializedName("label")
	private String label;
	@SerializedName("customer")
	private String customer;
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
	@NotNull(message = "The request amounts are set on the front end so should not be undefined.")
	@SerializedName("total")
	private Float total;
	@SerializedName("paid")
	private boolean paid = false;

	// - C O N S T R U C T O R S
	protected CustomerRequestRequestV2() {}

	// - G E T T E R S   &   S E T T E R S
	public List<RequestItem> getContents() {
		return this.contents;
	}

	public String getCustomer() {
		return this.customer;
	}

	public CustomerRequestRequestV2 setCustomer( final String customer ) {
		this.customer = customer;
		return this;
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

	public float getTotal() {
		return this.total;
	}

	public boolean isPaid() {
		return this.paid;
	}

	// - B U I L D E R
	public static class Builder {
		private final CustomerRequestRequestV2 onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new CustomerRequestRequestV2();
		}

		public CustomerRequestRequestV2 build() {
			DimObjects.requireNonNull( this.onConstruction.id );
			DimObjects.requireNonNull( this.onConstruction.label );
			if (null == this.onConstruction.requestDate) this.onConstruction.requestDate = Instant.now().toString();
			if (this.onConstruction.contents.isEmpty())
				throw new DimensinfinRuntimeException( RequestRestErrors.errorREQUESTMISSINGFIELD( "contents" ) );
			DimObjects.requireNonNull( this.onConstruction.total );
			return this.onConstruction;
		}

		public CustomerRequestRequestV2.Builder withContents( final List<RequestItem> contents ) {
			this.onConstruction.contents = DimObjects.requireNonNull( contents );
			return this;
		}

		public CustomerRequestRequestV2.Builder withId( final UUID id ) {
			this.onConstruction.id = DimObjects.requireNonNull( id );
			return this;
		}

		public CustomerRequestRequestV2.Builder withLabel( final String label ) {
			this.onConstruction.label = DimObjects.requireNonNull( label );
			return this;
		}

		public CustomerRequestRequestV2.Builder withPaid( final Boolean paid ) {
			if (null != paid) this.onConstruction.paid = paid;
			return this;
		}

		public CustomerRequestRequestV2.Builder withRequestDate( final String requestDate ) {
			if (null != requestDate) this.onConstruction.requestDate = requestDate;
			return this;
		}

		public CustomerRequestRequestV2.Builder withTotalAmount( final Float amount ) {
			if (null != amount) this.onConstruction.total = amount;
			return this;
		}
	}
}
