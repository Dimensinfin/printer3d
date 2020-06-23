package org.dimensinfin.printer3d.client.inventory.rest;

import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.backend.inventory.coil.persistence.Coil;
import org.dimensinfin.printer3d.client.inventory.rest.dto.CoilList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.FinishingsResponse;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Model;
import org.dimensinfin.printer3d.client.inventory.rest.dto.ModelList;
import org.dimensinfin.printer3d.client.inventory.rest.dto.NewModelRequest;
import org.dimensinfin.printer3d.client.inventory.rest.dto.Part;
import org.dimensinfin.printer3d.client.inventory.rest.dto.PartList;

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
	 * Add a new Part reference *partId* to the list of Parts that compose this model.
	 * The initial model has no parts associated because that is edited on a second UI. Add a new Part reference *partId* to the list of Parts that
	 * compose this model. If the Part is already at the model the part reference is added a second time.
	 *
	 * @param modelId The unique id (uuid) of the Model that store the Part link being received. (required)
	 * @param partId  The unique id (uuid) of the Part that is being added to this Model composition. (required)
	 * @return Call&lt;Model&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PUT("api/v1/inventory/models/{modelId}/addPart/{partId}")
	Call<Model> addModelPart( @Header("Authorization") final @NotNull String authorizationToken,
	                          @Path("modelId") UUID modelId,
	                          @Path("partId") UUID partId );

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
	 * Gets the list of **Machines** defined on the system and persisted on the Inventory unit.
	 * Machines do not change frequently so there is no need to have their UI. The Machine records are created manually but anyway the system is able
	 * to return their list form the repository. When the list of Machine records is collected from the repository the fields for job installation
	 * time are checked. If the elapsed time from this recorded time is less than the current time, then the job is expected to have finished. At this
	 * moment the machine is cleared and the part count for the target **Part** job is increased in the number of instances built.
	 *
	 * @return Call&lt;MachineList&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v1/inventory/machines")
	Call<MachineList> getMachines( @Header("Authorization") final @NotNull String authorizationToken );

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
	 * @param newModelRequest Contains the **Model** fields requested to the user on the frontend UI.
	 *                        (optional)
	 * @return Call&lt;Model&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@POST("api/v1/inventory/models")
	Call<Model> newModel( @Header("Authorization") final @NotNull String authorizationToken,
	                      @Body NewModelRequest newModelRequest );

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
	 * Removes a Part reference *partId* from the list of Parts that compose this model.
	 * This is a best effort action. If the Part identifier is not found or the part list becomes empty it is not considered an error.
	 *
	 * @param modelId The unique id (uuid) of the Model that store the Part link being removed. (required)
	 * @param partId  The unique id (uuid) of the Part that is being removed from this Model composition. (required)
	 * @return Call&lt;Model&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PUT("api/v1/inventory/models/{modelId}/removePart/{partId}")
	Call<Model> removeModelPart( @Header("Authorization") final @NotNull String authorizationToken,
	                             @Path("modelId") UUID modelId,
	                             @Path("partId") UUID partId );

	/**
	 * Signals the registration and start of a new build job.
	 * With this command the **Machine** will be active and building one new instance of the referenced Part model. The front end will not allow
	 * setting new jobs until this ends or it is cancelled.
	 *
	 * @param machineId The unique id (uuid) of the Machine that will execute the build job. (required)
	 * @param partId    The unique id (uuid) of the Part item to be built. (required)
	 * @return Call&lt;Machine&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@PUT("api/v1/inventory/machines/{machineId}/startbuild/{partId}")
	Call<Machine> startBuild( @Header("Authorization") final @NotNull String authorizationToken,
	                          @Path("machineId") final @NotNull UUID machineId,
	                          @Path("partId") final @NotNull UUID partId );

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
