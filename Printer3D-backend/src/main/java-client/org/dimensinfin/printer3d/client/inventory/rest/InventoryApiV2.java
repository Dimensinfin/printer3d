package org.dimensinfin.printer3d.client.inventory.rest;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;

import org.dimensinfin.printer3d.client.inventory.rest.dto.Machine;
import org.dimensinfin.printer3d.client.inventory.rest.dto.MachineV2;
import org.dimensinfin.printer3d.client.production.rest.dto.JobRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InventoryApiV2 {
	// - G E T T E R S   &   S E T T E R S

	/**
	 * Gets the list of **Machines** defined on the system and persisted on the Inventory unit.
	 * This version changes the contents so it is not compatible with previous versions. The build job information is collected into a new data
	 * structure and there are more fields added to the build record.
	 *
	 * @return Call&lt;MachineListV2&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@GET("api/v2/inventory/machines")
	Call<List<MachineV2>> getMachines();

	/**
	 * Signals the registration and start of a new build job.
	 * With this command the **Machine** will be active and building one new instance of the referenced Part model. The front end will not allow
	 * setting new jobs until this ends or it is cancelled.
	 *
	 * @param machineId The unique id (uuid) of the Machine that will execute the build job. (required)* @param partId    The unique id (uuid) of the Part item to be built. (required)
	 * @param job       The endpoint receives a new Job Request that has some of the key job identifiers. The new implementation allows to set the
	 *                  number of identical copies of the Part so the inventory can be updated precisely with each job completed.
	 *                  (optional)
	 * @return Call&lt;Machine&gt;
	 */
	@Headers({ "Content-Type:application/json" })
	@POST("api/v2/inventory/machines/{machineId}/startbuild")
	Call<Machine> startBuild( @Header("Authorization") final @NotNull String authorizationToken,
	                          @Path("machineId") UUID machineId,
	                          @Body JobRequest job );
}
