package org.dimensinfin.printer3d.client.part.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.inventory.roll.persistence.Roll;
import org.dimensinfin.printer3d.backend.part.persistence.Part;
import org.dimensinfin.printer3d.client.domain.FinishingsResponse;
import org.dimensinfin.printer3d.client.domain.PartList;
import org.dimensinfin.printer3d.client.domain.RollList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InventoryApiV1 {
	/**
	 * Returns the list of Materials with the colors available for each one.
	 * The endpoint collects the materials and colors from all the stock of available rolls. Collects the data and sorts the colors before packing
	 * them to be retuned to the frontend.
	 *
	 * @return Call&lt;FinishingsResponse&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/finishings")
	Call<FinishingsResponse> getFinishings( @Header("Authorization") final @NotNull String authorizationToken );
	/**
	 * Get the list of Rolls persisted at the Inventory repository.
	 * Get the complete list of **Rolls** persisted at the Inventory repository.
	 * @return Call&lt;RollList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/rolls")
	Call<RollList> getRolls( @Header("Authorization") final @NotNull String authorizationToken);

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
	Call<Part> newPart( @Header("Authorization") final @NotNull String authorizationToken,
	                    @Body @NotNull @Valid final Part newPart );

	/**
	 * Creates a new **Roll** instance on the persistence repository.
	 * The 3D printers need and use plastic filament to build the models. The rolls have as main characteristics the type of plastic, the color and
	 * the quantity of the material.
	 *
	 * @param roll Contains the *Roll** fields requested to the user on the frontend UI.
	 * @return the persisted Roll record.
	 */
	@Headers({ "Content-Type:application/json" })
	@POST("api/v1/inventory/rolls")
	Call<Roll> newRoll( @Header("Authorization") final @NotNull String authorizationToken,
	                    @Body @NotNull @Valid Roll roll
	);

	/**
	 * Get the list of Parts persisted at the Inventory repository.
	 * Get the complete list of &lt;b&gt;Parts&lt;/b&gt; persisted at the Inventory repository. If the active filter is active retrieve only the
	 * active Parts.
	 *
	 * @param activesOnly Allows the selection or filtering for not active Parts. By default all active parts are retrieved. (optional, default to
	 *                    false)
	 * @return Call&lt;PartList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/parts")
	Call<PartList> partList( @Header("Authorization") final @NotNull String authorizationToken,
	                         @Query("activesOnly") Boolean activesOnly );
}
