package org.dimensinfin.printer3d.client.production.rest;

import java.util.List;

import org.dimensinfin.printer3d.client.production.rest.dto.RequestV2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
	Call<RequestV2> newRequest( @Body RequestV2 request );
}
