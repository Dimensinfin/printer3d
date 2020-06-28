package org.dimensinfin.printer3d.client.production.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class RequestListV2 {
	private List<RequestV2> requests = new ArrayList<>();

	// - C O N S T R U C T O R S
	private RequestListV2() {}

	// - G E T T E R S   &   S E T T E R S
	public int getCount() {
		return this.requests.size();
	}

	public List<RequestV2> getRequests() {
		return this.requests;
	}

	public void addRequest( final RequestV2 request ) {
		this.requests.add( request );
	}

	// - B U I L D E R
	public static class Builder {
		private final RequestListV2 onConstruction;

		// - C O N S T R U C T O R S
		public Builder() {
			this.onConstruction = new RequestListV2();
		}

		public RequestListV2 build() {
			return this.onConstruction;
		}
	}
}
