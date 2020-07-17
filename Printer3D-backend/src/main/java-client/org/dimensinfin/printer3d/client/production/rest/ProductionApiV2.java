package org.dimensinfin.printer3d.client.production.rest;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductionApiV2 {
	// - G E T T E R S   &   S E T T E R S

	/**
	 * Get the list of Requests persisted at the repository.
	 * Get the complete list of **Requests** persisted at the Production repository and that are on an Open state.
	 *
	 * @return Call&lt;RequestList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v2/production/requests")
	Call<List<RequestV2>> getOpenRequests();

	/**
	 * Closes the state of a selected Request.
	 * When the Request is closed is the moment where the Parts that compose the request are subtracted from the Parts inventory.
	 *
	 * @return Call&lt;Request&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PUT("api/v2/production/requests/{requestId}/close")
	Call<RequestV2> closeRequest( @Path("requestId") final @NotNull UUID requestId );

	/**
	 * Delete the selected Request form the repository.
	 * Requests have no other relations with repository records so they can be deleted safely. If the user select to remove a Request because it
	 * should be modified of because no longer required the record ban be deleted without impact. Only OPEN Requests can be deleted. CLOSED are no
	 * longer available for update or deletion.
	 *
	 * @return Call&lt;CounterResponse&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@DELETE("api/v2/production/requests/{requestId}")
	Call<CounterResponse> deleteRequest( @Path("requestId") final @NotNull UUID requestId );

	/**
	 * Creates a new Request instance at the persistence repository.
	 * Customers that buy Parts will fill a request form. This form is processed and if all the Parts requested are found at the stock then the
	 * Request can be send and it will be closed. If there are missing Parts then priority jobs will be listed and the Requests stays open.
	 *
	 * @param request Contains the **Request** field values filled at the UI.
	 *                (optional)
	 * @return Call&lt;Request&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@POST("api/v2/production/requests")
	Call<RequestV2> newRequest( @Body final @NotNull @Valid RequestV2 request );
}
