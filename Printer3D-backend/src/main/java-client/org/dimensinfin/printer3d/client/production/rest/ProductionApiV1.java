package org.dimensinfin.printer3d.client.production.rest;

import java.util.List;

import org.dimensinfin.printer3d.client.production.rest.dto.Job;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ProductionApiV1 {
	// - G E T T E R S   &   S E T T E R S

//	/**
//	 * Get the list of Requests persisted at the repository.
//	 * Get the complete list of **Requests** persisted at the Production repository and that are on an Open state.
//	 *
//	 * @return Call&lt;RequestList&gt;
//	 */
//	@Headers({ "Content-Type:application/json" })
//	@GET("api/v1/production/requests")
//	Call<RequestList> getOpenRequests();

	/**
	 * Create the list of jobs required to reach the **Inventory** target.
	 * Search the **Inventory** needs and stocks mismatch to create a list of the Parts missing to reach the correct stocks levels for each part and
	 * each color. This version also has the rule 1 to show contiguous all parts with the same material type and color and same priority. Also applies
	 * the rule 2 to show contiguous jobs that use the same model if there rule 1 does not applies.
	 *
	 * @return Call&lt;JobsList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/production/jobs/pending")
	Call<List<Job>> getPendingJobs();

//	/**
//	 * Closes the state of a selected Request.
//	 * When the Request is closed is the moment where the Parts that compose the request are subtracted from the Parts inventory.
//	 *
//	 * @return Call&lt;Request&gt;
//	 */
//	@Headers({ "Content-Type:application/json" })
//	@PUT("api/v1/production/requests/{requestId}/close")
//	Call<Request> closeRequest( @Path("requestId") final @NotNull UUID requestId );

//	/**
//	 * Creates a new Request instance at the persistence repository.
//	 * Customers that buy Parts will fill a request form. This form is processed and if all the Parts requested are found at the stock then the
//	 * Request can be send and it will be closed. If there are missing Parts then priority jobs will be listed and the Requests stays open.
//	 *
//	 * @param request Contains the **Request** field values filled at the UI.
//	 *                (optional)
//	 * @return Call&lt;Request&gt;
//	 */
//	@Headers({ "Content-Type:application/json" })
//	@POST("api/v1/production/requests")
//	Call<Request> newRequest( @Body Request request );

//	/**
//	 * Updates an Open Request instance at the persistence repository.
//	 * An open Request can be edited at the UI. The user can decide to reduce the number of Parts initially requested or he can add or remove Parts
//	 * from the Request. Once the Request is completed and closed it is not editable..
//	 *
//	 * @param request Contains the **Request** field values filled at the UI.
//	 *                (optional)
//	 * @return Call&lt;Request&gt;
//	 */
//	@Headers({ "Content-Type:application/json" })
//	@PATCH("api/v1/production/requests")
//	Call<Request> updateRequest( @Body Request request
//	);
}
