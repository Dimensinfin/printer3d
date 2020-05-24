package org.dimensinfin.printer3d.client.part.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.client.part.domain.PartList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PartsApiV1 {
	/**
	 * Create a new Part from the data on the request.
	 * The Printer3D user interface should have requested the Part contents to the user. This endpoint should validate all the fields against the
	 * validation requirements and create a new record on the Inventory repository for the new Part.
	 *
	 * @param newPart the new part data to be persisted.
	 * @return Call&lt;Part&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@POST("api/v1/inventory/parts")
	Call<Part> newPart( @Header("Authorization") @NotNull final String authorizationToken,
	                    @Body @Valid @NotNull final Part newPart );

	/**
	 * Get the list of Parts persisted at the Inventory repository.
	 * Get the complete list of &lt;b&gt;Parts&lt;/b&gt; persisted at the Inventory repository. If the active filter is active retrieve only the
	 * active Parts.
	 *
	 * @param activesOnly Allows the selection or filtering for not active Parts. By default all active parts are retrieved. (optional, default to false)
	 * @return Call&lt;PartList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/parts")
	Call<PartList> partList( @Header("Authorization") @NotNull final String authorizationToken,
	                         @Query("activesOnly") Boolean activesOnly );
//
//	/**
//	 * Update an alrady existing Part. Only some of the firlds are allowed for update. The rest of the fields are ignored.
//	 * Process a request to update an existing Part on the repository by giving the new field contents. Only the allowed modifiable contents are
//	 * processed and the other fields are ignored.
//	 *
//	 * @param partId      The unique id (uuid) for the Part to be edited. (required)
//	 * @param partRequest The new contents for the Part record.
//	 *                    (optional)
//	 * @return Call&lt;PartList&gt;
//	 */
//	@Headers({
//			"Content-Type:application/json"
//	})
//	@PATCH("api/v1/parts/{partId}")
//	Call<PartList> updatePart(
//			@Path("partId") UUID partId, @Body PartRequest partRequest
//	);

}
