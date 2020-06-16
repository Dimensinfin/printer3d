package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class RequestList {
	private List<Request> requests = new ArrayList<>();

	// - C O N S T R U C T O R S
	private RequestList() {}

	// - G E T T E R S   &   S E T T E R S
	public int getCount() {
		return this.requests.size();
	}

	public List<Request> getRequests() {
		return this.requests;
	}

	public void addRequest( final Request request ) {
		this.requests.add( request );
	}

	// - B U I L D E R
	public static class Builder {
		private final RequestList onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new RequestList();
		}

		public RequestList build() {
			return this.onConstruction;
		}
	}
}
