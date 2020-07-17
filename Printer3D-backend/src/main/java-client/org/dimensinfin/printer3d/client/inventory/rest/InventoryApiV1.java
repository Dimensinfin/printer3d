package org.dimensinfin.printer3d.client.inventory.rest;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.core.dto.CounterResponse;
import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateCoilRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.UpdateGroupPartRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InventoryApiV1 {
	// - G E T T E R S   &   S E T T E R S

	/**
	 * Get the list of Models persisted at the Inventory repository.
	 * Get the complete list of **Models** persisted at the Inventory repository.
	 *
	 * @return Call&lt;ModelList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/models")
	Call<ModelList> getModels();

	/**
	 * Signals the cancellation of the current build.
	 * With this command the **Machine** will be recorded as ready. This cancellation command has not to be related toa real cancellation at the real
	 * 3D printer. Maybe used to correct the current job being performed by the real printer..
	 *
	 * @param machineId The unique id (uuid) of the Machine we are requesting to cancel the build job. (required)
	 * @return Call&lt;Machine&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PUT("api/v1/inventory/machines/{machineId}/cancelbuild")
	Call<Machine> cancelBuild( @Header("Authorization") final @NotNull String authorizationToken,
	                           @Path("machineId") final @NotNull UUID machineId );

	/**
	 * Signals the completion of the current build.
	 * With this command the **Machine** will complete the current job. This will change the Part stock levels adding to the current Part stock the
	 * number of Parts already built. Then the Machine will return to the IDLE state. The Job registered on the Machine is then persisted to a nee
	 * backend repository to create an analysis record to be able to perform production statistics with the completed jobs.
	 *
	 * @param machineId The unique id (uuid) of the Machine we are requesting to complete the build job. (required)
	 * @return Call&lt;Machine&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PUT("api/v1/inventory/machines/{machineId}/completebuild")
	Call<Machine> completeBuild( @Path("machineId") final @NotNull UUID machineId );

	/**
	 * Get the list of Coils persisted at the Inventory repository.
	 * Get the complete list of **Coils** persisted at the Inventory repository.
	 *
	 * @return Call&lt;CoilList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/coils")
	Call<CoilList> getCoils( @Header("Authorization") final @NotNull String authorizationToken );

	/**
	 * Returns the list of Materials with the colors available for each one.
	 * The endpoint collects the materials and colors from all the stock of available coils. Collects the data and sorts the colors before packing
	 * them to be retuned to the frontend.
	 *
	 * @return Call&lt;FinishingsResponse&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/finishings")
	Call<FinishingsResponse> getFinishings( @Header("Authorization") final @NotNull String authorizationToken );

	/**
	 * Get the list of Models persisted at the Inventory repository.
	 * Get the complete list of **Models** persisted at the Inventory repository.
	 *
	 * @return Call&lt;ModelList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/models")
	Call<ModelList> getModels( @Header("Authorization") final @NotNull String authorizationToken );

	/**
	 * Get the list of Parts persisted at the Inventory repository.
	 * Get the complete list of &lt;b&gt;Parts&lt;/b&gt; persisted at the Inventory repository. If the active filter is active retrieve only the
	 * active Parts.
	 *
	 * @param activesOnly Allows the selection or filtering for not active Parts. By default all active parts are retrieved. If false all parts are
	 *                    included on the list. If tru only active parts will be returned by the filter.  (optional, default to false)
	 * @return Call&lt;PartList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/parts")
	Call<PartList> getParts( @Header("Authorization") final @NotNull String authorizationToken,
	                         @Query("activesOnly") final Boolean activesOnly );

	/**
	 * Creates a new **Coil** instance on the persistence repository.
	 * The 3D printers need and use plastic filament to build the models. The Coils have as main characteristics the type of plastic, the color and
	 * the quantity of the material.
	 *
	 * @param newCoil Contains the *Coil** fields requested to the user on the frontend UI.
	 *                (optional)
	 * @return Call&lt;Coil&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@POST("api/v1/inventory/coils")
	Call<Coil> newCoil( @Header("Authorization") final @NotNull String authorizationToken,
	                    @Body final @NotNull @Valid Coil newCoil );

	/**
	 * Creates a new **Model**.
	 * The initial model has no parts associated because that is edited on a second UI. On this endpoint we set all the other Model fields.
	 *
	 * @param modelRequest Contains the **Model** fields requested to the user on the frontend UI.
	 *                        (optional)
	 * @return Call&lt;Model&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@POST("api/v1/inventory/models")
	Call<Model> newModel( @Header("Authorization") final @NotNull String authorizationToken,
	                      @Body ModelRequest modelRequest );

	/**
	 * Create a new Part from the data on the request.
	 * The Printer3D user interface should have requested the Part contents to the user. This endpoint should validate all the fields against the
	 * validation requirements and create a new record on the Inventory repository for the new Part.
	 *
	 * @param newPart Contains the Part fields to be used to create a new Part record at the repository.
	 *                (optional)
	 * @return Call&lt;Part&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@POST("api/v1/inventory/parts")
	Call<Part> newPart( @Header("Authorization") final @NotNull String authorizationToken,
	                    @Body final @NotNull @Valid Part newPart );

	/**
	 * Updates an existing Coil.
	 * Updates a Coil. All only field allowed to be changed is the weight.
	 *
	 * @param updateCoilRequest Contains the **Coil** fields that come from the GUI with the new Coil data.
	 *                          (optional)
	 * @return Call&lt;Coil&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PATCH("api/v1/inventory/coils")
	Call<Coil> updateCoil( @Body UpdateCoilRequest updateCoilRequest );

	/**
	 * Usually there a re a set of parts that share most of the field values changing only the finishings (material and color). Because the
	 * frontend will group them into sets and there is no purpose on having different values for same parts this endpoint will take care of
	 * changing the et values to the whole set of parts that have the same Label.
	 *
	 * @param updateData Contains the Part fields to be used to update group of Parts at the repository.
	 *                   (optional)
	 * @return Call&lt;Part&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PATCH("api/v1/inventory/parts/group")
	Call<CounterResponse> updateGroupPart( @Body final @NotNull @Valid UpdateGroupPartRequest updateData );

	/**
	 * Updates an existing Model.
	 * Updates a Model. All the fields are editable but the id. So the endpoint just replaces the contents.
	 *
	 * @param modelRequest Contains the **Model** fields requested to the user on the frontend UI.
	 *                     (optional)
	 * @return Call&lt;Model&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PATCH("api/v1/inventory/models")
	Call<Model> updateModel( @Body final @NotNull ModelRequest modelRequest );

	/**
	 * Update a Part with some of the fields on the request.
	 * The Printer3D user interface now has a feature to change some of the Part fields. Thew are mostly related with the stock values and the cost
	 * data. When the PATCH operation is sent to the backend all the fields are populated but this endpoint will only get some of them and persist
	 * their new values on the repository.
	 *
	 * @param part Contains the Part fields to be used to update the Part record at the repository.
	 *             (optional)
	 * @return Call&lt;Part&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PATCH("api/v1/inventory/parts")
	Call<Part> updatePart( @Header("Authorization") final @NotNull String authorizationToken,
	                       @Body final @NotNull @Valid Part part );

}
