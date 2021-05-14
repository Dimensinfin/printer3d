package org.dimensinfin.printer3d.client.production.rest.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Adam Antinoo (adamantinoo.git@gmail.com)
 * @since 0.17.0
 */
public class CustomerRequestResponseV2 {
	private UUID id;
	private String label;
	private String customer;
	private Instant requestDate;
	private Instant completedDate;
	private Instant paymentDate;
	private RequestState state = RequestState.OPEN;
	private boolean paid = false;
	private List<RequestItem> contents = new ArrayList<>();
	private float amount = 0.0F;
	private float iva = 0.0F;
	private float total = 0.0F;

	// - C O N S T R U C T O R S
	public CustomerRequestResponseV2() {}

	// - G E T T E R S   &   S E T T E R S
	public float getAmount() {
		return this.amount;
	}

	public CustomerRequestResponseV2 setAmount( final float amount ) {
		this.amount = amount;
		return this;
	}

	public Instant getCompletedDate() {
		return this.completedDate;
	}

	public CustomerRequestResponseV2 setCompletedDate( final Instant completedDate ) {
		this.completedDate = completedDate;
		return this;
	}

	public List<RequestItem> getContents() {
		return this.contents;
	}

	public CustomerRequestResponseV2 setContents( final List<RequestItem> contents ) {
		this.contents = contents;
		return this;
	}

	public String getCustomer() {
		return this.customer;
	}

	public CustomerRequestResponseV2 setCustomer( final String customer ) {
		this.customer = customer;
		return this;
	}

	public UUID getId() {
		return this.id;
	}

	public CustomerRequestResponseV2 setId( final UUID id ) {
		this.id = id;
		return this;
	}

	public float getIva() {
		return this.iva;
	}

	public CustomerRequestResponseV2 setIva( final float iva ) {
		this.iva = iva;
		return this;
	}

	public String getLabel() {
		return this.label;
	}

	public CustomerRequestResponseV2 setLabel( final String label ) {
		this.label = label;
		return this;
	}

	public Instant getPaymentDate() {
		return this.paymentDate;
	}

	public CustomerRequestResponseV2 setPaymentDate( final Instant paymentDate ) {
		this.paymentDate = paymentDate;
		return this;
	}

	public Instant getRequestDate() {
		return this.requestDate;
	}

	public CustomerRequestResponseV2 setRequestDate( final Instant requestDate ) {
		this.requestDate = requestDate;
		return this;
	}

	public RequestState getState() {
		return this.state;
	}

	public CustomerRequestResponseV2 setState( final RequestState state ) {
		this.state = state;
		return this;
	}

	public float getTotal() {
		return this.total;
	}

	public CustomerRequestResponseV2 setTotal( final float total ) {
		this.total = total;
		return this;
	}

	public boolean isPaid() {
		return this.paid;
	}

	public CustomerRequestResponseV2 setPaid( final boolean paid ) {
		this.paid = paid;
		return this;
	}
	//	// - B U I L D E R
	//	public static class Builder {
	//		private final CustomerRequestResponseV2 onConstruction;
	//
	//		// - C O N S T R U C T O R S
	//		public Builder() {
	//			this.onConstruction = new CustomerRequestResponseV2();
	//		}
	//
	//		public CustomerRequestResponseV2 build() {
	//			return this.onConstruction;
	//		}
	//	}
}