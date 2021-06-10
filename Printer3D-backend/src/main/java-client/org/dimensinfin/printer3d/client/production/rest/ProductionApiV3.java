package org.dimensinfin.printer3d.client.production.rest;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.production.rest.dto.CustomerRequestResponseV2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductionApiV3 {
	// - G E T T E R S   &   S E T T E R S
	@Headers({ "Content-Type:application/json" })
	@GET("api/v3/production/requests/closed")
	Call<List<CustomerRequestResponseV2>> getClosedRequests();

	/**
	 * Get the list of Requests persisted at the repository.
	 * Get the complete list of **Requests** persisted at the Production repository and that are on an Open state.
	 *
	 * @return Call&lt;RequestList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v3/production/requests/open")
	Call<List<CustomerRequestResponseV2>> getOpenRequests();

	@Headers({ "Content-Type:application/json" })
	@PUT("api/v3/production/requests/{requestId}/deliver")
	Call<CustomerRequestResponseV2> deliverRequest( @Path("requestId") @NotNull final UUID requestId );
}
